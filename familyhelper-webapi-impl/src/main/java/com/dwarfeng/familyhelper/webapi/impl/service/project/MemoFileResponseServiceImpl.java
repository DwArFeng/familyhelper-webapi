package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoFile;
import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoFileUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoFileUploadInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.MemoFileInfo;
import com.dwarfeng.familyhelper.project.stack.service.MemoFileInfoMaintainService;
import com.dwarfeng.familyhelper.project.stack.service.MemoFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.MemoFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MemoFileResponseServiceImpl implements MemoFileResponseService {

    private final MemoFileInfoMaintainService memoFileInfoMaintainService;
    private final MemoFileOperateService memoFileOperateService;

    public MemoFileResponseServiceImpl(
            @Qualifier("familyhelperProjectMemoFileInfoMaintainService")
                    MemoFileInfoMaintainService memoFileInfoMaintainService,
            @Qualifier("familyhelperProjectMemoFileOperateService")
                    MemoFileOperateService memoFileOperateService
    ) {
        this.memoFileInfoMaintainService = memoFileInfoMaintainService;
        this.memoFileOperateService = memoFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return memoFileInfoMaintainService.exists(key);
    }

    @Override
    public MemoFileInfo get(LongIdKey key) throws ServiceException {
        return memoFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<MemoFileInfo> childForMemo(LongIdKey memoKey, PagingInfo pagingInfo) throws ServiceException {
        return memoFileInfoMaintainService.lookup(
                MemoFileInfoMaintainService.CHILD_FOR_MEMO, new Object[]{memoKey}, pagingInfo
        );
    }

    @Override
    public PagedData<MemoFileInfo> childForMemoInspectedDateDesc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoFileInfoMaintainService.lookup(
                MemoFileInfoMaintainService.CHILD_FOR_MEMO_INSPECTED_DATE_DESC, new Object[]{memoKey}, pagingInfo
        );
    }

    @Override
    public PagedData<MemoFileInfo> childForMemoModifiedDateDesc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoFileInfoMaintainService.lookup(
                MemoFileInfoMaintainService.CHILD_FOR_MEMO_MODIFIED_DATE_DESC, new Object[]{memoKey}, pagingInfo
        );
    }

    @Override
    public PagedData<MemoFileInfo> childForMemoOriginNameAsc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoFileInfoMaintainService.lookup(
                MemoFileInfoMaintainService.CHILD_FOR_MEMO_ORIGIN_NAME_ASC, new Object[]{memoKey}, pagingInfo
        );
    }

    @Override
    public PagedData<MemoFileInfo> childForMemoCreatedDateAsc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return memoFileInfoMaintainService.lookup(
                MemoFileInfoMaintainService.CHILD_FOR_MEMO_CREATED_DATE_ASC, new Object[]{memoKey}, pagingInfo
        );
    }

    @Override
    public MemoFile downloadMemoFile(StringIdKey userKey, LongIdKey memoFileKey) throws ServiceException {
        return memoFileOperateService.downloadMemoFile(userKey, memoFileKey);
    }

    @Override
    public void uploadMemoFile(StringIdKey userKey, MemoFileUploadInfo memoFileUploadInfo) throws ServiceException {
        memoFileOperateService.uploadMemoFile(userKey, memoFileUploadInfo);
    }

    @Override
    public void updateMemoFile(StringIdKey userKey, MemoFileUpdateInfo memoFileUpdateInfo) throws ServiceException {
        memoFileOperateService.updateMemoFile(userKey, memoFileUpdateInfo);
    }

    @Override
    public void removeMemoFile(StringIdKey userKey, LongIdKey memoFileKey) throws ServiceException {
        memoFileOperateService.removeMemoFile(userKey, memoFileKey);
    }
}
