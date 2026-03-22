package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.ProtectDetailRecordMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectDetailRecord;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ProtectDetailRecordResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProtectDetailRecordResponseServiceImpl implements ProtectDetailRecordResponseService {

    private final ProtectDetailRecordMaintainService protectDetailRecordMaintainService;
    private final LoginHistoryMaintainService loginHistoryMaintainService;

    public ProtectDetailRecordResponseServiceImpl(
            @Qualifier("acckeeperProtectDetailRecordMaintainService")
            ProtectDetailRecordMaintainService protectDetailRecordMaintainService,
            @Qualifier("acckeeperLoginHistoryMaintainService")
            LoginHistoryMaintainService loginHistoryMaintainService
    ) {
        this.protectDetailRecordMaintainService = protectDetailRecordMaintainService;
        this.loginHistoryMaintainService = loginHistoryMaintainService;
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

    @Override
    public DispProtectDetailRecord getDisp(RecordKey key) throws ServiceException {
        ProtectDetailRecord protectDetailRecord = protectDetailRecordMaintainService.get(key);
        return toDisp(protectDetailRecord);
    }

    @Override
    public PagedData<DispProtectDetailRecord> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<ProtectDetailRecord> pagedData = protectDetailRecordMaintainService.lookup(pagingInfo);
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispProtectDetailRecord> childForLoginHistoryDisp(LongIdKey loginHistoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<ProtectDetailRecord> pagedData = protectDetailRecordMaintainService.lookup(
                ProtectDetailRecordMaintainService.CHILD_FOR_LOGIN_HISTORY_RECORD_ID_ASC,
                new Object[]{loginHistoryKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispProtectDetailRecord toDisp(ProtectDetailRecord protectDetailRecord) throws ServiceException {
        if (Objects.isNull(protectDetailRecord)) {
            return null;
        }
        RecordKey key = protectDetailRecord.getKey();
        LongIdKey loginHistoryKey = Objects.nonNull(key) && Objects.nonNull(key.getLoginHistoryId())
                ? new LongIdKey(key.getLoginHistoryId())
                : null;
        LoginHistory loginHistory = null;
        if (Objects.nonNull(loginHistoryKey)) {
            loginHistory = loginHistoryMaintainService.getIfExists(loginHistoryKey);
        }
        return DispProtectDetailRecord.of(protectDetailRecord, loginHistory);
    }

    private PagedData<DispProtectDetailRecord> toDispPagedData(PagedData<ProtectDetailRecord> pagedData)
            throws ServiceException {
        List<DispProtectDetailRecord> dispProtectDetailRecords = new ArrayList<>(pagedData.getData().size());
        for (ProtectDetailRecord protectDetailRecord : pagedData.getData()) {
            dispProtectDetailRecords.add(toDisp(protectDetailRecord));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(),
                pagedData.getTotalPages(),
                pagedData.getRows(),
                pagedData.getCount(),
                dispProtectDetailRecords
        );
    }
}
