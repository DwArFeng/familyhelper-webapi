package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestExportFileStream;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestExportFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.service.DubboRestExportFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportFileResponseService;
import com.dwarfeng.fileio.stack.bean.dto.File;
import com.dwarfeng.fileio.stack.bean.dto.FileDownloadInfo;
import com.dwarfeng.fileio.stack.bean.dto.FileStream;
import com.dwarfeng.fileio.stack.bean.dto.FileStreamDownloadInfo;
import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ExportFileInfoMaintainService;
import com.dwarfeng.fileio.stack.service.ExportService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExportFileResponseServiceImpl implements ExportFileResponseService {

    private final ExportFileInfoMaintainService exportFileInfoMaintainService;
    private final ExportService exportService;
    private final DubboRestExportFileOperateService dubboRestExportFileOperateService;

    public ExportFileResponseServiceImpl(
            @Qualifier("fileioExportFileInfoMaintainService")
            ExportFileInfoMaintainService exportFileInfoMaintainService,
            @Qualifier("fileioExportService")
            ExportService exportService,
            @Qualifier("familyhelperPluginFileioDubboRestExportFileOperateService")
            DubboRestExportFileOperateService dubboRestExportFileOperateService
    ) {
        this.exportFileInfoMaintainService = exportFileInfoMaintainService;
        this.exportService = exportService;
        this.dubboRestExportFileOperateService = dubboRestExportFileOperateService;
    }

    @Override
    public boolean exists(TaskItemKey key) throws ServiceException {
        return exportFileInfoMaintainService.exists(key);
    }

    @Override
    public ExportFileInfo get(TaskItemKey key) throws ServiceException {
        return exportFileInfoMaintainService.get(key);
    }

    @Override
    public TaskItemKey insert(ExportFileInfo exportFileInfo) throws ServiceException {
        return exportFileInfoMaintainService.insert(exportFileInfo);
    }

    @Override
    public void update(ExportFileInfo exportFileInfo) throws ServiceException {
        exportFileInfoMaintainService.update(exportFileInfo);
    }

    @Override
    public void delete(TaskItemKey key) throws ServiceException {
        exportFileInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<ExportFileInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return exportFileInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ExportFileInfo> childForTask(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException {
        return exportFileInfoMaintainService.lookup(
                ExportFileInfoMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}, pagingInfo);
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
