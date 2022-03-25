package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.TaskCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.TaskUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Task;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TaskTypeIndicator;
import com.dwarfeng.familyhelper.project.stack.service.TaskMaintainService;
import com.dwarfeng.familyhelper.project.stack.service.TaskOperateService;
import com.dwarfeng.familyhelper.project.stack.service.TaskTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispTask;
import com.dwarfeng.familyhelper.webapi.stack.service.project.ProjectResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.TaskResponseService;
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

@Service
public class TaskResponseServiceImpl implements TaskResponseService {

    private final TaskMaintainService taskMaintainService;
    private final TaskOperateService taskOperateService;
    private final TaskTypeIndicatorMaintainService taskTypeIndicatorMaintainService;

    private final ProjectResponseService projectResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TaskResponseServiceImpl(
            @Qualifier("familyhelperProjectTaskMaintainService") TaskMaintainService taskMaintainService,
            @Qualifier("familyhelperProjectTaskOperateService") TaskOperateService taskOperateService,
            @Qualifier("familyhelperProjectTaskTypeIndicatorMaintainService")
                    TaskTypeIndicatorMaintainService taskTypeIndicatorMaintainService,
            ProjectResponseService projectResponseService
    ) {
        this.taskMaintainService = taskMaintainService;
        this.taskOperateService = taskOperateService;
        this.taskTypeIndicatorMaintainService = taskTypeIndicatorMaintainService;
        this.projectResponseService = projectResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return taskMaintainService.exists(key);
    }

    @Override
    public Task get(LongIdKey key) throws ServiceException {
        return taskMaintainService.get(key);
    }

    @Override
    public PagedData<Task> all(PagingInfo pagingInfo) throws ServiceException {
        return taskMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Task> childForProject(LongIdKey projectKey, PagingInfo pagingInfo) throws ServiceException {
        return taskMaintainService.lookup(
                TaskMaintainService.CHILD_FOR_PROJECT, new Object[]{projectKey}, pagingInfo
        );
    }

    @Override
    public DispTask getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Task task = taskMaintainService.get(key);
        return dispTaskFromTask(task, inspectAccountKey);

    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispTask> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<Task> lookup = taskMaintainService.lookup(pagingInfo);
        List<DispTask> dispTasks = new ArrayList<>();
        for (Task task : lookup.getData()) {
            dispTasks.add(dispTaskFromTask(task, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispTasks
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispTask> childForProjectDisp(StringIdKey accountKey, LongIdKey projectKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Task> lookup = taskMaintainService.lookup(
                TaskMaintainService.CHILD_FOR_PROJECT, new Object[]{projectKey}, pagingInfo
        );
        List<DispTask> dispTasks = new ArrayList<>();
        for (Task task : lookup.getData()) {
            dispTasks.add(dispTaskFromTask(task, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispTasks
        );
    }

    private DispTask dispTaskFromTask(Task task, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispProject project = null;
        if (Objects.nonNull(task.getProjectKey())) {
            project = projectResponseService.getDisp(task.getProjectKey(), inspectAccountKey);
        }

        TaskTypeIndicator typeIndicator = null;
        if (Objects.nonNull(task.getType())) {
            typeIndicator = taskTypeIndicatorMaintainService.getIfExists(new StringIdKey(task.getType()));
        }

        return DispTask.of(task, project, typeIndicator);
    }

    @Override
    public LongIdKey createTask(StringIdKey userKey, TaskCreateInfo taskCreateInfo) throws ServiceException {
        return taskOperateService.createTask(userKey, taskCreateInfo);
    }

    @Override
    public void updateTask(StringIdKey userKey, TaskUpdateInfo taskUpdateInfo) throws ServiceException {
        taskOperateService.updateTask(userKey, taskUpdateInfo);
    }

    @Override
    public void removeTask(StringIdKey userKey, LongIdKey taskKey) throws ServiceException {
        taskOperateService.removeTask(userKey, taskKey);
    }
}
