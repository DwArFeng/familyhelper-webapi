package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispPermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限组响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionGroupResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    PermissionGroup get(StringIdKey key) throws ServiceException;

    StringIdKey insert(PermissionGroup permissionGroup) throws ServiceException;

    void update(PermissionGroup permissionGroup) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    void nestedDelete(StringIdKey key) throws ServiceException;

    PagedData<PermissionGroup> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PermissionGroup> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PermissionGroup> childForParent(StringIdKey parentKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PermissionGroup> childForRoot(PagingInfo pagingInfo) throws ServiceException;

    DispPermissionGroup getDisp(StringIdKey key) throws ServiceException;

    PagedData<DispPermissionGroup> idLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPermissionGroup> childForParentDisp(StringIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPermissionGroup> childForRootDisp(PagingInfo pagingInfo) throws ServiceException;
}
