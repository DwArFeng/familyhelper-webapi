package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionDriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计驱动器信息响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionDriverInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    InspectionDriverInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(InspectionDriverInfo inspectionDriverInfo) throws ServiceException;

    void update(InspectionDriverInfo inspectionDriverInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<InspectionDriverInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<InspectionDriverInfo> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispInspectionDriverInfo getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispInspectionDriverInfo> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispInspectionDriverInfo> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;
}
