package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestImageListNodeFileStream;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestImageListNodeFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestImageListNodeFileStreamUpdateInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestImageListNodeFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.service.DubboRestImageListNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeThumbnailDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.ImageListNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.ImageListNode;
import com.dwarfeng.settingrepo.stack.service.ImageListNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.ImageListNodeOperateService;
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
public class ImageListNodeResponseServiceImpl implements ImageListNodeResponseService {

    private final ImageListNodeMaintainService imageListNodeMaintainService;
    private final ImageListNodeOperateService imageListNodeOperateService;
    private final DubboRestImageListNodeOperateService dubboRestImageListNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public ImageListNodeResponseServiceImpl(
            @Qualifier("settingrepoImageListNodeMaintainService")
            ImageListNodeMaintainService imageListNodeMaintainService,
            @Qualifier("settingrepoImageListNodeOperateService")
            ImageListNodeOperateService imageListNodeOperateService,
            @Qualifier("familyhelperPluginSettingRepoDubboRestImageListNodeOperateService")
            DubboRestImageListNodeOperateService dubboRestImageListNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.imageListNodeMaintainService = imageListNodeMaintainService;
        this.imageListNodeOperateService = imageListNodeOperateService;
        this.dubboRestImageListNodeOperateService = dubboRestImageListNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return imageListNodeMaintainService.exists(key);
    }

    @Override
    public ImageListNode get(StringIdKey key) throws ServiceException {
        return imageListNodeMaintainService.get(key);
    }

    @Override
    public PagedData<ImageListNode> all(PagingInfo pagingInfo) throws ServiceException {
        return imageListNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public ImageListNodeSizeResult size(ImageListNodeSizeInfo info) throws ServiceException {
        return imageListNodeOperateService.size(info);
    }

    @Override
    public ImageListNodeInspectResult inspect(ImageListNodeInspectInfo info) throws ServiceException {
        return imageListNodeOperateService.inspect(info);
    }

    @Override
    public ImageListNodeFile downloadFile(ImageListNodeFileDownloadInfo info) throws ServiceException {
        return imageListNodeOperateService.downloadFile(info);
    }

    @Override
    public LongIdKey requestFileStreamVoucher(ImageListNodeFileDownloadInfo info) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestImageListNodeOperateService.requestFileStreamVoucher(
                new DubboRestImageListNodeFileStreamDownloadInfo(info.getCategory(), info.getArgs(), info.getIndex())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public ImageListNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestImageListNodeFileStream dubboRestImageListNodeFileStream =
                dubboRestImageListNodeOperateService.downloadFileStreamByVoucher(voucherIdWrapper);
        if (Objects.isNull(dubboRestImageListNodeFileStream)) {
            return null;
        }
        return new ImageListNodeFileStream(
                dubboRestImageListNodeFileStream.getOriginName(), dubboRestImageListNodeFileStream.getLength(),
                dubboRestImageListNodeFileStream.getContent()
        );
    }

    @Override
    public ImageListNodeThumbnail downloadThumbnail(ImageListNodeThumbnailDownloadInfo info) throws ServiceException {
        return imageListNodeOperateService.downloadThumbnail(info);
    }

    @Override
    public void uploadFile(ImageListNodeFileUploadInfo info) throws ServiceException {
        imageListNodeOperateService.uploadFile(info);
    }

    @Override
    public void uploadFileStream(ImageListNodeFileStreamUploadInfo info) throws ServiceException {
        dubboRestImageListNodeOperateService.uploadFileStream(
                new DubboRestImageListNodeFileStreamUploadInfo(
                        info.getCategory(), info.getArgs(), info.getIndex(), info.getOriginName(), info.getLength(),
                        info.getContent()
                )
        );
    }

    @Override
    public void updateFile(ImageListNodeFileUpdateInfo info) throws ServiceException {
        imageListNodeOperateService.updateFile(info);
    }

    @Override
    public void updateFileStream(ImageListNodeFileStreamUpdateInfo info) throws ServiceException {
        dubboRestImageListNodeOperateService.updateFileStream(
                new DubboRestImageListNodeFileStreamUpdateInfo(
                        info.getCategory(), info.getArgs(), info.getIndex(), info.getOriginName(), info.getLength(),
                        info.getContent()
                )
        );
    }

    @Override
    public void changeOrder(ImageListNodeChangeOrderInfo info) throws ServiceException {
        imageListNodeOperateService.changeOrder(info);
    }

    @Override
    public void remove(ImageListNodeRemoveInfo info) throws ServiceException {
        imageListNodeOperateService.remove(info);
    }

    @Override
    public ImageListNodeSizeResult sizeForPublic(PublicImageListNodeSizeInfo info) throws ServiceException {
        try {
            ImageListNodeSizeInfo originalInfo = new ImageListNodeSizeInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return imageListNodeOperateService.size(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("公共图片列表节点的大小时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public ImageListNodeInspectResult inspectForPublic(PublicImageListNodeInspectInfo info) throws ServiceException {
        try {
            ImageListNodeInspectInfo originalInfo = new ImageListNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return imageListNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共图片列表节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public ImageListNodeFile downloadFileForPublic(PublicImageListNodeFileDownloadInfo info) throws ServiceException {
        try {
            ImageListNodeFileDownloadInfo originalInfo = new ImageListNodeFileDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(), info.getIndex()
            );
            return imageListNodeOperateService.downloadFile(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共图片列表节点文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LongIdKey requestFileStreamVoucherForPublic(PublicImageListNodeFileDownloadInfo info)
            throws ServiceException {
        try {
            VoucherIdWrapper voucherIdWrapper = dubboRestImageListNodeOperateService.requestFileStreamVoucher(
                    new DubboRestImageListNodeFileStreamDownloadInfo(
                            publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                            info.getArgs(), info.getIndex()
                    )
            );
            return new LongIdKey(voucherIdWrapper.getVoucherId());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("请求下载公共图片列表节点文件流凭证时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public ImageListNodeThumbnail downloadThumbnailForPublic(PublicImageListNodeThumbnailDownloadInfo info)
            throws ServiceException {
        try {
            ImageListNodeThumbnailDownloadInfo originalInfo = new ImageListNodeThumbnailDownloadInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(), info.getIndex()
            );
            return imageListNodeOperateService.downloadThumbnail(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载公共图片列表节点缩略图时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
