package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTask;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 任务响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface TaskResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Task get(LongIdKey key) throws ServiceException;

    PagedData<Task> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Task> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Task> childForCurrentState(StateKey currentStateKey, PagingInfo pagingInfo) throws ServiceException;

    DispTask getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispTask> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispTask> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispTask> childForCurrentStateDisp(StateKey currentStateKey, PagingInfo pagingInfo)
            throws ServiceException;
}
