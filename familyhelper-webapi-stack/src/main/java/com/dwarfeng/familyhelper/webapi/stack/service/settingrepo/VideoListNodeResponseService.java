package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoListNodeThumbnailDownloadInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.VideoListNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 视频列表节点响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface VideoListNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    VideoListNode get(StringIdKey key) throws ServiceException;

    PagedData<VideoListNode> all(PagingInfo pagingInfo) throws ServiceException;

    VideoListNodeSizeResult size(VideoListNodeSizeInfo info) throws ServiceException;

    VideoListNodeInspectResult inspect(VideoListNodeInspectInfo info) throws ServiceException;

    VideoListNodeFile downloadFile(VideoListNodeFileDownloadInfo info) throws ServiceException;

    LongIdKey requestFileStreamVoucher(VideoListNodeFileDownloadInfo info) throws ServiceException;

    VideoListNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    VideoListNodeThumbnail downloadThumbnail(VideoListNodeThumbnailDownloadInfo info) throws ServiceException;

    void uploadFile(VideoListNodeFileUploadInfo info) throws ServiceException;

    void uploadFileStream(VideoListNodeFileStreamUploadInfo info) throws ServiceException;

    void updateFile(VideoListNodeFileUpdateInfo info) throws ServiceException;

    void updateFileStream(VideoListNodeFileStreamUpdateInfo info) throws ServiceException;

    void changeOrder(VideoListNodeChangeOrderInfo info) throws ServiceException;

    void remove(VideoListNodeRemoveInfo info) throws ServiceException;

    /**
     * 公共视频列表节点的大小。
     *
     * @param info 公共视频列表节点大小信息。
     * @return 视频列表节点大小结果。
     * @throws ServiceException 服务异常。
     */
    VideoListNodeSizeResult sizeForPublic(PublicVideoListNodeSizeInfo info) throws ServiceException;

    /**
     * 查看公共视频列表节点。
     *
     * @param info 公共视频列表节点查看信息。
     * @return 公共视频列表节点查看结果。
     * @throws ServiceException 服务异常。
     */
    VideoListNodeInspectResult inspectForPublic(PublicVideoListNodeInspectInfo info) throws ServiceException;

    /**
     * 下载公共视频列表节点文件。
     *
     * @param info 公共视频列表节点文件下载信息。
     * @return 公共视频列表节点文件。
     * @throws ServiceException 服务异常。
     */
    VideoListNodeFile downloadFileForPublic(PublicVideoListNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 请求下载公共视频列表节点文件流凭证。
     *
     * @param info 公共视频列表节点文件下载信息。
     * @return 下载视频列表节点文件流凭证主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey requestFileStreamVoucherForPublic(PublicVideoListNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 下载公共视频列表节点缩略图。
     *
     * @param info 公共视频列表节点缩略图下载信息。
     * @return 视频列表节点缩略图。
     * @throws ServiceException 服务异常。
     */
    VideoListNodeThumbnail downloadThumbnailForPublic(PublicVideoListNodeThumbnailDownloadInfo info)
            throws ServiceException;
}
