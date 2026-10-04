package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTaskEvent;
import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.TaskEventResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.logicengine.stack.service.TaskEventMaintainService;
import com.dwarfeng.logicengine.stack.service.TaskMaintainService;
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
public class TaskEventResponseServiceImpl implements TaskEventResponseService {

    private final TaskEventMaintainService taskEventMaintainService;
    private final TaskMaintainService taskMaintainService;

    public TaskEventResponseServiceImpl(
            @Qualifier("logicengineTaskEventMaintainService") TaskEventMaintainService taskEventMaintainService,
            @Qualifier("logicengineTaskMaintainService") TaskMaintainService taskMaintainService
    ) {
        this.taskEventMaintainService = taskEventMaintainService;
        this.taskMaintainService = taskMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return taskEventMaintainService.exists(key);
    }

    @Override
    public TaskEvent get(LongIdKey key) throws ServiceException {
        return taskEventMaintainService.get(key);
    }

    @Override
    public PagedData<TaskEvent> all(PagingInfo pagingInfo) throws ServiceException {
        return taskEventMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<TaskEvent> childForTask(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException {
        return taskEventMaintainService.lookup(
                TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo
        );
    }

    @Override
    public DispTaskEvent getDisp(LongIdKey key) throws ServiceException {
        TaskEvent taskEvent = taskEventMaintainService.get(key);
        return toDisp(taskEvent);
    }

    @Override
    public PagedData<DispTaskEvent> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<TaskEvent> lookup = taskEventMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispTaskEvent> childForTaskDisp(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<TaskEvent> lookup = taskEventMaintainService.lookup(
                TaskEventMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispTaskEvent toDisp(TaskEvent taskEvent) throws ServiceException {
        if (Objects.isNull(taskEvent)) {
            return null;
        }
        LongIdKey taskKey = taskEvent.getTaskKey();
        Task task = null;
        if (Objects.nonNull(taskKey)) {
            task = taskMaintainService.getIfExists(taskKey);
        }
        return DispTaskEvent.of(taskEvent, task);
    }

    private PagedData<DispTaskEvent> toDispPagedData(PagedData<TaskEvent> pagedData) throws ServiceException {
        List<DispTaskEvent> dispTaskEvents = new ArrayList<>(pagedData.getData().size());
        for (TaskEvent taskEvent : pagedData.getData()) {
            dispTaskEvents.add(toDisp(taskEvent));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispTaskEvents
        );
    }
}
