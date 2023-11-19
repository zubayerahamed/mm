package com.zayaanit.mm.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zayaanit.mm.dto.req.AccountReqDto;
import com.zayaanit.mm.dto.res.AccountResDto;
import com.zayaanit.mm.entity.Account;
import com.zayaanit.mm.entity.Arhed;
import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.enums.AccountType;
import com.zayaanit.mm.enums.TransactionType;
import com.zayaanit.mm.repo.AccountRepo;
import com.zayaanit.mm.repo.ArhedRepo;
import com.zayaanit.mm.repo.TrnHistoryRepo;
import com.zayaanit.mm.service.AccountService;
import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

import jakarta.transaction.Transactional;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Service
public class AccountServiceImpl extends AbstractBaseService<Account, AccountReqDto, AccountResDto> implements AccountService<AccountReqDto, AccountResDto> {

	private AccountRepo accountRepo;
	@Autowired private ArhedRepo arhedRepo;
	@Autowired private TrnHistoryRepo trnHistoryRepo;

	AccountServiceImpl(AccountRepo accountRepo) {
		super(accountRepo);
		this.accountRepo = accountRepo;
	}

	@Override
	public Response<AccountResDto> find(Long id) throws ServiceException {
		if(id == null) throw new ServiceException("Id Required");
		Optional<Account> accountOp = accountRepo.findByIdAndUser(id, getLoggedInUser());
		return accountOp.isPresent() ? getSuccessResponse("Account foud", new AccountResDto(accountOp.get())) : getErrorResponse("Account not found");
	}

	@Transactional
	@Override
	public Response<AccountResDto> save(AccountReqDto reqDto) throws ServiceException {
		if(reqDto == null) throw new ServiceException("Request is empty");
		if(StringUtils.isBlank(reqDto.getName())) throw new ServiceException("Account name required");
		if(reqDto.getAccountType() == null) throw new ServiceException("Account type required");
		if(reqDto.getAmount().compareTo(BigDecimal.ZERO) == -1) throw new ServiceException("Invalid opening amount");

		Account account = createEntity(reqDto.getBean());
		if(account == null) throw new ServiceException("Account creation failed");

		// if wallet
		Calendar cal = Calendar.getInstance();
		if(AccountType.WALLET.equals(reqDto.getAccountType())) {
			// transaction history first
			TrnHistory th = new TrnHistory();
			th.setTransactionType(TransactionType.OPENING);
			th.setAmount(reqDto.getAmount());
			th.setTransactionCharge(BigDecimal.ZERO);
			th.setFromAccount(null);
			th.setToAccount(account);
			th.setIncomeSource(null);
			th.setExpenseType(null);
			th.setTransactionDate(cal.getTime());
			th.setTransactionTime(cal.getTime());
			th.setNote("Opening Entry");
			th.setYear(String.valueOf(cal.get(Calendar.YEAR)));
			th.setMonth(String.valueOf(cal.get(Calendar.MONTH) + 1));

			th.setCreatedBy(getLoggedInUserDetails().getUsername());
			th.setCreatedOn(new Date());
			th.setUpdatedBy(getLoggedInUserDetails().getUsername());
			th.setUpdatedOn(new Date());
			th.setUser(getLoggedInUser());
			trnHistoryRepo.save(th);

			// create arhed
			Arhed arhed = new Arhed();
			arhed.setAccount(account);
			arhed.setRowSign(1);
			arhed.setAmount(reqDto.getAmount());
			arhed.setTransactionCharge(BigDecimal.ZERO);
			arhed.setTransactionDate(new Date());
			arhed.setTransactionTime(new Date());
			arhed.setTrnHistory(th);

			arhed.setCreatedBy(getLoggedInUserDetails().getUsername());
			arhed.setCreatedOn(new Date());
			arhed.setUpdatedBy(getLoggedInUserDetails().getUsername());
			arhed.setUpdatedOn(new Date());
			arhed.setUser(getLoggedInUser());

			arhed = arhedRepo.save(arhed);
			if(arhed == null) throw new ServiceException("Account creation failed");
		}

		return getSuccessResponse("Account created successfully", new AccountResDto(account));
	}

	@Transactional
	@Override
	public Response<AccountResDto> update(Long id, AccountReqDto reqDto) throws ServiceException {
		if(id == null) throw new ServiceException("Id Required");
		if(reqDto == null) throw new ServiceException("Request is empty");
		if(StringUtils.isBlank(reqDto.getName())) throw new ServiceException("Account name required");

		Optional<Account> accountOp = accountRepo.findByIdAndUser(id, getLoggedInUser());
		if(accountOp.isEmpty()) throw new ServiceException("Account not exist");

		Account account = accountOp.get();
		BeanUtils.copyProperties(reqDto, account, "accountType", "id");

		account = updateEntity(account);
		if(account == null) throw new ServiceException("Account update failed");

		return getSuccessResponse("Account updated successfully", new AccountResDto(account));
	}

	@Override
	public Response<AccountResDto> getAll() throws ServiceException {
		List<Account> list = accountRepo.findAllByUser(getLoggedInUser());
		if (list == null || list.isEmpty()) return getErrorResponse("No accounts found");
		list.sort(Comparator.comparing(Account::getName));
		return getSuccessResponse("Found List of Accounts", list.stream().map(data -> new ModelMapper().map(data, AccountResDto.class)).collect(Collectors.toList()));
	}

	@Override
	public Response<AccountResDto> getAllByAccountType(AccountType accountType) throws ServiceException {
		if(accountType == null) throw new ServiceException("Account type required");

		List<Account> list = accountRepo.findAllByAccountTypeAndUser(accountType, getLoggedInUser());
		if (list == null || list.isEmpty()) return getErrorResponse("No accounts found");
		list.sort(Comparator.comparing(Account::getName));
		return getSuccessResponse("Found List of Accounts", list.stream().map(data -> new ModelMapper().map(data, AccountResDto.class)).collect(Collectors.toList()));
	}

	@Transactional
	@Override
	public Response<AccountResDto> delete(Long id) throws ServiceException {
		if(id == null) throw new ServiceException("Id Required");

		Optional<Account> accountOp = accountRepo.findByIdAndUser(id, getLoggedInUser());
		if(accountOp.isEmpty()) throw new ServiceException("Account not exist");

		Account account = accountOp.get();
		accountRepo.delete(account);

		return getSuccessResponse("Account deleted successfully");
	}

}
