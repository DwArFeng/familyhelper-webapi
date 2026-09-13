package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportMetadataResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ExportMetadataMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExportMetadataResponseServiceImpl implements ExportMetadataResponseService {

    private final ExportMetadataMaintainService exportMetadataMaintainService;

    public ExportMetadataResponseServiceImpl(
            @Qualifier("fileioExportMetadataMaintainService")
            ExportMetadataMaintainService exportMetadataMaintainService
    ) {
        this.exportMetadataMaintainService = exportMetadataMaintainService;
    }

    @Override
    public boolean exists(TaskItemKey key) throws ServiceException {
        return exportMetadataMaintainService.exists(key);
    }

    @Override
    public ExportMetadata get(TaskItemKey key) throws ServiceException {
        return exportMetadataMaintainService.get(key);
    }

    @Override
    public TaskItemKey insert(ExportMetadata exportMetadata) throws ServiceException {
        return exportMetadataMaintainService.insert(exportMetadata);
    }

    @Override
    public void update(ExportMetadata exportMetadata) throws ServiceException {
        exportMetadataMaintainService.update(exportMetadata);
    }

    @Override
    public void delete(TaskItemKey key) throws ServiceException {
        exportMetadataMaintainService.delete(key);
    }

    @Override
    public PagedData<ExportMetadata> all(PagingInfo pagingInfo) throws ServiceException {
        return exportMetadataMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ExportMetadata> childForTask(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException {
        return exportMetadataMaintainService.lookup(
                ExportMetadataMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo);
    }

}
