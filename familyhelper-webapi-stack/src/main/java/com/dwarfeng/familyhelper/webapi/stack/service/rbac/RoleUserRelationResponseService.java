package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispRoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 角色用户关系响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RoleUserRelationResponseService extends Service {

    boolean exists(RoleUserRelationKey key) throws ServiceException;

    RoleUserRelation get(RoleUserRelationKey key) throws ServiceException;

    RoleUserRelationKey insert(RoleUserRelation entity) throws ServiceException;

    void update(RoleUserRelation entity) throws ServiceException;

    void delete(RoleUserRelationKey key) throws ServiceException;

    PagedData<RoleUserRelation> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<RoleUserRelation> childForRole(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<RoleUserRelation> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    DispRoleUserRelation getDisp(RoleUserRelationKey key) throws ServiceException;

    PagedData<DispRoleUserRelation> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispRoleUserRelation> childForRoleDisp(StringIdKey roleKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispRoleUserRelation> childForUserDisp(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException;
}
