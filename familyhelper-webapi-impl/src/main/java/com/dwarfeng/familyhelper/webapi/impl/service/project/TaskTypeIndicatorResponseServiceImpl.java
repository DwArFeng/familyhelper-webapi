package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.TaskTypeIndicator;
import com.dwarfeng.familyhelper.project.stack.service.TaskTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.TaskTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TaskTypeIndicatorResponseServiceImpl implements TaskTypeIndicatorResponseService {

    private final TaskTypeIndicatorMaintainService taskTypeIndicatorMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TaskTypeIndicatorResponseServiceImpl(
            @Qualifier("familyhelperProjectTaskTypeIndicatorMaintainService")
                    TaskTypeIndicatorMaintainService taskTypeIndicatorMaintainService
    ) {
        this.taskTypeIndicatorMaintainService = taskTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return taskTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public TaskTypeIndicator get(StringIdKey key) throws ServiceException {
        return taskTypeIndicatorMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(TaskTypeIndicator taskTypeIndicator) throws ServiceException {
        return taskTypeIndicatorMaintainService.insert(taskTypeIndicator);
    }

    @Override
    public void update(TaskTypeIndicator taskTypeIndicator) throws ServiceException {
        taskTypeIndicatorMaintainService.update(taskTypeIndicator);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        taskTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<TaskTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return taskTypeIndicatorMaintainService.lookup(pagingInfo);
    }
}
