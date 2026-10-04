package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTaskEvent;
import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 任务事件响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface TaskEventResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    TaskEvent get(LongIdKey key) throws ServiceException;

    PagedData<TaskEvent> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<TaskEvent> childForTask(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException;

    DispTaskEvent getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispTaskEvent> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispTaskEvent> childForTaskDisp(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException;
}
