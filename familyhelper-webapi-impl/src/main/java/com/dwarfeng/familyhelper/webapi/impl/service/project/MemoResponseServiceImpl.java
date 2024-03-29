package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Memo;
import com.dwarfeng.familyhelper.project.stack.service.MemoMaintainService;
import com.dwarfeng.familyhelper.project.stack.service.MemoOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.MemoResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemoResponseServiceImpl implements MemoResponseService {

    private final MemoMaintainService memoMaintainService;
    private final MemoOperateService memoOperateService;

    public MemoResponseServiceImpl(
            @Qualifier("familyhelperProjectMemoMaintainService") MemoMaintainService memoMaintainService,
            @Qualifier("familyhelperProjectMemoOperateService") MemoOperateService memoOperateService
    ) {
        this.memoMaintainService = memoMaintainService;
        this.memoOperateService = memoOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return memoMaintainService.exists(key);
    }

    @Override
    public Memo get(LongIdKey key) throws ServiceException {
        return memoMaintainService.get(key);
    }

    @Override
    public PagedData<Memo> all(PagingInfo pagingInfo) throws ServiceException {
        return memoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Memo> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_CREATED_DATE_DESC, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserInProgress(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_IN_PROGRESS_CREATED_DATE_DESC, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserFinished(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_FINISHED_CREATED_DATE_DESC, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserDefaultOrder(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_DEFAULT_ORDER, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserInProgressDefaultOrder(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_IN_PROGRESS_DEFAULT_ORDER, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserFinishedDefaultOrder(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_FINISHED_DEFAULT_ORDER, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserProfileLikeDefaultOrder(
            StringIdKey userKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_PROFILE_LIKE_DEFAULT_ORDER,
                new Object[]{userKey, pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserInProgressProfileLikeDefaultOrder(
            StringIdKey userKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_IN_PROGRESS_PROFILE_LIKE_DEFAULT_ORDER,
                new Object[]{userKey, pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<Memo> childForUserFinishedProfileLikeDefaultOrder(
            StringIdKey userKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return memoMaintainService.lookup(
                MemoMaintainService.CHILD_FOR_USER_FINISHED_PROFILE_LIKE_DEFAULT_ORDER,
                new Object[]{userKey, pattern},
                pagingInfo
        );
    }

    @Override
    public LongIdKey createMemo(StringIdKey userKey, MemoCreateInfo memoCreateInfo) throws ServiceException {
        return memoOperateService.createMemo(userKey, memoCreateInfo);
    }

    @Override
    public void updateMemo(StringIdKey userKey, MemoUpdateInfo memoUpdateInfo) throws ServiceException {
        memoOperateService.updateMemo(userKey, memoUpdateInfo);
    }

    @Override
    public void removeMemo(StringIdKey userKey, LongIdKey memoKey) throws ServiceException {
        memoOperateService.removeMemo(userKey, memoKey);
    }

    @Override
    public void finishMemo(StringIdKey userKey, LongIdKey memoKey) throws ServiceException {
        memoOperateService.finishMemo(userKey, memoKey);
    }

    @Override
    public void removeFinishedMemos(StringIdKey userKey) throws ServiceException {
        memoOperateService.removeFinishedMemos(userKey);
    }
}
