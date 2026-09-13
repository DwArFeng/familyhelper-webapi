package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImporterInfoResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ImporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ImporterInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImporterInfoResponseServiceImpl implements ImporterInfoResponseService {

    private final ImporterInfoMaintainService importerInfoMaintainService;

    public ImporterInfoResponseServiceImpl(
            @Qualifier("fileioImporterInfoMaintainService")
            ImporterInfoMaintainService importerInfoMaintainService
    ) {
        this.importerInfoMaintainService = importerInfoMaintainService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return importerInfoMaintainService.exists(key);
    }

    @Override
    public ImporterInfo get(TaskSettingItemKey key) throws ServiceException {
        return importerInfoMaintainService.get(key);
    }

    @Override
    public TaskSettingItemKey insert(ImporterInfo importerInfo) throws ServiceException {
        return importerInfoMaintainService.insert(importerInfo);
    }

    @Override
    public void update(ImporterInfo importerInfo) throws ServiceException {
        importerInfoMaintainService.update(importerInfo);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws ServiceException {
        importerInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<ImporterInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return importerInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ImporterInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return importerInfoMaintainService.lookup(
                ImporterInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

}
