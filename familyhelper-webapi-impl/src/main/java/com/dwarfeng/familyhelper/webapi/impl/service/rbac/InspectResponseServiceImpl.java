package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.InspectResponseService;
import com.dwarfeng.rbacds.stack.bean.dto.*;
import com.dwarfeng.rbacds.stack.service.InspectService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class InspectResponseServiceImpl implements InspectResponseService {

    private final InspectService inspectService;

    public InspectResponseServiceImpl(
            @Qualifier("rbacInspectService") InspectService inspectService
    ) {
        this.inspectService = inspectService;
    }

    @Nullable
    @Override
    public UserViewOfPermissionInspectResult inspectUserViewOfPermission(UserViewOfPermissionInspectInfo info)
            throws ServiceException {
        return inspectService.inspectUserViewOfPermission(info);
    }

    @Nullable
    @Override
    public PermissionViewOfRoleInspectResult inspectPermissionViewOfRole(PermissionViewOfRoleInspectInfo info)
            throws ServiceException {
        return inspectService.inspectPermissionViewOfRole(info);
    }

    @Nullable
    @Override
    public PermissionViewOfUserInspectResult inspectPermissionViewOfUser(PermissionViewOfUserInspectInfo info)
            throws ServiceException {
        return inspectService.inspectPermissionViewOfUser(info);
    }
}
