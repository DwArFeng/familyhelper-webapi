package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.AccountRegisterInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.AccountUpdateInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordResetInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordUpdateInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.system.Account;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 账号响应服务。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public interface AccountResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Account get(StringIdKey key) throws ServiceException;

    void addRoleRelation(StringIdKey accountKey, StringIdKey roleKey) throws ServiceException;

    void deleteRoleRelation(StringIdKey accountKey, StringIdKey roleKey) throws ServiceException;

    void resetRoleRelation(StringIdKey accountKey, List<StringIdKey> roleKeys) throws ServiceException;

    PagedData<Account> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Account> childForRole(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Account> childForProfileGuest(StringIdKey profileKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Account> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    DispAccount getDisp(StringIdKey subjectUserKey, StringIdKey objectUserKey) throws ServiceException;

    void register(AccountRegisterInfo accountRegisterInfo) throws ServiceException;

    void update(AccountUpdateInfo accountUpdateInfo) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws ServiceException;

    void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException;

    void invalid(StringIdKey key) throws ServiceException;
}
