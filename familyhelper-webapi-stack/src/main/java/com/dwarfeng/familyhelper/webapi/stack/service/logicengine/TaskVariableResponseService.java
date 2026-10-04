package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTaskVariable;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 任务变量响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface TaskVariableResponseService extends Service {

    boolean exists(TaskVariableKey key) throws ServiceException;

    TaskVariable get(TaskVariableKey key) throws ServiceException;

    PagedData<TaskVariable> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<TaskVariable> childForTask(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException;

    DispTaskVariable getDisp(TaskVariableKey key) throws ServiceException;

    PagedData<DispTaskVariable> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispTaskVariable> childForTaskDisp(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException;
}
