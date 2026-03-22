package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import javax.annotation.Nullable;

/**
 * 查看响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface InspectResponseService extends Service {

    /**
     * 以权限为依据查看用户视图。
     *
     * <p>
     * 该方法返回以指定的权限为依据，查看用户视图的查看结果。<br>
     * 如果指定的权限不存在，则返回 <code>null</code>。
     *
     * @param info 以权限为依据的用户视图查看信息。
     * @return 以权限为依据的用户视图查看结果。
     * @throws ServiceException 服务异常。
     * @since 2.0.2
     */
    @Nullable
    UserViewOfPermissionInspectResult inspectUserViewOfPermission(UserViewOfPermissionInspectInfo info)
            throws ServiceException;

    /**
     * 以角色为依据查看权限视图。
     *
     * <p>
     * 该方法返回以指定的角色为依据，查看权限视图的查看结果。<br>
     * 如果指定的角色不存在，则返回 <code>null</code>。
     *
     * @param info 以角色为依据的权限视图查看信息。
     * @return 以角色为依据的权限视图查看结果。
     * @throws ServiceException 服务异常。
     * @since 2.0.2
     */
    @Nullable
    PermissionViewOfRoleInspectResult inspectPermissionViewOfRole(PermissionViewOfRoleInspectInfo info)
            throws ServiceException;

    /**
     * 以用户为依据查看权限视图。
     *
     * <p>
     * 该方法返回以指定的用户为依据，查看权限视图的查看结果。<br>
     * 如果指定的用户不存在，则返回 <code>null</code>。
     *
     * @param info 以用户为依据的权限视图查看信息。
     * @return 以用户为依据的权限视图查看结果。
     * @throws ServiceException 服务异常。
     * @since 2.0.2
     */
    @Nullable
    PermissionViewOfUserInspectResult inspectPermissionViewOfUser(PermissionViewOfUserInspectInfo info)
            throws ServiceException;
}
