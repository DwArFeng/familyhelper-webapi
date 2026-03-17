package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicFileNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.FileNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 文件节点响应服务。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public interface FileNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    FileNode get(StringIdKey key) throws ServiceException;

    PagedData<FileNode> all(PagingInfo pagingInfo) throws ServiceException;

    FileNodeInspectResult inspect(FileNodeInspectInfo info) throws ServiceException;

    FileNodeFile downloadFile(FileNodeFileDownloadInfo info) throws ServiceException;

    LongIdKey requestFileStreamVoucher(FileNodeFileDownloadInfo info) throws ServiceException;

    FileNodeFileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    void uploadFile(FileNodeFileUploadInfo info) throws ServiceException;

    void uploadFileStream(FileNodeFileStreamUploadInfo info) throws ServiceException;

    /**
     * 查看公共文件节点。
     *
     * @param info 公共文件节点查看信息。
     * @return 文件节点查看结果。
     * @throws ServiceException 服务异常。
     */
    FileNodeInspectResult inspectForPublic(PublicFileNodeInspectInfo info) throws ServiceException;

    /**
     * 下载公共文件节点文件。
     *
     * @param info 公共文件节点文件下载信息。
     * @return 文件节点文件。
     * @throws ServiceException 服务异常。
     */
    FileNodeFile downloadFileForPublic(PublicFileNodeFileDownloadInfo info) throws ServiceException;

    /**
     * 请求下载公共文件节点文件流凭证。
     *
     * @param info 公共文件节点文件下载信息。
     * @return 下载文件节点文件流凭证主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey requestFileStreamVoucherForPublic(PublicFileNodeFileDownloadInfo info) throws ServiceException;
}
