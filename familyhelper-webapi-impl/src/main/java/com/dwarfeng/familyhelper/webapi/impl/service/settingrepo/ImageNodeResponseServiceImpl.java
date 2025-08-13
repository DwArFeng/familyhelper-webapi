package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestImageNodeFileStream;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestImageNodeFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestImageNodeFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.service.DubboRestImageNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeThumbnailDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.ImageNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.ImageNode;
import com.dwarfeng.settingrepo.stack.service.ImageNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.ImageNodeOperateService;
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
public class ImageNodeResponseServiceImpl implements ImageNodeResponseService {

    private final ImageNodeMaintainService imageNodeMaintainService;
    private final ImageNodeOperateService imageNodeOperateService;
    private final DubboRestImageNodeOperateService dubboRestImageNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public ImageNodeResponseServiceImpl(
            @Qualifier("settingrepoImageNodeMaintainService")
            ImageNodeMaintainService imageNodeMaintainService,
            @Qualifier("settingrepoImageNodeOperateService")
            ImageNodeOperateService imageNodeOperateService,
            @Qualifier("familyhelperPluginSettingRepoDubboRestImageNodeOperateService")
            DubboRestImageNodeOperateService dubboRestImageNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.imageNodeMaintainService = imageNodeMaintainService;
        this.imageNodeOperateService = imageNodeOperateService;
        this.dubboRestImageNodeOperateService = dubboRestImageNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return imageNodeMaintainService.exists(key);
    }

    @Override
    public ImageNode get(StringIdKey key) throws ServiceException {
        return imageNodeMaintainService.get(key);
    }

    @Override
    public PagedData<ImageNode> all(PagingInfo pagingInfo) throws ServiceException {
        return imageNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public ImageNodeInspectResult inspect(ImageNodeInspectInfo info) throws ServiceException {
        return imageNodeOperateService.inspect(info);
    }

    @Override
    public ImageNodeFile downloadFile(ImageNodeFileDownloadInfo info) throws ServiceException {
        return imageNodeOperateService.downloadFile(info);
    }

    @Override
    public LongIdKey requestFileStreamVoucher(ImageNodeFileDownloadInfo info) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestImageNodeOperateService.requestFileStreamVoucher(
                new DubboRestImageNodeFileStreamDownloadInfo(info.getCategory(), info.getArgs())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public ImageNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestImageNodeFileStream dubboRestImageNodeFileStream =
                dubboRestImageNodeOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        if (Objects.isNull(dubboRestImageNodeFileStream)) {
            return null;
        }
        return new ImageNodeFileStream(
                dubboRestImageNodeFileStream.getOriginName(), dubboRestImageNodeFileStream.getLength(),
                dubboRestImageNodeFileStream.getContent()
        );
    }

    @Override
    public ImageNodeThumbnail downloadThumbnail(ImageNodeThumbnailDownloadInfo info) throws ServiceException {
        return imageNodeOperateService.downloadThumbnail(info);
    }

    @Override
    public void uploadFile(ImageNodeFileUploadInfo info) throws ServiceException {
        imageNodeOperateService.uploadFile(info);
    }

    @Override
    public void uploadFileStream(ImageNodeFileStreamUploadInfo info) throws ServiceException {
        dubboRestImageNodeOperateService.uploadFileStream(
                new DubboRestImageNodeFileStreamUploadInfo(
                        info.getCategory(), info.getArgs(), info.getOriginName(), info.getLength(), info.getContent()
                )
        );
    }

    @Override
    public ImageNodeInspectResult inspectForPublic(PublicImageNodeInspectInfo info) throws ServiceException {
        try {
            ImageNodeInspectInfo originalInfo = new ImageNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return imageNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看公共图片节点时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public ImageNodeFile downloadFileForPublic(PublicImageNodeFileDownloadInfo info) throws ServiceException {
        try {
            ImageNodeFileDownloadInfo originalInfo = new ImageNodeFileDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return imageNodeOperateService.downloadFile(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共图片节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey requestFileStreamVoucherForPublic(PublicImageNodeFileDownloadInfo info) throws ServiceException {
        try {
            VoucherIdWrapper voucherIdWrapper = dubboRestImageNodeOperateService.requestFileStreamVoucher(
                    new DubboRestImageNodeFileStreamDownloadInfo(
                            publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                            info.getArgs()
                    )
            );
            return new LongIdKey(voucherIdWrapper.getVoucherId());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("请求下载公共图片节点文件流凭证时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public ImageNodeThumbnail downloadThumbnailForPublic(PublicImageNodeThumbnailDownloadInfo info)
            throws ServiceException {
        try {
            ImageNodeThumbnailDownloadInfo originalInfo = new ImageNodeThumbnailDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return imageNodeOperateService.downloadThumbnail(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共图片节点缩略图时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
