package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeThumbnailDownloadInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.ImageNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 图像节点响应服务。
 *
 * @author DwArFeng
 * @since 1.3.0
 */
public interface ImageNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ImageNode get(StringIdKey key) throws ServiceException;

    PagedData<ImageNode> all(PagingInfo pagingInfo) throws ServiceException;

    ImageNodeInspectResult inspect(ImageNodeInspectInfo info) throws ServiceException;

    ImageNodeFile downloadFile(ImageNodeFileDownloadInfo info) throws ServiceException;

    LongIdKey requestFileStreamVoucher(ImageNodeFileDownloadInfo info) throws ServiceException;

    ImageNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    ImageNodeThumbnail downloadThumbnail(ImageNodeThumbnailDownloadInfo info) throws ServiceException;

    void uploadFile(ImageNodeFileUploadInfo info) throws ServiceException;

    void uploadFileStream(ImageNodeFileStreamUploadInfo info) throws ServiceException;

    /**
     * 查看公共图片节点。
     *
     * @param info 公共图片节点查看信息。
     * @return 图片节点查看结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    ImageNodeInspectResult inspectForPublic(PublicImageNodeInspectInfo info) throws ServiceException;

    /**
     * 下载公共图片节点文件。
     *
     * @param info 公共图片节点文件下载信息。
     * @return 图片节点文件。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    ImageNodeFile downloadFileForPublic(PublicImageNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 请求下载公共图片节点文件流凭证。
     *
     * @param info 公共图片节点文件下载信息。
     * @return 下载图片节点文件流凭证主键。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    LongIdKey requestFileStreamVoucherForPublic(PublicImageNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 下载公共图片节点缩略图。
     *
     * @param info 公共图片节点缩略图下载信息。
     * @return 图片节点缩略图。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    ImageNodeThumbnail downloadThumbnailForPublic(PublicImageNodeThumbnailDownloadInfo info) throws ServiceException;
}
