package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispState;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 状态响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface StateResponseService extends Service {

    boolean exists(StateKey key) throws ServiceException;

    State get(StateKey key) throws ServiceException;

    StateKey insert(State state) throws ServiceException;

    void update(State state) throws ServiceException;

    void delete(StateKey key) throws ServiceException;

    PagedData<State> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<State> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    DispState getDisp(StateKey key) throws ServiceException;

    PagedData<DispState> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispState> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;
}
