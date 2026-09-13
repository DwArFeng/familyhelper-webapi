package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportTaskSettingResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExportTaskSettingResponseServiceImpl implements ExportTaskSettingResponseService {

    private final ExportTaskSettingMaintainService exportTaskSettingMaintainService;

    public ExportTaskSettingResponseServiceImpl(
            @Qualifier("fileioExportTaskSettingMaintainService")
            ExportTaskSettingMaintainService exportTaskSettingMaintainService
    ) {
        this.exportTaskSettingMaintainService = exportTaskSettingMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return exportTaskSettingMaintainService.exists(key);
    }

    @Override
    public ExportTaskSetting get(LongIdKey key) throws ServiceException {
        return exportTaskSettingMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(ExportTaskSetting exportTaskSetting) throws ServiceException {
        return exportTaskSettingMaintainService.insert(exportTaskSetting);
    }

    @Override
    public void update(ExportTaskSetting exportTaskSetting) throws ServiceException {
        exportTaskSettingMaintainService.update(exportTaskSetting);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        exportTaskSettingMaintainService.delete(key);
    }

    @Override
    public PagedData<ExportTaskSetting> all(PagingInfo pagingInfo) throws ServiceException {
        return exportTaskSettingMaintainService.lookup(pagingInfo);
    }

}
