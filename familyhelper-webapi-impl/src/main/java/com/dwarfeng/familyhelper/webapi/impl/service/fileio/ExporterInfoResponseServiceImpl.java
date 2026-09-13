package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExporterInfoResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ExporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ExporterInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExporterInfoResponseServiceImpl implements ExporterInfoResponseService {

    private final ExporterInfoMaintainService exporterInfoMaintainService;

    public ExporterInfoResponseServiceImpl(
            @Qualifier("fileioExporterInfoMaintainService")
            ExporterInfoMaintainService exporterInfoMaintainService
    ) {
        this.exporterInfoMaintainService = exporterInfoMaintainService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return exporterInfoMaintainService.exists(key);
    }

    @Override
    public ExporterInfo get(TaskSettingItemKey key) throws ServiceException {
        return exporterInfoMaintainService.get(key);
    }

    @Override
    public TaskSettingItemKey insert(ExporterInfo exporterInfo) throws ServiceException {
        return exporterInfoMaintainService.insert(exporterInfo);
    }

    @Override
    public void update(ExporterInfo exporterInfo) throws ServiceException {
        exporterInfoMaintainService.update(exporterInfo);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws ServiceException {
        exporterInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<ExporterInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return exporterInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ExporterInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return exporterInfoMaintainService.lookup(
                ExporterInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

}
