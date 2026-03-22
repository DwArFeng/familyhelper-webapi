package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPexp;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限表达式响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PexpResponseService extends Service {

    boolean exists(PexpKey key) throws ServiceException;

    Pexp get(PexpKey key) throws ServiceException;

    PagedData<Pexp> childForScope(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Pexp> childForRole(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Pexp> childForScopeChildForRole(StringIdKey scopeKey, StringIdKey roleKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispPexp getDisp(PexpKey key) throws ServiceException;

    PagedData<DispPexp> childForScopeDisp(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPexp> childForRoleDisp(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPexp> childForScopeChildForRoleDisp(
            StringIdKey scopeKey, StringIdKey roleKey, PagingInfo pagingInfo
    ) throws ServiceException;

    /**
     * 创建权限表达式。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws ServiceException 服务异常。
     */
    PexpCreateResult create(PexpCreateInfo info) throws ServiceException;

    /**
     * 更新权限表达式。
     *
     * @param info 更新信息。
     * @throws ServiceException 服务异常。
     */
    void update(PexpUpdateInfo info) throws ServiceException;

    /**
     * 移除权限表达式。
     *
     * @param info 移除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(PexpRemoveInfo info) throws ServiceException;
}
