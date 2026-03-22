package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 用户响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface UserResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    User get(StringIdKey key) throws ServiceException;

    StringIdKey insert(User entity) throws ServiceException;

    void update(User entity) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<User> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<User> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
