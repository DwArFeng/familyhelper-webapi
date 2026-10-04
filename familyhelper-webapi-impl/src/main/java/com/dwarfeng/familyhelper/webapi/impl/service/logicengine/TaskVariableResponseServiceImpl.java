package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTaskVariable;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.TaskVariableResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskVariableMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskVariableResponseServiceImpl implements TaskVariableResponseService {

    private final TaskVariableMaintainService taskVariableMaintainService;
    private final TaskMaintainService taskMaintainService;

    public TaskVariableResponseServiceImpl(
            @Qualifier("logicengineTaskVariableMaintainService")
            TaskVariableMaintainService taskVariableMaintainService,
            @Qualifier("logicengineTaskMaintainService") TaskMaintainService taskMaintainService
    ) {
        this.taskVariableMaintainService = taskVariableMaintainService;
        this.taskMaintainService = taskMaintainService;
    }

    @Override
    public boolean exists(TaskVariableKey key) throws ServiceException {
        return taskVariableMaintainService.exists(key);
    }

    @Override
    public TaskVariable get(TaskVariableKey key) throws ServiceException {
        return taskVariableMaintainService.get(key);
    }

    @Override
    public PagedData<TaskVariable> all(PagingInfo pagingInfo) throws ServiceException {
        return taskVariableMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<TaskVariable> childForTask(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException {
        return taskVariableMaintainService.lookup(
                TaskVariableMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo
        );
    }

    @Override
    public DispTaskVariable getDisp(TaskVariableKey key) throws ServiceException {
        TaskVariable taskVariable = taskVariableMaintainService.get(key);
        return toDisp(taskVariable);
    }

    @Override
    public PagedData<DispTaskVariable> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<TaskVariable> lookup = taskVariableMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispTaskVariable> childForTaskDisp(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<TaskVariable> lookup = taskVariableMaintainService.lookup(
                TaskVariableMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispTaskVariable toDisp(TaskVariable taskVariable) throws ServiceException {
        if (Objects.isNull(taskVariable)) {
            return null;
        }
        TaskVariableKey key = taskVariable.getKey();
        LongIdKey taskKey = Objects.nonNull(key) && Objects.nonNull(key.getTaskLongId())
                ? new LongIdKey(key.getTaskLongId())
                : null;
        Task task = null;
        if (Objects.nonNull(taskKey)) {
            task = taskMaintainService.getIfExists(taskKey);
        }
        return DispTaskVariable.of(taskVariable, task);
    }

    private PagedData<DispTaskVariable> toDispPagedData(PagedData<TaskVariable> pagedData) throws ServiceException {
        List<DispTaskVariable> dispTaskVariables = new ArrayList<>(pagedData.getData().size());
        for (TaskVariable taskVariable : pagedData.getData()) {
            dispTaskVariables.add(toDisp(taskVariable));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispTaskVariables
        );
    }
}
