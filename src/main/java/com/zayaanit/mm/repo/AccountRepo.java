package com.zayaanit.mm.repo;

import java.util.List;

import com.zayaanit.mm.entity.Account;
import com.zayaanit.mm.entity.User;
import com.zayaanit.mm.enums.AccountType;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
public interface AccountRepo extends ServiceRepository<Account> {

	List<Account> findAllByAccountTypeAndUser(AccountType accountType, User user);
}
