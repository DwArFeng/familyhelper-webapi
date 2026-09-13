package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportConfResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ImportConfMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportConfResponseServiceImpl implements ImportConfResponseService {

    private final ImportConfMaintainService importConfMaintainService;

    public ImportConfResponseServiceImpl(
            @Qualifier("fileioImportConfMaintainService") ImportConfMaintainService importConfMaintainService
    ) {
        this.importConfMaintainService = importConfMaintainService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return importConfMaintainService.exists(key);
    }

    @Override
    public ImportConf get(TaskSettingItemKey key) throws ServiceException {
        return importConfMaintainService.get(key);
    }

    @Override
    public TaskSettingItemKey insert(ImportConf importConf) throws ServiceException {
        return importConfMaintainService.insert(importConf);
    }

    @Override
    public void update(ImportConf importConf) throws ServiceException {
        importConfMaintainService.update(importConf);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws ServiceException {
        importConfMaintainService.delete(key);
    }

    @Override
    public PagedData<ImportConf> all(PagingInfo pagingInfo) throws ServiceException {
        return importConfMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ImportConf> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return importConfMaintainService.lookup(
                ImportConfMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

}
