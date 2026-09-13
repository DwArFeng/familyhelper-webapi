package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestExportTemplateStream;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestExportTemplateStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestExportTemplateStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.service.DubboRestExportTemplateOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ExportTemplateResponseService;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ExportTemplateInfoMaintainService;
import com.dwarfeng.fileio.stack.service.ExportTemplateOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExportTemplateResponseServiceImpl implements ExportTemplateResponseService {

    private final ExportTemplateInfoMaintainService exportTemplateInfoMaintainService;
    private final ExportTemplateOperateService exportTemplateOperateService;
    private final DubboRestExportTemplateOperateService dubboRestExportTemplateOperateService;

    public ExportTemplateResponseServiceImpl(
            @Qualifier("fileioExportTemplateInfoMaintainService")
            ExportTemplateInfoMaintainService exportTemplateInfoMaintainService,
            @Qualifier("fileioExportTemplateOperateService")
            ExportTemplateOperateService exportTemplateOperateService,
            @Qualifier("familyhelperPluginFileioDubboRestExportTemplateOperateService")
            DubboRestExportTemplateOperateService dubboRestExportTemplateOperateService
    ) {
        this.exportTemplateInfoMaintainService = exportTemplateInfoMaintainService;
        this.exportTemplateOperateService = exportTemplateOperateService;
        this.dubboRestExportTemplateOperateService = dubboRestExportTemplateOperateService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return exportTemplateInfoMaintainService.exists(key);
    }

    @Override
    public ExportTemplateInfo get(TaskSettingItemKey key) throws ServiceException {
        return exportTemplateInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ExportTemplateInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return exportTemplateInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ExportTemplateInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return exportTemplateInfoMaintainService.lookup(
                ExportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

    @Override
    public TaskSettingItemKey create(TemplateCreateInfo createInfo) throws ServiceException {
        return exportTemplateOperateService.create(createInfo);
    }

    @Override
    public Template download(TemplateDownloadInfo downloadInfo) throws ServiceException {
        return exportTemplateOperateService.download(downloadInfo);
    }

    @Override
    public TemplateStream downloadStream(TemplateStreamDownloadInfo downloadInfo) throws ServiceException {
        DubboRestExportTemplateStream dubboRestExportTemplateStream = dubboRestExportTemplateOperateService.downloadStream(
                new DubboRestExportTemplateStreamDownloadInfo(
                        downloadInfo.getKey().getTaskSettingId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new TemplateStream(
                dubboRestExportTemplateStream.getOriginName(), dubboRestExportTemplateStream.getLength(),
                dubboRestExportTemplateStream.getContent()
        );
    }

    @Override
    public LongIdKey requestStreamVoucher(TemplateStreamDownloadInfo downloadInfo) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestExportTemplateOperateService.requestStreamVoucher(
                new DubboRestExportTemplateStreamDownloadInfo(
                        downloadInfo.getKey().getTaskSettingId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public TemplateStream downloadStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestExportTemplateStream dubboRestExportTemplateStream =
                dubboRestExportTemplateOperateService.downloadStreamByVoucher(voucherIdWrapper);
        return new TemplateStream(
                dubboRestExportTemplateStream.getOriginName(), dubboRestExportTemplateStream.getLength(),
                dubboRestExportTemplateStream.getContent()
        );
    }

    @Override
    public void upload(TemplateUploadInfo uploadInfo) throws ServiceException {
        exportTemplateOperateService.upload(uploadInfo);
    }

    @Override
    public void uploadStream(TemplateStreamUploadInfo uploadInfo) throws ServiceException {
        dubboRestExportTemplateOperateService.uploadStream(new DubboRestExportTemplateStreamUploadInfo(
                uploadInfo.getKey().getTaskSettingId(), uploadInfo.getKey().getIdentifier(),
                uploadInfo.getOriginName(), uploadInfo.getLength(), uploadInfo.getContent()
        ));
    }

    @Override
    public void remove(TemplateRemoveInfo removeInfo) throws ServiceException {
        exportTemplateOperateService.remove(removeInfo);
    }
}
