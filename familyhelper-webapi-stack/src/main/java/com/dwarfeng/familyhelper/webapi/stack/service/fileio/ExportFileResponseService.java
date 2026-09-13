package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.dto.File;
import com.dwarfeng.fileio.stack.bean.dto.FileDownloadInfo;
import com.dwarfeng.fileio.stack.bean.dto.FileStream;
import com.dwarfeng.fileio.stack.bean.dto.FileStreamDownloadInfo;
import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出文件响应服务。
 *
 * @author zhaofz
 * @author DwArFeng
 * @since 2.1.0
 */
public interface ExportFileResponseService extends Service {

    boolean exists(TaskItemKey key) throws ServiceException;

    ExportFileInfo get(TaskItemKey key) throws ServiceException;

    TaskItemKey insert(ExportFileInfo exportFileInfo) throws ServiceException;

    void update(ExportFileInfo exportFileInfo) throws ServiceException;

    void delete(TaskItemKey key) throws ServiceException;

    PagedData<ExportFileInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ExportFileInfo> childForTask(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException;

    File downloadFile(FileDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 下载导出任务文件流。
     *
     * <p>
     * 通过插件 <code>DubboRestExportFileOperateService</code> 以流式方式下载文件。
     *
     * @param downloadInfo 文件流下载信息。
     * @return 文件流。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 请求导出任务文件流下载凭证。
     *
     * @param downloadInfo 文件流下载信息。
     * @return 下载凭证主键。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    LongIdKey requestFileStreamVoucher(FileStreamDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 通过凭证下载导出任务文件流。
     *
     * @param voucherKey 下载凭证主键。
     * @return 文件流。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    FileStream downloadFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;
}
