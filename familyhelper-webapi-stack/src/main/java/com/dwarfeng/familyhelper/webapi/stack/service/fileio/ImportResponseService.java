package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入响应服务。
 *
 * @author zhaofz
 * @author DwArFeng
 * @since 2.1.0
 */
public interface ImportResponseService extends Service {

    LongIdKey createTask(TaskCreateInfo createInfo) throws ServiceException;

    void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws ServiceException;

    void uploadFile(FileUploadInfo uploadInfo) throws ServiceException;

    void uploadFileStream(FileStreamUploadInfo uploadInfo) throws ServiceException;

    void executeTask(TaskExecuteInfo executeInfo) throws ServiceException;

    void executeTaskAsync(TaskExecuteInfo executeInfo) throws ServiceException;

    File downloadFile(FileDownloadInfo downloadInfo) throws ServiceException;

    FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 请求导入任务文件流下载凭证。
     *
     * <p>
     * 通过插件 <code>DubboRestImportFileOperateService</code> 申请流式下载凭证，
     * 供后续 <code>downloadFileStreamByVoucher</code> 两阶段下载使用。
     *
     * @param downloadInfo 文件流下载信息。
     * @return 下载凭证主键。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    LongIdKey requestFileStreamVoucher(FileStreamDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 通过凭证下载导入任务文件流。
     *
     * @param voucherKey 下载凭证主键。
     * @return 文件流。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    FileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;
}
