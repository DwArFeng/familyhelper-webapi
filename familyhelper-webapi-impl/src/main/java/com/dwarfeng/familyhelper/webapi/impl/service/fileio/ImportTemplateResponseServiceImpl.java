package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportTemplateStream;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportTemplateStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.bean.dto.DubboRestImportTemplateStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.fileio.service.DubboRestImportTemplateOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ImportTemplateResponseService;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.service.ImportTemplateInfoMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTemplateOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportTemplateResponseServiceImpl implements ImportTemplateResponseService {

    private final ImportTemplateInfoMaintainService importTemplateInfoMaintainService;
    private final ImportTemplateOperateService importTemplateOperateService;
    private final DubboRestImportTemplateOperateService dubboRestImportTemplateOperateService;

    public ImportTemplateResponseServiceImpl(
            @Qualifier("fileioImportTemplateInfoMaintainService")
            ImportTemplateInfoMaintainService importTemplateInfoMaintainService,
            @Qualifier("fileioImportTemplateOperateService")
            ImportTemplateOperateService importTemplateOperateService,
            @Qualifier("familyhelperPluginFileioDubboRestImportTemplateOperateService")
            DubboRestImportTemplateOperateService dubboRestImportTemplateOperateService
    ) {
        this.importTemplateInfoMaintainService = importTemplateInfoMaintainService;
        this.importTemplateOperateService = importTemplateOperateService;
        this.dubboRestImportTemplateOperateService = dubboRestImportTemplateOperateService;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws ServiceException {
        return importTemplateInfoMaintainService.exists(key);
    }

    @Override
    public ImportTemplateInfo get(TaskSettingItemKey key) throws ServiceException {
        return importTemplateInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ImportTemplateInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return importTemplateInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ImportTemplateInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return importTemplateInfoMaintainService.lookup(
                ImportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{taskSettingKey}, pagingInfo
        );
    }

    @Override
    public TaskSettingItemKey create(TemplateCreateInfo createInfo) throws ServiceException {
        return importTemplateOperateService.create(createInfo);
    }

    @Override
    public Template download(TemplateDownloadInfo downloadInfo) throws ServiceException {
        return importTemplateOperateService.download(downloadInfo);
    }

    @Override
    public TemplateStream downloadStream(TemplateStreamDownloadInfo downloadInfo) throws ServiceException {
        DubboRestImportTemplateStream dubboRestImportTemplateStream = dubboRestImportTemplateOperateService.downloadStream(
                new DubboRestImportTemplateStreamDownloadInfo(
                        downloadInfo.getKey().getTaskSettingId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new TemplateStream(
                dubboRestImportTemplateStream.getOriginName(), dubboRestImportTemplateStream.getLength(),
                dubboRestImportTemplateStream.getContent()
        );
    }

    @Override
    public LongIdKey requestStreamVoucher(TemplateStreamDownloadInfo downloadInfo) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestImportTemplateOperateService.requestStreamVoucher(
                new DubboRestImportTemplateStreamDownloadInfo(
                        downloadInfo.getKey().getTaskSettingId(), downloadInfo.getKey().getIdentifier()
                )
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public TemplateStream downloadStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestImportTemplateStream dubboRestImportTemplateStream =
                dubboRestImportTemplateOperateService.downloadStreamByVoucher(voucherIdWrapper);
        return new TemplateStream(
                dubboRestImportTemplateStream.getOriginName(), dubboRestImportTemplateStream.getLength(),
                dubboRestImportTemplateStream.getContent()
        );
    }

    @Override
    public void upload(TemplateUploadInfo uploadInfo) throws ServiceException {
        importTemplateOperateService.upload(uploadInfo);
    }

    @Override
    public void uploadStream(TemplateStreamUploadInfo uploadInfo) throws ServiceException {
        dubboRestImportTemplateOperateService.uploadStream(new DubboRestImportTemplateStreamUploadInfo(
                uploadInfo.getKey().getTaskSettingId(), uploadInfo.getKey().getIdentifier(),
                uploadInfo.getOriginName(), uploadInfo.getLength(), uploadInfo.getContent()
        ));
    }

    @Override
    public void remove(TemplateRemoveInfo removeInfo) throws ServiceException {
        importTemplateOperateService.remove(removeInfo);
    }
}
