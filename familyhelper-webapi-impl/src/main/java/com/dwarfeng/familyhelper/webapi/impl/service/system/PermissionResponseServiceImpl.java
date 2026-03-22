package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectResult;
import com.dwarfeng.familyhelper.webapi.stack.service.system.PermissionResponseService;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionViewOfUserInspectInfo;
import com.dwarfeng.rbacds.stack.bean.key.ScopedUserKey;
import com.dwarfeng.rbacds.stack.service.InspectService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("systemPermissionResponseService")
public class PermissionResponseServiceImpl implements PermissionResponseService {

    private final InspectService inspectService;

    public PermissionResponseServiceImpl(
            @Qualifier("rbacInspectService") InspectService inspectService
    ) {
        this.inspectService = inspectService;
    }

    @Override
    public PermissionInspectResult inspectPermission(PermissionInspectInfo info) throws ServiceException {
        // 将 PermissionInspectInfo 转换为 PermissionViewOfUserInspectInfo。
        PermissionViewOfUserInspectInfo rbacdsInfo = toRbacdsPermissionViewOfUserInspectInfo(info);

        // 调用 InspectService 的 inspectPermissionViewOfUser 方法进行权限查看。
        com.dwarfeng.rbacds.stack.bean.dto.PermissionViewOfUserInspectResult rbacdsResult =
                inspectService.inspectPermissionViewOfUser(rbacdsInfo);

        // 将 com.dwarfeng.rbacds.stack.bean.dto.PermissionViewOfUserInspectResult 转换为 PermissionInspectResult。
        return toWebapiPermissionInspectResult(rbacdsResult);
    }

    private PermissionViewOfUserInspectInfo toRbacdsPermissionViewOfUserInspectInfo(PermissionInspectInfo info) {
        if (Objects.isNull(info)) {
            return null;
        } else {
            return new PermissionViewOfUserInspectInfo(
                    new ScopedUserKey(info.getScopeKey().getStringId(), info.getAccountKey().getStringId()),
                    null, null, null, false
            );
        }
    }

    private PermissionInspectResult toWebapiPermissionInspectResult(
            com.dwarfeng.rbacds.stack.bean.dto.PermissionViewOfUserInspectResult result
    ) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new PermissionInspectResult(result.getMatchedPermissions());
        }
    }
}
