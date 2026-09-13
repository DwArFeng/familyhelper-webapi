package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.WriterInfoResponseService;
import com.dwarfeng.fileio.stack.bean.entity.WriterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.WriterInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WriterInfoResponseServiceImpl implements WriterInfoResponseService {

    private final WriterInfoMaintainService writerInfoMaintainService;

    public WriterInfoResponseServiceImpl(
            @Qualifier("fileioWriterInfoMaintainService") WriterInfoMaintainService writerInfoMaintainService
    ) {
        this.writerInfoMaintainService = writerInfoMaintainService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return writerInfoMaintainService.exists(key);
    }

    @Override
    public WriterInfo get(TaskSettingItemKey key) throws ServiceException {
        return writerInfoMaintainService.get(key);
    }

    @Override
    public TaskSettingItemKey insert(WriterInfo writerInfo) throws ServiceException {
        return writerInfoMaintainService.insert(writerInfo);
    }

    @Override
    public void update(WriterInfo writerInfo) throws ServiceException {
        writerInfoMaintainService.update(writerInfo);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws ServiceException {
        writerInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<WriterInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return writerInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<WriterInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return writerInfoMaintainService.lookup(
                WriterInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

}
