package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportMetadataResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ImportMetadataMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportMetadataResponseServiceImpl implements ImportMetadataResponseService {

    private final ImportMetadataMaintainService importMetadataMaintainService;

    public ImportMetadataResponseServiceImpl(
            @Qualifier("fileioImportMetadataMaintainService")
            ImportMetadataMaintainService importMetadataMaintainService
    ) {
        this.importMetadataMaintainService = importMetadataMaintainService;
    }

    @Override
    public boolean exists(TaskItemKey key) throws ServiceException {
        return importMetadataMaintainService.exists(key);
    }

    @Override
    public ImportMetadata get(TaskItemKey key) throws ServiceException {
        return importMetadataMaintainService.get(key);
    }

    @Override
    public TaskItemKey insert(ImportMetadata importMetadata) throws ServiceException {
        return importMetadataMaintainService.insert(importMetadata);
    }

    @Override
    public void update(ImportMetadata importMetadata) throws ServiceException {
        importMetadataMaintainService.update(importMetadata);
    }

    @Override
    public void delete(TaskItemKey key) throws ServiceException {
        importMetadataMaintainService.delete(key);
    }

    @Override
    public PagedData<ImportMetadata> all(PagingInfo pagingInfo) throws ServiceException {
        return importMetadataMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ImportMetadata> childForTask(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException {
        return importMetadataMaintainService.lookup(
                ImportMetadataMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo);
    }

}
