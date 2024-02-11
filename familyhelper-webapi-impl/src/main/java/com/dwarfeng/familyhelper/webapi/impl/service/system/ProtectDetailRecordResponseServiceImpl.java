package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.service.ProtectDetailRecordMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.ProtectDetailRecordResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProtectDetailRecordResponseServiceImpl implements ProtectDetailRecordResponseService {

    private final ProtectDetailRecordMaintainService protectDetailRecordMaintainService;

    public ProtectDetailRecordResponseServiceImpl(
            @Qualifier("acckeeperProtectDetailRecordMaintainService")
            ProtectDetailRecordMaintainService protectDetailRecordMaintainService
    ) {
        this.protectDetailRecordMaintainService = protectDetailRecordMaintainService;
    }

    @Override
    public boolean exists(RecordKey key) throws ServiceException {
        return protectDetailRecordMaintainService.exists(key);
    }

    @Override
    public ProtectDetailRecord get(RecordKey key) throws ServiceException {
        return protectDetailRecordMaintainService.get(key);
    }

    @Override
    public PagedData<ProtectDetailRecord> all(PagingInfo pagingInfo) throws ServiceException {
        return protectDetailRecordMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ProtectDetailRecord> childForLoginHistory(LongIdKey loginHistoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        return protectDetailRecordMaintainService.lookup(
                ProtectDetailRecordMaintainService.CHILD_FOR_LOGIN_HISTORY_RECORD_ID_ASC,
                new Object[]{loginHistoryKey},
                pagingInfo
        );
    }
}
