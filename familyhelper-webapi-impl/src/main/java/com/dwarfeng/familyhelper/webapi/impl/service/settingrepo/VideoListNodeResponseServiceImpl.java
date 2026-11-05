package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestVideoListNodeFileStream;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestVideoListNodeFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestVideoListNodeFileStreamUpdateInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestVideoListNodeFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.service.DubboRestVideoListNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeThumbnailDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.VideoListNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.VideoListNode;
import com.dwarfeng.settingrepo.stack.service.VideoListNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.VideoListNodeOperateService;
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
public class VideoListNodeResponseServiceImpl implements VideoListNodeResponseService {

    private final VideoListNodeMaintainService videoListNodeMaintainService;
    private final VideoListNodeOperateService videoListNodeOperateService;
    private final DubboRestVideoListNodeOperateService dubboRestVideoListNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public VideoListNodeResponseServiceImpl(
            @Qualifier("settingrepoVideoListNodeMaintainService")
            VideoListNodeMaintainService videoListNodeMaintainService,
            @Qualifier("settingrepoVideoListNodeOperateService")
            VideoListNodeOperateService videoListNodeOperateService,
            @Qualifier("familyhelperPluginSettingRepoDubboRestVideoListNodeOperateService")
            DubboRestVideoListNodeOperateService dubboRestVideoListNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.videoListNodeMaintainService = videoListNodeMaintainService;
        this.videoListNodeOperateService = videoListNodeOperateService;
        this.dubboRestVideoListNodeOperateService = dubboRestVideoListNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return videoListNodeMaintainService.exists(key);
    }

    @Override
    public VideoListNode get(StringIdKey key) throws ServiceException {
        return videoListNodeMaintainService.get(key);
    }

    @Override
    public PagedData<VideoListNode> all(PagingInfo pagingInfo) throws ServiceException {
        return videoListNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public VideoListNodeSizeResult size(VideoListNodeSizeInfo info) throws ServiceException {
        return videoListNodeOperateService.size(info);
    }

    @Override
    public VideoListNodeInspectResult inspect(VideoListNodeInspectInfo info) throws ServiceException {
        return videoListNodeOperateService.inspect(info);
    }

    @Override
    public VideoListNodeFile downloadFile(VideoListNodeFileDownloadInfo info) throws ServiceException {
        return videoListNodeOperateService.downloadFile(info);
    }

    @Override
    public LongIdKey requestFileStreamVoucher(VideoListNodeFileDownloadInfo info) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestVideoListNodeOperateService.requestFileStreamVoucher(
                new DubboRestVideoListNodeFileStreamDownloadInfo(info.getCategory(), info.getArgs(), info.getIndex())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public VideoListNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestVideoListNodeFileStream dubboRestVideoListNodeFileStream =
                dubboRestVideoListNodeOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        if (Objects.isNull(dubboRestVideoListNodeFileStream)) {
            return null;
        }
        return new VideoListNodeFileStream(
                dubboRestVideoListNodeFileStream.getOriginName(), dubboRestVideoListNodeFileStream.getLength(),
                dubboRestVideoListNodeFileStream.getContent()
        );
    }

    @Override
    public VideoListNodeThumbnail downloadThumbnail(VideoListNodeThumbnailDownloadInfo info) throws ServiceException {
        return videoListNodeOperateService.downloadThumbnail(info);
    }

    @Override
    public void uploadFile(VideoListNodeFileUploadInfo info) throws ServiceException {
        videoListNodeOperateService.uploadFile(info);
    }

    @Override
    public void uploadFileStream(VideoListNodeFileStreamUploadInfo info) throws ServiceException {
        dubboRestVideoListNodeOperateService.uploadFileStream(
                new DubboRestVideoListNodeFileStreamUploadInfo(
                        info.getCategory(), info.getArgs(), info.getIndex(), info.getOriginName(), info.getLength(),
                        info.getContent(), info.getCoverContent()
                )
        );
    }

    @Override
    public void updateFile(VideoListNodeFileUpdateInfo info) throws ServiceException {
        videoListNodeOperateService.updateFile(info);
    }

    @Override
    public void updateFileStream(VideoListNodeFileStreamUpdateInfo info) throws ServiceException {
        dubboRestVideoListNodeOperateService.updateFileStream(
                new DubboRestVideoListNodeFileStreamUpdateInfo(
                        info.getCategory(), info.getArgs(), info.getIndex(), info.getOriginName(), info.getLength(),
                        info.getContent(), info.getCoverContent()
                )
        );
    }

    @Override
    public void changeOrder(VideoListNodeChangeOrderInfo info) throws ServiceException {
        videoListNodeOperateService.changeOrder(info);
    }

    @Override
    public void remove(VideoListNodeRemoveInfo info) throws ServiceException {
        videoListNodeOperateService.remove(info);
    }

    @Override
    public VideoListNodeSizeResult sizeForPublic(PublicVideoListNodeSizeInfo info) throws ServiceException {
        try {
            VideoListNodeSizeInfo originalInfo = new VideoListNodeSizeInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return videoListNodeOperateService.size(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("公共视频列表节点的大小时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public VideoListNodeInspectResult inspectForPublic(PublicVideoListNodeInspectInfo info) throws ServiceException {
        try {
            VideoListNodeInspectInfo originalInfo = new VideoListNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return videoListNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看公共视频列表节点时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public VideoListNodeFile downloadFileForPublic(PublicVideoListNodeFileDownloadInfo info) throws ServiceException {
        try {
            VideoListNodeFileDownloadInfo originalInfo = new VideoListNodeFileDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(), info.getIndex()
            );
            return videoListNodeOperateService.downloadFile(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共视频列表节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey requestFileStreamVoucherForPublic(PublicVideoListNodeFileDownloadInfo info)
            throws ServiceException {
        try {
            VoucherIdWrapper voucherIdWrapper = dubboRestVideoListNodeOperateService.requestFileStreamVoucher(
                    new DubboRestVideoListNodeFileStreamDownloadInfo(
                            publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                            info.getArgs(), info.getIndex()
                    )
            );
            return new LongIdKey(voucherIdWrapper.getVoucherId());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("请求下载公共视频列表节点文件流凭证时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public VideoListNodeThumbnail downloadThumbnailForPublic(PublicVideoListNodeThumbnailDownloadInfo info)
            throws ServiceException {
        try {
            VideoListNodeThumbnailDownloadInfo originalInfo = new VideoListNodeThumbnailDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(), info.getIndex()
            );
            return videoListNodeOperateService.downloadThumbnail(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共视频列表节点缩略图时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
