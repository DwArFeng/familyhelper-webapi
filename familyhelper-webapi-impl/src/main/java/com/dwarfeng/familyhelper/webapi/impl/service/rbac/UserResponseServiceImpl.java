package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.UserResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserResponseServiceImpl implements UserResponseService {

    private final UserMaintainService userMaintainService;

    public UserResponseServiceImpl(
            @Qualifier("rbacUserMaintainService") UserMaintainService userMaintainService
    ) {
        this.userMaintainService = userMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return userMaintainService.exists(key);
    }

    @Override
    public User get(StringIdKey key) throws ServiceException {
        return userMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(User entity) throws ServiceException {
        return userMaintainService.insert(entity);
    }

    @Override
    public void update(User entity) throws ServiceException {
        userMaintainService.update(entity);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        userMaintainService.delete(key);
    }

    @Override
    public PagedData<User> all(PagingInfo pagingInfo) throws ServiceException {
        return userMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<User> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return userMaintainService.lookup(
                UserMaintainService.ID_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }
}
