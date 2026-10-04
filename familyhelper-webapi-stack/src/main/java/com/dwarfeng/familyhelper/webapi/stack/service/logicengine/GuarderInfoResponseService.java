package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispGuarderInfo;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderInfo;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 守卫器信息响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface GuarderInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    GuarderInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(GuarderInfo guarderInfo) throws ServiceException;

    void update(GuarderInfo guarderInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<GuarderInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<GuarderInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<GuarderInfo> childForAnchorState(StateKey anchorStateKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<GuarderInfo> childForTargetState(StateKey targetStateKey, PagingInfo pagingInfo) throws ServiceException;

    DispGuarderInfo getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispGuarderInfo> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispGuarderInfo> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispGuarderInfo> childForAnchorStateDisp(StateKey anchorStateKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispGuarderInfo> childForTargetStateDisp(StateKey targetStateKey, PagingInfo pagingInfo)
            throws ServiceException;
}
