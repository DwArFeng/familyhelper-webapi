package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeThumbnailDownloadInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.ImageListNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 图像列表节点响应服务
 *
 * @author DwArFeng
 * @since 1.3.0
 */
public interface ImageListNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ImageListNode get(StringIdKey key) throws ServiceException;

    PagedData<ImageListNode> all(PagingInfo pagingInfo) throws ServiceException;

    ImageListNodeSizeResult size(ImageListNodeSizeInfo info) throws ServiceException;

    ImageListNodeInspectResult inspect(ImageListNodeInspectInfo info) throws ServiceException;

    ImageListNodeFile downloadFile(ImageListNodeFileDownloadInfo info) throws ServiceException;

    LongIdKey requestFileStreamVoucher(ImageListNodeFileDownloadInfo info) throws ServiceException;

    ImageListNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    ImageListNodeThumbnail downloadThumbnail(ImageListNodeThumbnailDownloadInfo info) throws ServiceException;

    void uploadFile(ImageListNodeFileUploadInfo info) throws ServiceException;

    void uploadFileStream(ImageListNodeFileStreamUploadInfo info) throws ServiceException;

    void updateFile(ImageListNodeFileUpdateInfo info) throws ServiceException;

    void updateFileStream(ImageListNodeFileStreamUpdateInfo info) throws ServiceException;

    void changeOrder(ImageListNodeChangeOrderInfo info) throws ServiceException;

    void remove(ImageListNodeRemoveInfo info) throws ServiceException;

    /**
     * 公共图片列表节点的大小。
     *
     * @param info 公共图片列表节点大小信息。
     * @return 图片列表节点大小结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    ImageListNodeSizeResult sizeForPublic(PublicImageListNodeSizeInfo info) throws ServiceException;

    /**
     * 查看公共图片列表节点。
     *
     * @param info 公共图片列表节点查看信息。
     * @return 公共图片列表节点查看结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    ImageListNodeInspectResult inspectForPublic(PublicImageListNodeInspectInfo info) throws ServiceException;

    /**
     * 下载公共图片列表节点文件。
     *
     * @param info 公共图片列表节点文件下载信息。
     * @return 公共图片列表节点文件。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    ImageListNodeFile downloadFileForPublic(PublicImageListNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 请求下载公共图片列表节点文件流凭证。
     *
     * @param info 公共图片列表节点文件下载信息。
     * @return 下载图片列表节点文件流凭证主键。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    LongIdKey requestFileStreamVoucherForPublic(PublicImageListNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 下载公共图片列表节点缩略图。
     *
     * @param info 公共图片列表节点缩略图下载信息。
     * @return 图片列表节点缩略图。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    ImageListNodeThumbnail downloadThumbnailForPublic(PublicImageListNodeThumbnailDownloadInfo info)
            throws ServiceException;
}
