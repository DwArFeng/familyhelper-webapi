package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportConfResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ExportConfMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExportConfResponseServiceImpl implements ExportConfResponseService {

    private final ExportConfMaintainService exportConfMaintainService;

    public ExportConfResponseServiceImpl(
            @Qualifier("fileioExportConfMaintainService") ExportConfMaintainService exportConfMaintainService
    ) {
        this.exportConfMaintainService = exportConfMaintainService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return exportConfMaintainService.exists(key);
    }

    @Override
    public ExportConf get(TaskSettingItemKey key) throws ServiceException {
        return exportConfMaintainService.get(key);
    }

    @Override
    public TaskSettingItemKey insert(ExportConf exportConf) throws ServiceException {
        return exportConfMaintainService.insert(exportConf);
    }

    @Override
    public void update(ExportConf exportConf) throws ServiceException {
        exportConfMaintainService.update(exportConf);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws ServiceException {
        exportConfMaintainService.delete(key);
    }

    @Override
    public PagedData<ExportConf> all(PagingInfo pagingInfo) throws ServiceException {
        return exportConfMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ExportConf> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return exportConfMaintainService.lookup(
                ExportConfMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

}
