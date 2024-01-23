package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.MemoRemindDriverSupport;
import com.dwarfeng.familyhelper.project.stack.service.MemoRemindDriverSupportMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.MemoRemindDriverSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemoRemindDriverSupportResponseServiceImpl implements MemoRemindDriverSupportResponseService {

    private final MemoRemindDriverSupportMaintainService memoRemindDriverSupportMaintainService;

    public MemoRemindDriverSupportResponseServiceImpl(
            @Qualifier("familyhelperProjectMemoRemindDriverSupportMaintainService")
            MemoRemindDriverSupportMaintainService memoRemindDriverSupportMaintainService
    ) {
        this.memoRemindDriverSupportMaintainService = memoRemindDriverSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return memoRemindDriverSupportMaintainService.exists(key);
    }

    @Override
    public MemoRemindDriverSupport get(StringIdKey key) throws ServiceException {
        return memoRemindDriverSupportMaintainService.get(key);
    }

    @Override
    public PagedData<MemoRemindDriverSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return memoRemindDriverSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<MemoRemindDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return memoRemindDriverSupportMaintainService.lookup(
                MemoRemindDriverSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
