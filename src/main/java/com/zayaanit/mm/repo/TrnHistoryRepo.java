package com.zayaanit.mm.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zayaanit.mm.entity.TrnHistory;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.enums.TransactionType;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
public interface TrnHistoryRepo extends ServiceRepository<TrnHistory> {

	List<TrnHistory> getAllByYearAndUser(String year, User user);
	List<TrnHistory> getAllByYearAndMonthAndUser(String year, String month, User user);
	List<TrnHistory> getAllByYearAndMonthAndTransactionTypeAndUser(String year, String month, TransactionType transactionType, User user);

	@Query(value = "SELECT SUM(amount) FROM TRN_HISTORY WHERE income_source_id = ?1 AND user_id = ?2 AND transactionType = 'INCOME'", nativeQuery = true)
	BigDecimal getTotalIncome(Long incomeSourceId, Long userId);

	@Query(value = "SELECT SUM(amount) FROM TRN_HISTORY WHERE expense_type_id = ?1 AND user_id = ?2 AND transactionType = 'EXPENSE'", nativeQuery = true)
	BigDecimal getTotalExpense(Long expenseId, Long userId);

	@Query(value = "SELECT SUM(amount * rowSign)-SUM(transactionCharge) FROM ARHED WHERE account_id=?1 AND user_id=?2 AND transactionDate <= ?3", nativeQuery = true)
	BigDecimal getCurrentBalance(Long accountId, Long userId, String date);
}
