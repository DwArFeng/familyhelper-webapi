package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.assets.sdk.util.Constants;
import com.dwarfeng.familyhelper.project.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.PermissionUpsertInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.ProjectCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.ProjectUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Pop;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Project;
import com.dwarfeng.familyhelper.project.stack.service.PopMaintainService;
import com.dwarfeng.familyhelper.project.stack.service.ProjectMaintainService;
import com.dwarfeng.familyhelper.project.stack.service.ProjectOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.project.ProjectResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectResponseServiceImpl implements ProjectResponseService {

    private final ProjectMaintainService projectMaintainService;
    private final PopMaintainService popMaintainService;
    private final ProjectOperateService projectOperateService;

    private final AccountResponseService accountResponseService;

    public ProjectResponseServiceImpl(
            @Qualifier("familyhelperProjectProjectMaintainService") ProjectMaintainService projectMaintainService,
            @Qualifier("familyhelperProjectPopMaintainService") PopMaintainService popMaintainService,
            @Qualifier("familyhelperProjectProjectOperateService") ProjectOperateService projectOperateService,
            AccountResponseService accountResponseService
    ) {
        this.projectMaintainService = projectMaintainService;
        this.popMaintainService = popMaintainService;
        this.projectOperateService = projectOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return projectMaintainService.exists(key);
    }

    @Override
    public Project get(LongIdKey key) throws ServiceException {
        return projectMaintainService.get(key);
    }

    @Override
    public PagedData<Project> all(PagingInfo pagingInfo) throws ServiceException {
        return projectMaintainService.lookup(pagingInfo);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<Project> allPermitted(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<Pop> lookup = popMaintainService.lookup(
                PopMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<Project> projects = new ArrayList<>();
        for (Pop pop : lookup.getData()) {
            projects.add(projectMaintainService.get(new LongIdKey(pop.getKey().getLongId())));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), projects
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<Project> allOwned(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<Pop> lookup = popMaintainService.lookup(
                PopMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Constants.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        List<Project> projects = new ArrayList<>();
        for (Pop pop : lookup.getData()) {
            projects.add(projectMaintainService.get(new LongIdKey(pop.getKey().getLongId())));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), projects
        );
    }

    @Override
    public DispProject getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Project project = projectMaintainService.get(key);
        return dispProjectFromProject(project, inspectAccountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispProject> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Project> lookup = allPermitted(accountKey, pagingInfo);
        List<DispProject> dispProjects = new ArrayList<>();
        for (Project project : lookup.getData()) {
            dispProjects.add(dispProjectFromProject(project, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispProjects
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispProject> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Project> lookup = allOwned(accountKey, pagingInfo);
        List<DispProject> dispProjects = new ArrayList<>();
        for (Project project : lookup.getData()) {
            dispProjects.add(dispProjectFromProject(project, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispProjects
        );
    }

    private DispProject dispProjectFromProject(Project project, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<Pop> relatedPops = popMaintainService.lookup(
                PopMaintainService.CHILD_FOR_PROJECT, new Object[]{project.getKey()}
        ).getData();
        Pop ownerPop = relatedPops.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Pop myPop = relatedPops.stream().filter(
                p -> Objects.equals(p.getKey().getStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPop)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPop.getKey().getStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPop).map(Pop::getPermissionLevel).orElse(null);
        return DispProject.of(project, ownerAccount, permissionLevel);
    }

    @Override
    public LongIdKey createProject(StringIdKey userKey, ProjectCreateInfo projectCreateInfo)
            throws ServiceException {
        return projectOperateService.createProject(userKey, projectCreateInfo);
    }

    @Override
    public void updateProject(StringIdKey userKey, ProjectUpdateInfo projectUpdateInfo)
            throws ServiceException {
        projectOperateService.updateProject(userKey, projectUpdateInfo);
    }

    @Override
    public void removeProject(StringIdKey userKey, LongIdKey projectKey) throws ServiceException {
        projectOperateService.removeProject(userKey, projectKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, PermissionUpsertInfo permissionUpsertInfo) throws ServiceException {
        projectOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, PermissionRemoveInfo permissionRemoveInfo) throws ServiceException {
        projectOperateService.removePermission(userKey, permissionRemoveInfo);
    }
}
