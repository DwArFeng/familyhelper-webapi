package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectorInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计器信息响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectorInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    InspectorInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(InspectorInfo inspectorInfo) throws ServiceException;

    void update(InspectorInfo inspectorInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<InspectorInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<InspectorInfo> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispInspectorInfo getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispInspectorInfo> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispInspectorInfo> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;
}
