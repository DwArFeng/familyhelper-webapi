package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectDetailRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 保护详细信息记录响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ProtectDetailRecordResponseService extends Service {

    boolean exists(RecordKey key) throws ServiceException;

    ProtectDetailRecord get(RecordKey key) throws ServiceException;

    PagedData<ProtectDetailRecord> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ProtectDetailRecord> childForLoginHistory(LongIdKey loginHistoryKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispProtectDetailRecord getDisp(RecordKey key) throws ServiceException;

    PagedData<DispProtectDetailRecord> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispProtectDetailRecord> childForLoginHistoryDisp(LongIdKey loginHistoryKey, PagingInfo pagingInfo)
            throws ServiceException;
}
