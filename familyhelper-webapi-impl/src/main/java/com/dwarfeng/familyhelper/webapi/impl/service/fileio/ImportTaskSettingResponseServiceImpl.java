package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportTaskSettingResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportTaskSettingResponseServiceImpl implements ImportTaskSettingResponseService {

    private final ImportTaskSettingMaintainService importTaskSettingMaintainService;

    public ImportTaskSettingResponseServiceImpl(
            @Qualifier("fileioImportTaskSettingMaintainService")
            ImportTaskSettingMaintainService importTaskSettingMaintainService
    ) {
        this.importTaskSettingMaintainService = importTaskSettingMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return importTaskSettingMaintainService.exists(key);
    }

    @Override
    public ImportTaskSetting get(LongIdKey key) throws ServiceException {
        return importTaskSettingMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(ImportTaskSetting importTaskSetting) throws ServiceException {
        return importTaskSettingMaintainService.insert(importTaskSetting);
    }

    @Override
    public void update(ImportTaskSetting importTaskSetting) throws ServiceException {
        importTaskSettingMaintainService.update(importTaskSetting);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        importTaskSettingMaintainService.delete(key);
    }

    @Override
    public PagedData<ImportTaskSetting> all(PagingInfo pagingInfo) throws ServiceException {
        return importTaskSettingMaintainService.lookup(pagingInfo);
    }

}
