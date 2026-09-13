package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportFileStream;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.service.DubboRestImportFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportResponseService;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.service.ImportService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportResponseServiceImpl implements ImportResponseService {

    private final ImportService importService;
    private final DubboRestImportFileOperateService dubboRestImportFileOperateService;

    public ImportResponseServiceImpl(
            @Qualifier("fileioImportService") ImportService importService,
            @Qualifier("familyhelperPluginFileioDubboRestImportFileOperateService")
            DubboRestImportFileOperateService dubboRestImportFileOperateService
    ) {
        this.importService = importService;
        this.dubboRestImportFileOperateService = dubboRestImportFileOperateService;
    }

    @Override
    public LongIdKey createTask(TaskCreateInfo createInfo) throws ServiceException {
        return importService.createTask(createInfo);
    }

    @Override
    public void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws ServiceException {
        importService.upsertTaskMetadata(upsertInfo);
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

    @Override
    public void executeTask(TaskExecuteInfo executeInfo) throws ServiceException {
        importService.executeTask(executeInfo);
    }

    @Override
    public void executeTaskAsync(TaskExecuteInfo executeInfo) throws ServiceException {
        importService.executeTaskAsync(executeInfo);
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
}
