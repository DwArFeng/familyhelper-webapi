package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicVideoNodeThumbnailDownloadInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.VideoNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 视频节点响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface VideoNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    VideoNode get(StringIdKey key) throws ServiceException;

    PagedData<VideoNode> all(PagingInfo pagingInfo) throws ServiceException;

    VideoNodeInspectResult inspect(VideoNodeInspectInfo info) throws ServiceException;

    VideoNodeFile downloadFile(VideoNodeFileDownloadInfo info) throws ServiceException;

    LongIdKey requestFileStreamVoucher(VideoNodeFileDownloadInfo info) throws ServiceException;

    VideoNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    VideoNodeThumbnail downloadThumbnail(VideoNodeThumbnailDownloadInfo info) throws ServiceException;

    void uploadFile(VideoNodeFileUploadInfo info) throws ServiceException;

    void uploadFileStream(VideoNodeFileStreamUploadInfo info) throws ServiceException;

    /**
     * 查看公共视频节点。
     *
     * @param info 公共视频节点查看信息。
     * @return 视频节点查看结果。
     * @throws ServiceException 服务异常。
     */
    VideoNodeInspectResult inspectForPublic(PublicVideoNodeInspectInfo info) throws ServiceException;

    /**
     * 下载公共视频节点文件。
     *
     * @param info 公共视频节点文件下载信息。
     * @return 视频节点文件。
     * @throws ServiceException 服务异常。
     */
    VideoNodeFile downloadFileForPublic(PublicVideoNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 请求下载公共视频节点文件流凭证。
     *
     * @param info 公共视频节点文件下载信息。
     * @return 下载视频节点文件流凭证主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey requestFileStreamVoucherForPublic(PublicVideoNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 下载公共视频节点缩略图。
     *
     * @param info 公共视频节点缩略图下载信息。
     * @return 视频节点缩略图。
     * @throws ServiceException 服务异常。
     */
    VideoNodeThumbnail downloadThumbnailForPublic(PublicVideoNodeThumbnailDownloadInfo info) throws ServiceException;
}
