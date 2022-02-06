package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.TaskTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 任务类型指示器响应服务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public interface TaskTypeIndicatorResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    TaskTypeIndicator get(StringIdKey key) throws ServiceException;

    StringIdKey insert(TaskTypeIndicator taskTypeIndicator) throws ServiceException;

    void update(TaskTypeIndicator taskTypeIndicator) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<TaskTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException;
}
