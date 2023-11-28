package com.zayaanit.mm.service;

import com.zayaanit.mm.enums.AccountType;
import com.zayaanit.mm.service.exception.ServiceException;
import com.zayaanit.mm.util.Response;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
public interface AccountService<REQ, RES> extends BaseService<REQ, RES> {

	public Response<RES> getAllByAccountType(AccountType accountType) throws ServiceException;
}
