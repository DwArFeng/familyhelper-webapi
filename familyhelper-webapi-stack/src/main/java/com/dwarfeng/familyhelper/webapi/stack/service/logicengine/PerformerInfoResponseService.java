package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispPerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 执行器信息响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface PerformerInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    PerformerInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(PerformerInfo performerInfo) throws ServiceException;

    void update(PerformerInfo performerInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<PerformerInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PerformerInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PerformerInfo> childForAnchorState(StateKey anchorStateKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PerformerInfo> childForTargetState(StateKey targetStateKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispPerformerInfo getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispPerformerInfo> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPerformerInfo> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPerformerInfo> childForAnchorStateDisp(StateKey anchorStateKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPerformerInfo> childForTargetStateDisp(StateKey targetStateKey, PagingInfo pagingInfo)
            throws ServiceException;
}
