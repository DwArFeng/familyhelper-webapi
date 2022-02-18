package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.TaskCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.TaskUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Task;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispTask;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 任务响应服务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public interface TaskResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Task get(LongIdKey key) throws ServiceException;

    PagedData<Task> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Task> childForProject(LongIdKey projectKey, PagingInfo pagingInfo) throws ServiceException;

    DispTask getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispTask> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispTask> childForProjectDisp(StringIdKey accountKey, LongIdKey projectKey, PagingInfo pagingInfo)
            throws ServiceException;

    LongIdKey createTask(StringIdKey userKey, TaskCreateInfo taskCreateInfo) throws ServiceException;

    void updateTask(StringIdKey userKey, TaskUpdateInfo taskUpdateInfo) throws ServiceException;

    void removeTask(StringIdKey userKey, LongIdKey taskKey) throws ServiceException;
}
