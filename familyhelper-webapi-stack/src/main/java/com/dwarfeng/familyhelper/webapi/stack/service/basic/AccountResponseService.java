package com.dwarfeng.familyhelper.webapi.stack.service.basic;

import com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic.PasswordResetInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic.PasswordUpdateInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic.RegisterInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.basic.Account;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 账号响应服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface AccountResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Account get(StringIdKey key) throws ServiceException;

    void update(Account account) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<Account> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Account> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    void register(RegisterInfo registerInfo) throws ServiceException;

    void updatePassword(StringIdKey key, PasswordUpdateInfo passwordUpdateInfo) throws ServiceException;

    void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException;
}
