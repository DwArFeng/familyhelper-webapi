package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginParamRecordMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginParamRecord;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.LoginParamRecordResponseService;
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
public class LoginParamRecordResponseServiceImpl implements LoginParamRecordResponseService {

    private final LoginParamRecordMaintainService loginParamRecordMaintainService;
    private final LoginHistoryMaintainService loginHistoryMaintainService;

    public LoginParamRecordResponseServiceImpl(
            @Qualifier("acckeeperLoginParamRecordMaintainService")
            LoginParamRecordMaintainService loginParamRecordMaintainService,
            @Qualifier("acckeeperLoginHistoryMaintainService")
            LoginHistoryMaintainService loginHistoryMaintainService
    ) {
        this.loginParamRecordMaintainService = loginParamRecordMaintainService;
        this.loginHistoryMaintainService = loginHistoryMaintainService;
    }

    @Override
    public boolean exists(RecordKey key) throws ServiceException {
        return loginParamRecordMaintainService.exists(key);
    }

    @Override
    public LoginParamRecord get(RecordKey key) throws ServiceException {
        return loginParamRecordMaintainService.get(key);
    }

    @Override
    public PagedData<LoginParamRecord> all(PagingInfo pagingInfo) throws ServiceException {
        return loginParamRecordMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<LoginParamRecord> childForLoginHistory(LongIdKey loginHistoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        return loginParamRecordMaintainService.lookup(
                LoginParamRecordMaintainService.CHILD_FOR_LOGIN_HISTORY_RECORD_ID_ASC,
                new Object[]{loginHistoryKey},
                pagingInfo
        );
    }

    @Override
    public DispLoginParamRecord getDisp(RecordKey key) throws ServiceException {
        LoginParamRecord loginParamRecord = loginParamRecordMaintainService.get(key);
        return toDisp(loginParamRecord);
    }

    @Override
    public PagedData<DispLoginParamRecord> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<LoginParamRecord> pagedData = loginParamRecordMaintainService.lookup(pagingInfo);
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispLoginParamRecord> childForLoginHistoryDisp(LongIdKey loginHistoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<LoginParamRecord> pagedData = loginParamRecordMaintainService.lookup(
                LoginParamRecordMaintainService.CHILD_FOR_LOGIN_HISTORY_RECORD_ID_ASC,
                new Object[]{loginHistoryKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispLoginParamRecord toDisp(LoginParamRecord loginParamRecord) throws ServiceException {
        if (Objects.isNull(loginParamRecord)) {
            return null;
        }
        RecordKey key = loginParamRecord.getKey();
        LongIdKey loginHistoryKey = Objects.nonNull(key) && Objects.nonNull(key.getLoginHistoryId())
                ? new LongIdKey(key.getLoginHistoryId())
                : null;
        LoginHistory loginHistory = null;
        if (Objects.nonNull(loginHistoryKey)) {
            loginHistory = loginHistoryMaintainService.getIfExists(loginHistoryKey);
        }
        return DispLoginParamRecord.of(loginParamRecord, loginHistory);
    }

    private PagedData<DispLoginParamRecord> toDispPagedData(PagedData<LoginParamRecord> pagedData)
            throws ServiceException {
        List<DispLoginParamRecord> dispLoginParamRecords = new ArrayList<>(pagedData.getData().size());
        for (LoginParamRecord loginParamRecord : pagedData.getData()) {
            dispLoginParamRecords.add(toDisp(loginParamRecord));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispLoginParamRecords
        );
    }
}
