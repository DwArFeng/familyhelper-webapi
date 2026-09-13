package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestExportFileStream;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestExportFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.service.DubboRestExportFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportResponseService;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.service.ExportService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExportResponseServiceImpl implements ExportResponseService {

    private final ExportService exportService;
    private final DubboRestExportFileOperateService dubboRestExportFileOperateService;

    public ExportResponseServiceImpl(
            @Qualifier("fileioExportService") ExportService exportService,
            @Qualifier("familyhelperPluginFileioDubboRestExportFileOperateService")
            DubboRestExportFileOperateService dubboRestExportFileOperateService
    ) {
        this.exportService = exportService;
        this.dubboRestExportFileOperateService = dubboRestExportFileOperateService;
    }

    @Override
    public LongIdKey createTask(TaskCreateInfo createInfo) throws ServiceException {
        return exportService.createTask(createInfo);
    }

    @Override
    public void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws ServiceException {
        exportService.upsertTaskMetadata(upsertInfo);
    }

    @Override
    public void executeTask(TaskExecuteInfo executeInfo) throws ServiceException {
        exportService.executeTask(executeInfo);
    }

    @Override
    public void executeTaskAsync(TaskExecuteInfo executeInfo) throws ServiceException {
        exportService.executeTaskAsync(executeInfo);
    }

    @Override
    public File downloadFile(FileDownloadInfo downloadInfo) throws ServiceException {
        return exportService.downloadFile(downloadInfo);
    }

    @Override
    public FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws ServiceException {
        DubboRestExportFileStream dubboRestExportFileStream = dubboRestExportFileOperateService.downloadFileStream(
                new DubboRestExportFileStreamDownloadInfo(
                        downloadInfo.getKey().getTaskId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new FileStream(
                dubboRestExportFileStream.getOriginName(), dubboRestExportFileStream.getLength(),
                dubboRestExportFileStream.getContent()
        );
    }

    @Override
    public LongIdKey requestFileStreamVoucher(FileStreamDownloadInfo downloadInfo) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestExportFileOperateService.requestFileStreamVoucher(
                new DubboRestExportFileStreamDownloadInfo(
                        downloadInfo.getKey().getTaskId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public FileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestExportFileStream dubboRestExportFileStream =
                dubboRestExportFileOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        return new FileStream(
                dubboRestExportFileStream.getOriginName(), dubboRestExportFileStream.getLength(),
                dubboRestExportFileStream.getContent()
        );
    }
}
