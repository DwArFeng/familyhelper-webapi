package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestFileNodeFileStream;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestFileNodeFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestFileNodeFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.service.DubboRestFileNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.FileNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.FileNode;
import com.dwarfeng.settingrepo.stack.service.FileNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.FileNodeOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FileNodeResponseServiceImpl implements FileNodeResponseService {

    private final FileNodeMaintainService fileNodeMaintainService;
    private final FileNodeOperateService fileNodeOperateService;
    private final DubboRestFileNodeOperateService dubboRestFileNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public FileNodeResponseServiceImpl(
            @Qualifier("settingrepoFileNodeMaintainService")
            FileNodeMaintainService fileNodeMaintainService,
            @Qualifier("settingrepoFileNodeOperateService")
            FileNodeOperateService fileNodeOperateService,
            @Qualifier("familyhelperPluginSettingRepoDubboRestFileNodeOperateService")
            DubboRestFileNodeOperateService dubboRestFileNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.fileNodeMaintainService = fileNodeMaintainService;
        this.fileNodeOperateService = fileNodeOperateService;
        this.dubboRestFileNodeOperateService = dubboRestFileNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return fileNodeMaintainService.exists(key);
    }

    @Override
    public FileNode get(StringIdKey key) throws ServiceException {
        return fileNodeMaintainService.get(key);
    }

    @Override
    public PagedData<FileNode> all(PagingInfo pagingInfo) throws ServiceException {
        return fileNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public FileNodeInspectResult inspect(FileNodeInspectInfo info) throws ServiceException {
        return fileNodeOperateService.inspect(info);
    }

    @Override
    public FileNodeFile downloadFile(FileNodeFileDownloadInfo info) throws ServiceException {
        return fileNodeOperateService.downloadFile(info);
    }

    @Override
    public LongIdKey requestFileStreamVoucher(FileNodeFileDownloadInfo info) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestFileNodeOperateService.requestFileStreamVoucher(
                new DubboRestFileNodeFileStreamDownloadInfo(info.getCategory(), info.getArgs())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public FileNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestFileNodeFileStream dubboRestFileNodeFileStream =
                dubboRestFileNodeOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        if (Objects.isNull(dubboRestFileNodeFileStream)) {
            return null;
        }
        return new FileNodeFileStream(
                dubboRestFileNodeFileStream.getOriginName(), dubboRestFileNodeFileStream.getLength(),
                dubboRestFileNodeFileStream.getContent()
        );
    }

    @Override
    public void uploadFile(FileNodeFileUploadInfo info) throws ServiceException {
        fileNodeOperateService.uploadFile(info);
    }

    @Override
    public void uploadFileStream(FileNodeFileStreamUploadInfo info) throws ServiceException {
        dubboRestFileNodeOperateService.uploadFileStream(
                new DubboRestFileNodeFileStreamUploadInfo(
                        info.getCategory(), info.getArgs(), info.getOriginName(), info.getLength(), info.getContent()
                )
        );
    }

    @Override
    public FileNodeInspectResult inspectForPublic(PublicFileNodeInspectInfo info) throws ServiceException {
        try {
            FileNodeInspectInfo originalInfo = new FileNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return fileNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看公共文件节点时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public FileNodeFile downloadFileForPublic(PublicFileNodeFileDownloadInfo info) throws ServiceException {
        try {
            FileNodeFileDownloadInfo originalInfo = new FileNodeFileDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return fileNodeOperateService.downloadFile(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共文件节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey requestFileStreamVoucherForPublic(PublicFileNodeFileDownloadInfo info) throws ServiceException {
        try {
            VoucherIdWrapper voucherIdWrapper = dubboRestFileNodeOperateService.requestFileStreamVoucher(
                    new DubboRestFileNodeFileStreamDownloadInfo(
                            publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                            info.getArgs()
                    )
            );
            return new LongIdKey(voucherIdWrapper.getVoucherId());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("请求下载公共文件节点文件流凭证时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
