package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestVideoNodeFileStream;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestVideoNodeFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestVideoNodeFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.service.DubboRestVideoNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoNodeThumbnailDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.VideoNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.VideoNode;
import com.dwarfeng.settingrepo.stack.service.VideoNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.VideoNodeOperateService;
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
public class VideoNodeResponseServiceImpl implements VideoNodeResponseService {

    private final VideoNodeMaintainService videoNodeMaintainService;
    private final VideoNodeOperateService videoNodeOperateService;
    private final DubboRestVideoNodeOperateService dubboRestVideoNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public VideoNodeResponseServiceImpl(
            @Qualifier("settingrepoVideoNodeMaintainService")
            VideoNodeMaintainService videoNodeMaintainService,
            @Qualifier("settingrepoVideoNodeOperateService")
            VideoNodeOperateService videoNodeOperateService,
            @Qualifier("familyhelperPluginSettingRepoDubboRestVideoNodeOperateService")
            DubboRestVideoNodeOperateService dubboRestVideoNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.videoNodeMaintainService = videoNodeMaintainService;
        this.videoNodeOperateService = videoNodeOperateService;
        this.dubboRestVideoNodeOperateService = dubboRestVideoNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return videoNodeMaintainService.exists(key);
    }

    @Override
    public VideoNode get(StringIdKey key) throws ServiceException {
        return videoNodeMaintainService.get(key);
    }

    @Override
    public PagedData<VideoNode> all(PagingInfo pagingInfo) throws ServiceException {
        return videoNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public VideoNodeInspectResult inspect(VideoNodeInspectInfo info) throws ServiceException {
        return videoNodeOperateService.inspect(info);
    }

    @Override
    public VideoNodeFile downloadFile(VideoNodeFileDownloadInfo info) throws ServiceException {
        return videoNodeOperateService.downloadFile(info);
    }

    @Override
    public LongIdKey requestFileStreamVoucher(VideoNodeFileDownloadInfo info) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestVideoNodeOperateService.requestFileStreamVoucher(
                new DubboRestVideoNodeFileStreamDownloadInfo(info.getCategory(), info.getArgs())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public VideoNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestVideoNodeFileStream dubboRestVideoNodeFileStream =
                dubboRestVideoNodeOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        if (Objects.isNull(dubboRestVideoNodeFileStream)) {
            return null;
        }
        return new VideoNodeFileStream(
                dubboRestVideoNodeFileStream.getOriginName(), dubboRestVideoNodeFileStream.getLength(),
                dubboRestVideoNodeFileStream.getContent()
        );
    }

    @Override
    public VideoNodeThumbnail downloadThumbnail(VideoNodeThumbnailDownloadInfo info) throws ServiceException {
        return videoNodeOperateService.downloadThumbnail(info);
    }

    @Override
    public void uploadFile(VideoNodeFileUploadInfo info) throws ServiceException {
        videoNodeOperateService.uploadFile(info);
    }

    @Override
    public void uploadFileStream(VideoNodeFileStreamUploadInfo info) throws ServiceException {
        dubboRestVideoNodeOperateService.uploadFileStream(
                new DubboRestVideoNodeFileStreamUploadInfo(
                        info.getCategory(), info.getArgs(), info.getOriginName(), info.getLength(), info.getContent(),
                        info.getCoverContent()
                )
        );
    }

    @Override
    public VideoNodeInspectResult inspectForPublic(PublicVideoNodeInspectInfo info) throws ServiceException {
        try {
            VideoNodeInspectInfo originalInfo = new VideoNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return videoNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看公共视频节点时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public VideoNodeFile downloadFileForPublic(PublicVideoNodeFileDownloadInfo info) throws ServiceException {
        try {
            VideoNodeFileDownloadInfo originalInfo = new VideoNodeFileDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return videoNodeOperateService.downloadFile(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共视频节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey requestFileStreamVoucherForPublic(PublicVideoNodeFileDownloadInfo info) throws ServiceException {
        try {
            VoucherIdWrapper voucherIdWrapper = dubboRestVideoNodeOperateService.requestFileStreamVoucher(
                    new DubboRestVideoNodeFileStreamDownloadInfo(
                            publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                            info.getArgs()
                    )
            );
            return new LongIdKey(voucherIdWrapper.getVoucherId());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("请求下载公共视频节点文件流凭证时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public VideoNodeThumbnail downloadThumbnailForPublic(PublicVideoNodeThumbnailDownloadInfo info)
            throws ServiceException {
        try {
            VideoNodeThumbnailDownloadInfo originalInfo = new VideoNodeThumbnailDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return videoNodeOperateService.downloadThumbnail(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共视频节点缩略图时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
