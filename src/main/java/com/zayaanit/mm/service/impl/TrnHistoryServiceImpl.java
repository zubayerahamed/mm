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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zayaanit.mm.dto.req.TrnHistoryReqDto;
import com.zayaanit.mm.dto.res.TrnHistoryResDto;
import com.zayaanit.mm.entity.Arhed;
import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.enums.TransactionType;
import com.zayaanit.mm.repo.ArhedRepo;
import com.zayaanit.mm.repo.TrnHistoryRepo;
import com.zayaanit.mm.service.TrnHistoryService;
import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

import jakarta.transaction.Transactional;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Service
public class TrnHistoryServiceImpl extends AbstractBaseService<TrnHistory, TrnHistoryReqDto, TrnHistoryResDto> implements TrnHistoryService<TrnHistoryReqDto, TrnHistoryResDto> {

	private TrnHistoryRepo trnHistoryRepo;
	@Autowired private ArhedRepo arhedRepo;

	TrnHistoryServiceImpl(TrnHistoryRepo trnHistoryRepo) {
		super(trnHistoryRepo);
		this.trnHistoryRepo = trnHistoryRepo;
	}

	@Override
	public Response<TrnHistoryResDto> find(Long id) throws ServiceException {
		if(id == null) throw new ServiceException("Id Required");
		Optional<TrnHistory> trnOp = trnHistoryRepo.findByIdAndUser(id, getLoggedInUser());
		return trnOp.isPresent() ? getSuccessResponse("Transaction history foud", new TrnHistoryResDto(trnOp.get())) : getErrorResponse("Transaction history not found");
	}

	@Transactional
	@Override
	public Response<TrnHistoryResDto> save(TrnHistoryReqDto reqDto) throws ServiceException {
		if(reqDto == null) throw new ServiceException("Request is empty");
		if(reqDto.getTransactionType() == null) throw new ServiceException("Transaction type required");
		if(reqDto.getAmount() == null) throw new ServiceException("Amount required");
		if(reqDto.getAmount().compareTo(BigDecimal.ZERO) == -1) throw new ServiceException("Invalid amount");
		if(reqDto.getTransactionCharge() == null) throw new ServiceException("Transaction charge required");
		if(reqDto.getTransactionCharge().compareTo(BigDecimal.ZERO) == -1) throw new ServiceException("Invalid transaction charge");
		if(reqDto.getTrnDate() == null) throw new ServiceException("Date required");
		if(StringUtils.isBlank(reqDto.getTrnTime())) throw new ServiceException("Time required");

		String[] time = reqDto.getTrnTime().split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTime(reqDto.getTrnDate());
		cal.set(Calendar.HOUR, Integer.valueOf(time[0]));
		cal.set(Calendar.MINUTE, Integer.valueOf(time[1]));
		cal.set(Calendar.SECOND, Integer.valueOf(time[2]));

		TrnHistory th = new TrnHistory();

		if(TransactionType.INCOME.equals(reqDto.getTransactionType())) {
			if(reqDto.getIncomeSource() == null) throw new ServiceException("Income source required");
			if(reqDto.getToAccount() == null) throw new ServiceException("To account required");

			th.setTransactionType(TransactionType.INCOME);
			th.setAmount(reqDto.getAmount());
			th.setTransactionCharge(reqDto.getTransactionCharge());
			th.setFromAccount(null);
			th.setToAccount(reqDto.getToAccount());
			th.setIncomeSource(reqDto.getIncomeSource());
			th.setExpenseType(null);
			th.setTransactionDate(cal.getTime());
			th.setTransactionTime(cal.getTime());
			th.setNote(reqDto.getNote());
			th.setYear(String.valueOf(cal.get(Calendar.YEAR)));
			th.setMonth(String.valueOf(cal.get(Calendar.MONTH) + 1));

			th = createEntity(th);
			if(th == null) throw new ServiceException("Transaction failed");

			// arhed
			Arhed a = new Arhed();
			a.setAccount(reqDto.getToAccount());
			a.setRowSign(1);
			a.setAmount(reqDto.getAmount());
			a.setTransactionCharge(reqDto.getTransactionCharge());
			a.setTransactionDate(cal.getTime());
			a.setTransactionTime(cal.getTime());
			a.setTrnHistory(th);

			a.setCreatedBy(getLoggedInUserDetails().getUsername());
			a.setCreatedOn(new Date());
			a.setUpdatedBy(getLoggedInUserDetails().getUsername());
			a.setUpdatedOn(new Date());
			a.setUser(getLoggedInUser());

			a = arhedRepo.save(a);
			if(a == null) throw new ServiceException("Transaction failed");
		} else if (TransactionType.EXPENSE.equals(reqDto.getTransactionType())) {
			if(reqDto.getFromAccount() == null) throw new ServiceException("From account required");
			if(reqDto.getExpenseType() == null) throw new ServiceException("Expense Type required");

			th.setTransactionType(TransactionType.EXPENSE);
			th.setAmount(reqDto.getAmount());
			th.setTransactionCharge(reqDto.getTransactionCharge());
			th.setFromAccount(reqDto.getFromAccount());
			th.setToAccount(null);
			th.setIncomeSource(null);
			th.setExpenseType(reqDto.getExpenseType());
			th.setTransactionDate(cal.getTime());
			th.setTransactionTime(cal.getTime());
			th.setNote(reqDto.getNote());
			th.setYear(String.valueOf(cal.get(Calendar.YEAR)));
			th.setMonth(String.valueOf(cal.get(Calendar.MONTH) + 1));

			th = createEntity(th);
			if(th == null) throw new ServiceException("Transaction failed");

			// arhed
			Arhed a = new Arhed();
			a.setAccount(reqDto.getFromAccount());
			a.setRowSign(-1);
			a.setAmount(reqDto.getAmount());
			a.setTransactionCharge(reqDto.getTransactionCharge());
			a.setTransactionDate(cal.getTime());
			a.setTransactionTime(cal.getTime());
			a.setTrnHistory(th);

			a.setCreatedBy(getLoggedInUserDetails().getUsername());
			a.setCreatedOn(new Date());
			a.setUpdatedBy(getLoggedInUserDetails().getUsername());
			a.setUpdatedOn(new Date());
			a.setUser(getLoggedInUser());

			a = arhedRepo.save(a);
			if(a == null) throw new ServiceException("Transaction failed");
		} else if (TransactionType.TRANSFER.equals(reqDto.getTransactionType())) {
			if(reqDto.getFromAccount() == null) throw new ServiceException("From account required");
			if(reqDto.getToAccount() == null) throw new ServiceException("To account required");

			th.setTransactionType(TransactionType.TRANSFER);
			th.setAmount(reqDto.getAmount());
			th.setTransactionCharge(reqDto.getTransactionCharge());
			th.setFromAccount(reqDto.getFromAccount());
			th.setToAccount(reqDto.getToAccount());
			th.setIncomeSource(null);
			th.setExpenseType(null);
			th.setTransactionDate(cal.getTime());
			th.setTransactionTime(cal.getTime());
			th.setNote(reqDto.getNote());
			th.setYear(String.valueOf(cal.get(Calendar.YEAR)));
			th.setMonth(String.valueOf(cal.get(Calendar.MONTH) + 1));

			th = createEntity(th);
			if(th == null) throw new ServiceException("Transaction failed");

			// arhed transfer from
			Arhed a = new Arhed();
			a.setAccount(reqDto.getFromAccount());
			a.setRowSign(-1);
			a.setAmount(reqDto.getAmount());
			a.setTransactionCharge(reqDto.getTransactionCharge());
			a.setTransactionDate(cal.getTime());
			a.setTransactionTime(cal.getTime());
			a.setTrnHistory(th);

			a.setCreatedBy(getLoggedInUserDetails().getUsername());
			a.setCreatedOn(new Date());
			a.setUpdatedBy(getLoggedInUserDetails().getUsername());
			a.setUpdatedOn(new Date());
			a.setUser(getLoggedInUser());

			a = arhedRepo.save(a);
			if(a == null) throw new ServiceException("Transaction failed");

			// arhed transfer to
			a = new Arhed();
			a.setAccount(reqDto.getToAccount());
			a.setRowSign(1);
			a.setAmount(reqDto.getAmount());
			a.setTransactionCharge(reqDto.getTransactionCharge());
			a.setTransactionDate(cal.getTime());
			a.setTransactionTime(cal.getTime());
			a.setTrnHistory(th);

			a.setCreatedBy(getLoggedInUserDetails().getUsername());
			a.setCreatedOn(new Date());
			a.setUpdatedBy(getLoggedInUserDetails().getUsername());
			a.setUpdatedOn(new Date());
			a.setUser(getLoggedInUser());

			a = arhedRepo.save(a);
			if(a == null) throw new ServiceException("Transaction failed");
		}

		return getSuccessResponse("Transaction successfull", new TrnHistoryResDto(th));
	}

