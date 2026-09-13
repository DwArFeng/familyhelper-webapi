package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispExportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportTaskResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.service.ExportTaskMaintainService;
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
public class ExportTaskResponseServiceImpl implements ExportTaskResponseService {

    private final ExportTaskMaintainService exportTaskMaintainService;
    private final AccountResponseService accountResponseService;

    public ExportTaskResponseServiceImpl(
            @Qualifier("fileioExportTaskMaintainService") ExportTaskMaintainService exportTaskMaintainService,
            AccountResponseService accountResponseService
    ) {
        this.exportTaskMaintainService = exportTaskMaintainService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return exportTaskMaintainService.exists(key);
    }

    @Override
    public ExportTask get(LongIdKey key) throws ServiceException {
        return exportTaskMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(ExportTask exportTask) throws ServiceException {
        return exportTaskMaintainService.insert(exportTask);
    }

    @Override
    public void update(ExportTask exportTask) throws ServiceException {
        exportTaskMaintainService.update(exportTask);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        exportTaskMaintainService.delete(key);
    }

    @Override
    public PagedData<ExportTask> all(PagingInfo pagingInfo) throws ServiceException {
        return exportTaskMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ExportTask> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return exportTaskMaintainService.lookup(
                ExportTaskMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ExportTask> createDateDesc(PagingInfo pagingInfo) throws ServiceException {
        return exportTaskMaintainService.lookup(
                ExportTaskMaintainService.CREATE_DATE_DESC, new Object[0], pagingInfo
        );
    }

    @Override
    public PagedData<ExportTask> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException {
        return exportTaskMaintainService.lookup(
                ExportTaskMaintainService.CHILD_FOR_USER, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public DispExportTask getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        ExportTask exportTask = get(key);
        return toDisp(exportTask, inspectAccountKey);
    }

    @Override
    public PagedData<DispExportTask> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        return toDispPagedData(all(pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispExportTask> childForTaskSettingDisp(
            LongIdKey taskSettingKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    )
            throws ServiceException {
        return toDispPagedData(childForTaskSetting(taskSettingKey, pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispExportTask> createDateDescDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        return toDispPagedData(createDateDesc(pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispExportTask> childForUserDisp(
            StringIdKey userKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    )
            throws ServiceException {
        return toDispPagedData(childForUser(userKey, pagingInfo), inspectAccountKey);
    }

    @Override
    public PagedData<DispExportTask> childForMeDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        return childForUserDisp(inspectAccountKey, pagingInfo, inspectAccountKey);
    }

    private DispExportTask toDisp(ExportTask exportTask, StringIdKey inspectAccountKey) throws ServiceException {
        DispAccount dispAccount = null;
        if (Objects.nonNull(exportTask.getUserKey())) {
            dispAccount = accountResponseService.getDisp(inspectAccountKey, exportTask.getUserKey());
        }
        return DispExportTask.of(exportTask, dispAccount);
    }

    private PagedData<DispExportTask> toDispPagedData(
            PagedData<ExportTask> pagedData, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispExportTask> dispList = new ArrayList<>();
        for (ExportTask exportTask : pagedData.getData()) {
            dispList.add(toDisp(exportTask, inspectAccountKey));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispList
        );
    }
}
