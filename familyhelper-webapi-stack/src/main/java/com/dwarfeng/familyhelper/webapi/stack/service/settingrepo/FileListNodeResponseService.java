package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileListNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileListNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileListNodeSizeInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.FileListNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 文件列表节点响应服务。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public interface FileListNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    FileListNode get(StringIdKey key) throws ServiceException;

    PagedData<FileListNode> all(PagingInfo pagingInfo) throws ServiceException;

    FileListNodeSizeResult size(FileListNodeSizeInfo info) throws ServiceException;

    FileListNodeInspectResult inspect(FileListNodeInspectInfo info) throws ServiceException;

    FileListNodeFile downloadFile(FileListNodeFileDownloadInfo info) throws ServiceException;

    LongIdKey requestFileStreamVoucher(FileListNodeFileDownloadInfo info) throws ServiceException;

    FileListNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    void uploadFile(FileListNodeFileUploadInfo info) throws ServiceException;

    void uploadFileStream(FileListNodeFileStreamUploadInfo info) throws ServiceException;

    void updateFile(FileListNodeFileUpdateInfo info) throws ServiceException;

    void updateFileStream(FileListNodeFileStreamUpdateInfo info) throws ServiceException;

    void changeOrder(FileListNodeChangeOrderInfo info) throws ServiceException;

    void remove(FileListNodeRemoveInfo info) throws ServiceException;

    /**
     * 公共文件列表节点的大小。
     *
     * @param info 公共文件列表节点大小信息。
     * @return 文件列表节点大小结果。
     * @throws ServiceException 服务异常。
     */
    FileListNodeSizeResult sizeForPublic(PublicFileListNodeSizeInfo info) throws ServiceException;

    /**
     * 查看公共文件列表节点。
     *
     * @param info 公共文件列表节点查看信息。
     * @return 公共文件列表节点查看结果。
     * @throws ServiceException 服务异常。
     */
    FileListNodeInspectResult inspectForPublic(PublicFileListNodeInspectInfo info) throws ServiceException;

    /**
     * 下载公共文件列表节点文件。
     *
     * @param info 公共文件列表节点文件下载信息。
     * @return 公共文件列表节点文件。
     * @throws ServiceException 服务异常。
     */
    FileListNodeFile downloadFileForPublic(PublicFileListNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 请求下载公共文件列表节点文件流凭证。
     *
     * @param info 公共文件列表节点文件下载信息。
     * @return 下载文件列表节点文件流凭证主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey requestFileStreamVoucherForPublic(PublicFileListNodeFileDownloadInfo info) throws ServiceException;
}
