package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectResult;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionResponseService extends Service {

    /**
     * 查看权限信息。
     *
     * @param info 权限查看信息。
     * @return 权限查看结果。
     * @throws ServiceException 服务异常。
     */
    PermissionInspectResult inspectPermission(PermissionInspectInfo info) throws ServiceException;
}
