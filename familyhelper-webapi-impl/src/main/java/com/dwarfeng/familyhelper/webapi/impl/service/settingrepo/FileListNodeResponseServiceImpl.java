package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestFileListNodeFileStream;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestFileListNodeFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestFileListNodeFileStreamUpdateInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestFileListNodeFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.service.DubboRestFileListNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileListNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileListNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileListNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.FileListNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.FileListNode;
import com.dwarfeng.settingrepo.stack.service.FileListNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.FileListNodeOperateService;
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
public class FileListNodeResponseServiceImpl implements FileListNodeResponseService {

    private final FileListNodeMaintainService fileListNodeMaintainService;
    private final FileListNodeOperateService fileListNodeOperateService;
    private final DubboRestFileListNodeOperateService dubboRestFileListNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public FileListNodeResponseServiceImpl(
            @Qualifier("settingrepoFileListNodeMaintainService")
            FileListNodeMaintainService fileListNodeMaintainService,
            @Qualifier("settingrepoFileListNodeOperateService")
            FileListNodeOperateService fileListNodeOperateService,
            @Qualifier("familyhelperPluginSettingRepoDubboRestFileListNodeOperateService")
            DubboRestFileListNodeOperateService dubboRestFileListNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.fileListNodeMaintainService = fileListNodeMaintainService;
        this.fileListNodeOperateService = fileListNodeOperateService;
        this.dubboRestFileListNodeOperateService = dubboRestFileListNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return fileListNodeMaintainService.exists(key);
    }

    @Override
    public FileListNode get(StringIdKey key) throws ServiceException {
        return fileListNodeMaintainService.get(key);
    }

    @Override
    public PagedData<FileListNode> all(PagingInfo pagingInfo) throws ServiceException {
        return fileListNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public FileListNodeSizeResult size(FileListNodeSizeInfo info) throws ServiceException {
        return fileListNodeOperateService.size(info);
    }

    @Override
    public FileListNodeInspectResult inspect(FileListNodeInspectInfo info) throws ServiceException {
        return fileListNodeOperateService.inspect(info);
    }

    @Override
    public FileListNodeFile downloadFile(FileListNodeFileDownloadInfo info) throws ServiceException {
        return fileListNodeOperateService.downloadFile(info);
    }

    @Override
    public LongIdKey requestFileStreamVoucher(FileListNodeFileDownloadInfo info) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestFileListNodeOperateService.requestFileStreamVoucher(
                new DubboRestFileListNodeFileStreamDownloadInfo(info.getCategory(), info.getArgs(), info.getIndex())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public FileListNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestFileListNodeFileStream dubboRestFileListNodeFileStream =
                dubboRestFileListNodeOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        if (Objects.isNull(dubboRestFileListNodeFileStream)) {
            return null;
        }
        return new FileListNodeFileStream(
                dubboRestFileListNodeFileStream.getOriginName(), dubboRestFileListNodeFileStream.getLength(),
                dubboRestFileListNodeFileStream.getContent()
        );
    }

    @Override
    public void uploadFile(FileListNodeFileUploadInfo info) throws ServiceException {
        fileListNodeOperateService.uploadFile(info);
    }

    @Override
    public void uploadFileStream(FileListNodeFileStreamUploadInfo info) throws ServiceException {
        dubboRestFileListNodeOperateService.uploadFileStream(
                new DubboRestFileListNodeFileStreamUploadInfo(
                        info.getCategory(), info.getArgs(), info.getIndex(), info.getOriginName(), info.getLength(),
                        info.getContent()
                )
        );
    }

    @Override
    public void updateFile(FileListNodeFileUpdateInfo info) throws ServiceException {
        fileListNodeOperateService.updateFile(info);
    }

    @Override
    public void updateFileStream(FileListNodeFileStreamUpdateInfo info) throws ServiceException {
        dubboRestFileListNodeOperateService.updateFileStream(
                new DubboRestFileListNodeFileStreamUpdateInfo(
                        info.getCategory(), info.getArgs(), info.getIndex(), info.getOriginName(), info.getLength(),
                        info.getContent()
                )
        );
    }

    @Override
    public void changeOrder(FileListNodeChangeOrderInfo info) throws ServiceException {
        fileListNodeOperateService.changeOrder(info);
    }

    @Override
    public void remove(FileListNodeRemoveInfo info) throws ServiceException {
        fileListNodeOperateService.remove(info);
    }

    @Override
    public FileListNodeSizeResult sizeForPublic(PublicFileListNodeSizeInfo info) throws ServiceException {
        try {
            FileListNodeSizeInfo originalInfo = new FileListNodeSizeInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return fileListNodeOperateService.size(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("公共文件列表节点的大小时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public FileListNodeInspectResult inspectForPublic(PublicFileListNodeInspectInfo info) throws ServiceException {
        try {
            FileListNodeInspectInfo originalInfo = new FileListNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return fileListNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共文件列表节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public FileListNodeFile downloadFileForPublic(PublicFileListNodeFileDownloadInfo info) throws ServiceException {
        try {
            FileListNodeFileDownloadInfo originalInfo = new FileListNodeFileDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(), info.getIndex()
            );
            return fileListNodeOperateService.downloadFile(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共文件列表节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey requestFileStreamVoucherForPublic(PublicFileListNodeFileDownloadInfo info)
            throws ServiceException {
        try {
            VoucherIdWrapper voucherIdWrapper = dubboRestFileListNodeOperateService.requestFileStreamVoucher(
                    new DubboRestFileListNodeFileStreamDownloadInfo(
                            publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                            info.getArgs(), info.getIndex()
                    )
            );
            return new LongIdKey(voucherIdWrapper.getVoucherId());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("请求下载公共文件列表节点文件流凭证时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
