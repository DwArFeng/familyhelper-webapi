package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.acckeeper.stack.service.LoginParamRecordMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginParamRecordResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginDetailRecordResponseServiceImpl implements LoginParamRecordResponseService {

    private final LoginParamRecordMaintainService loginParamRecordMaintainService;

    public LoginDetailRecordResponseServiceImpl(
            @Qualifier("acckeeperLoginParamRecordMaintainService")
            LoginParamRecordMaintainService loginParamRecordMaintainService
    ) {
        this.loginParamRecordMaintainService = loginParamRecordMaintainService;
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
}
