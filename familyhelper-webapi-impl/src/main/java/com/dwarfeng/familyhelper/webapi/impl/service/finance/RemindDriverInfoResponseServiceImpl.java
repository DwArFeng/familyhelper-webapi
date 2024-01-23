package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.RemindDriverInfo;
import com.dwarfeng.familyhelper.finance.stack.service.RemindDriverInfoMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.RemindDriverInfoResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RemindDriverInfoResponseServiceImpl implements RemindDriverInfoResponseService {

    private final RemindDriverInfoMaintainService remindDriverInfoMaintainService;

    public RemindDriverInfoResponseServiceImpl(
            @Qualifier("familyhelperFinanceRemindDriverInfoMaintainService")
            RemindDriverInfoMaintainService remindDriverInfoMaintainService
    ) {
        this.remindDriverInfoMaintainService = remindDriverInfoMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return remindDriverInfoMaintainService.exists(key);
    }

    @Override
    public RemindDriverInfo get(LongIdKey key) throws ServiceException {
        return remindDriverInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(RemindDriverInfo remindDriverInfo) throws ServiceException {
        return remindDriverInfoMaintainService.insert(remindDriverInfo);
    }

    @Override
    public void update(RemindDriverInfo remindDriverInfo) throws ServiceException {
        remindDriverInfoMaintainService.update(remindDriverInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        remindDriverInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<RemindDriverInfo> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return remindDriverInfoMaintainService.lookup(
                RemindDriverInfoMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBookKey}, pagingInfo
        );
    }
}