	@Transactional
	@Override
	public Response<TrnHistoryResDto> update(Long id, TrnHistoryReqDto reqDto) throws ServiceException {
		if(id == null) throw new ServiceException("Id required");

		Optional<TrnHistory> thOp = trnHistoryRepo.findByIdAndUser(id, getLoggedInUser());
		if(thOp.isEmpty()) throw new ServiceException("Transaction history not found");

		Optional<Arhed> arhedOp = arhedRepo.findByTrnHistoryAndUser(thOp.get(), getLoggedInUser());
		if(arhedOp.isEmpty()) throw new ServiceException("Transaction history not found");

		trnHistoryRepo.delete(thOp.get());
		arhedRepo.delete(arhedOp.get());

		Response<TrnHistoryResDto> response = save(reqDto);
		response.setMessage("Transaction updated successfully");
		return response;
	}

	@Override
	public Response<TrnHistoryResDto> getAll() throws ServiceException {
		List<TrnHistory> list = trnHistoryRepo.findAllByUser(getLoggedInUser());
		if (list == null || list.isEmpty()) return getErrorResponse("No transactions found");
		list.sort(Comparator.comparing(TrnHistory::getTransactionDate).reversed());
		return getSuccessResponse("Found List of transaction history", list.stream().map(data -> new ModelMapper().map(data, TrnHistoryResDto.class)).collect(Collectors.toList()));
	}

	@Transactional
	@Override
	public Response<TrnHistoryResDto> delete(Long id) throws ServiceException {
		if(id == null) throw new ServiceException("Id required");

		Optional<TrnHistory> thOp = trnHistoryRepo.findByIdAndUser(id, getLoggedInUser());
		if(thOp.isEmpty()) throw new ServiceException("Transaction history not found");

		Optional<Arhed> arhedOp = arhedRepo.findByTrnHistoryAndUser(thOp.get(), getLoggedInUser());
		if(arhedOp.isEmpty()) throw new ServiceException("Transaction history not found");

		trnHistoryRepo.delete(thOp.get());
		arhedRepo.delete(arhedOp.get());

		return getSuccessResponse("Transaction deleted successfully");
	}

}
