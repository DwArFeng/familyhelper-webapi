package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispImportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportTaskResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.service.ImportTaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImportTaskResponseServiceImpl implements ImportTaskResponseService {

    private final ImportTaskMaintainService importTaskMaintainService;
    private final AccountResponseService accountResponseService;

    public ImportTaskResponseServiceImpl(
            @Qualifier("fileioImportTaskMaintainService") ImportTaskMaintainService importTaskMaintainService,
            AccountResponseService accountResponseService
    ) {
        this.importTaskMaintainService = importTaskMaintainService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return importTaskMaintainService.exists(key);
    }

    @Override
    public ImportTask get(LongIdKey key) throws ServiceException {
        return importTaskMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(ImportTask importTask) throws ServiceException {
        return importTaskMaintainService.insert(importTask);
    }

    @Override
    public void update(ImportTask importTask) throws ServiceException {
        importTaskMaintainService.update(importTask);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        importTaskMaintainService.delete(key);
    }

    @Override
    public PagedData<ImportTask> all(PagingInfo pagingInfo) throws ServiceException {
        return importTaskMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ImportTask> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return importTaskMaintainService.lookup(
                ImportTaskMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ImportTask> createDateDesc(PagingInfo pagingInfo) throws ServiceException {
        return importTaskMaintainService.lookup(
                ImportTaskMaintainService.CREATE_DATE_DESC, new Object[0], pagingInfo
        );
    }

    @Override
    public PagedData<ImportTask> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException {
        return importTaskMaintainService.lookup(
                ImportTaskMaintainService.CHILD_FOR_USER, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public DispImportTask getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        ImportTask importTask = get(key);
        return toDisp(importTask, inspectAccountKey);
    }

    @Override
    public PagedData<DispImportTask> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        return toDispPagedData(all(pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispImportTask> childForTaskSettingDisp(
            LongIdKey taskSettingKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    )
            throws ServiceException {
        return toDispPagedData(childForTaskSetting(taskSettingKey, pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispImportTask> createDateDescDisp(
            PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        return toDispPagedData(createDateDesc(pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispImportTask> childForUserDisp(
            StringIdKey userKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    )
            throws ServiceException {
        return toDispPagedData(childForUser(userKey, pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispImportTask> childForMeDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        return childForUserDisp(inspectAccountKey, pagingInfo, inspectAccountKey);
    }

    private DispImportTask toDisp(ImportTask importTask, StringIdKey inspectAccountKey) throws ServiceException {
        DispAccount dispAccount = null;
        if (Objects.nonNull(importTask.getUserKey())) {
            dispAccount = accountResponseService.getDisp(inspectAccountKey, importTask.getUserKey());
        }
        return DispImportTask.of(importTask, dispAccount);
    }

    private PagedData<DispImportTask> toDispPagedData(
            PagedData<ImportTask> pagedData, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispImportTask> dispList = new ArrayList<>();
        for (ImportTask importTask : pagedData.getData()) {
            dispList.add(toDisp(importTask, inspectAccountKey));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispList
        );
    }
}
