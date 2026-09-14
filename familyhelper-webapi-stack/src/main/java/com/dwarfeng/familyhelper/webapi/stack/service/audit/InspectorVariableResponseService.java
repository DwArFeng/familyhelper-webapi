package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectorVariable;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计器变量响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectorVariableResponseService extends Service {

    boolean exists(InspectorVariableKey key) throws ServiceException;

    InspectorVariable get(InspectorVariableKey key) throws ServiceException;

    PagedData<InspectorVariable> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<InspectorVariable> childForInspectorInfo(LongIdKey inspectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispInspectorVariable getDisp(InspectorVariableKey key) throws ServiceException;

    PagedData<DispInspectorVariable> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispInspectorVariable> childForInspectorInfoDisp(LongIdKey inspectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException;

    InspectorVariableInspectResult inspect(InspectorVariableInspectInfo info) throws ServiceException;

    void upsert(InspectorVariableUpsertInfo info) throws ServiceException;

    void remove(InspectorVariableRemoveInfo info) throws ServiceException;
}
