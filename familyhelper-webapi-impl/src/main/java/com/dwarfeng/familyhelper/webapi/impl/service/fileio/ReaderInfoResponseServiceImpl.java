package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ReaderInfoResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ReaderInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ReaderInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReaderInfoResponseServiceImpl implements ReaderInfoResponseService {

    private final ReaderInfoMaintainService readerInfoMaintainService;

    public ReaderInfoResponseServiceImpl(
            @Qualifier("fileioReaderInfoMaintainService") ReaderInfoMaintainService readerInfoMaintainService
    ) {
        this.readerInfoMaintainService = readerInfoMaintainService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return readerInfoMaintainService.exists(key);
    }

    @Override
    public ReaderInfo get(TaskSettingItemKey key) throws ServiceException {
        return readerInfoMaintainService.get(key);
    }

    @Override
    public TaskSettingItemKey insert(ReaderInfo readerInfo) throws ServiceException {
        return readerInfoMaintainService.insert(readerInfo);
    }

    @Override
    public void update(ReaderInfo readerInfo) throws ServiceException {
        readerInfoMaintainService.update(readerInfo);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws ServiceException {
        readerInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<ReaderInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return readerInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ReaderInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return readerInfoMaintainService.lookup(
                ReaderInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

}
