package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportFileStream;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.service.DubboRestImportFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportFileResponseService;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ImportFileInfoMaintainService;
import com.dwarfeng.fileio.stack.service.ImportService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportFileResponseServiceImpl implements ImportFileResponseService {

    private final ImportFileInfoMaintainService importFileInfoMaintainService;
    private final ImportService importService;
    private final DubboRestImportFileOperateService dubboRestImportFileOperateService;

    public ImportFileResponseServiceImpl(
            @Qualifier("fileioImportFileInfoMaintainService")
            ImportFileInfoMaintainService importFileInfoMaintainService,
            @Qualifier("fileioImportService")
            ImportService importService,
            @Qualifier("familyhelperPluginFileioDubboRestImportFileOperateService")
            DubboRestImportFileOperateService dubboRestImportFileOperateService
    ) {
        this.importFileInfoMaintainService = importFileInfoMaintainService;
        this.importService = importService;
        this.dubboRestImportFileOperateService = dubboRestImportFileOperateService;
    }

    @Override
    public boolean exists(TaskItemKey key) throws ServiceException {
        return importFileInfoMaintainService.exists(key);
    }

    @Override
    public ImportFileInfo get(TaskItemKey key) throws ServiceException {
        return importFileInfoMaintainService.get(key);
    }

    @Override
    public TaskItemKey insert(ImportFileInfo importFileInfo) throws ServiceException {
        return importFileInfoMaintainService.insert(importFileInfo);
    }

    @Override
    public void update(ImportFileInfo importFileInfo) throws ServiceException {
        importFileInfoMaintainService.update(importFileInfo);
    }

    @Override
    public void delete(TaskItemKey key) throws ServiceException {
        importFileInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<ImportFileInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return importFileInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ImportFileInfo> childForTask(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException {
        return importFileInfoMaintainService.lookup(
                ImportFileInfoMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo);
    }

    @Override
    public File downloadFile(FileDownloadInfo downloadInfo) throws ServiceException {
        return importService.downloadFile(downloadInfo);
    }

    @Override
    public FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws ServiceException {
        DubboRestImportFileStream dubboRestImportFileStream = dubboRestImportFileOperateService.downloadFileStream(
                new DubboRestImportFileStreamDownloadInfo(
                        downloadInfo.getKey().getTaskId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new FileStream(
                dubboRestImportFileStream.getOriginName(), dubboRestImportFileStream.getLength(),
                dubboRestImportFileStream.getContent()
        );
    }

    @Override
    public LongIdKey requestFileStreamVoucher(FileStreamDownloadInfo downloadInfo) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestImportFileOperateService.requestFileStreamVoucher(
                new DubboRestImportFileStreamDownloadInfo(
                        downloadInfo.getKey().getTaskId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public FileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestImportFileStream dubboRestImportFileStream =
                dubboRestImportFileOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        return new FileStream(
                dubboRestImportFileStream.getOriginName(), dubboRestImportFileStream.getLength(),
                dubboRestImportFileStream.getContent()
        );
    }

    @Override
    public void uploadFile(FileUploadInfo uploadInfo) throws ServiceException {
        importService.uploadFile(uploadInfo);
    }

    @Override
    public void uploadFileStream(FileStreamUploadInfo uploadInfo) throws ServiceException {
        dubboRestImportFileOperateService.uploadFileStream(new DubboRestImportFileStreamUploadInfo(
                uploadInfo.getKey().getTaskId(), uploadInfo.getKey().getIdentifier(),
                uploadInfo.getOriginName(), uploadInfo.getLength(), uploadInfo.getContent()
        ));
    }
}
