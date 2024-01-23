package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.MemoRemindDriverInfo;
import com.dwarfeng.familyhelper.project.stack.service.MemoRemindDriverInfoMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.MemoRemindDriverInfoResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemoRemindDriverInfoResponseServiceImpl implements MemoRemindDriverInfoResponseService {

    private final MemoRemindDriverInfoMaintainService memoRemindDriverInfoMaintainService;

    public MemoRemindDriverInfoResponseServiceImpl(
            @Qualifier("familyhelperProjectMemoRemindDriverInfoMaintainService")
            MemoRemindDriverInfoMaintainService memoRemindDriverInfoMaintainService
    ) {
        this.memoRemindDriverInfoMaintainService = memoRemindDriverInfoMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return memoRemindDriverInfoMaintainService.exists(key);
    }

    @Override
    public MemoRemindDriverInfo get(LongIdKey key) throws ServiceException {
        return memoRemindDriverInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(MemoRemindDriverInfo memoRemindDriverInfo) throws ServiceException {
        return memoRemindDriverInfoMaintainService.insert(memoRemindDriverInfo);
    }

    @Override
    public void update(MemoRemindDriverInfo memoRemindDriverInfo) throws ServiceException {
        memoRemindDriverInfoMaintainService.update(memoRemindDriverInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        memoRemindDriverInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<MemoRemindDriverInfo> childForMemo(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoRemindDriverInfoMaintainService.lookup(
                MemoRemindDriverInfoMaintainService.CHILD_FOR_MEMO, new Object[]{memoKey}, pagingInfo
        );
    }
}
