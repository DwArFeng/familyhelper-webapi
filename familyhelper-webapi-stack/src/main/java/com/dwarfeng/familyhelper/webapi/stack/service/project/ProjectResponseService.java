package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.PermissionUpsertInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.ProjectCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.ProjectUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Project;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 工程响应服务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public interface ProjectResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Project get(LongIdKey key) throws ServiceException;

    PagedData<Project> all(PagingInfo pagingInfo) throws ServiceException;

    DispProject getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispProject> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispProject> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createProject(StringIdKey userKey, ProjectCreateInfo projectCreateInfo)
            throws ServiceException;

    void updateProject(StringIdKey userKey, ProjectUpdateInfo projectUpdateInfo) throws ServiceException;

    void removeProject(StringIdKey userKey, LongIdKey projectKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, PermissionUpsertInfo permissionUpsertInfo) throws ServiceException;

    void removePermission(StringIdKey userKey, PermissionRemoveInfo permissionRemoveInfo) throws ServiceException;
}
