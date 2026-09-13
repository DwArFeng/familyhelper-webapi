package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入模板响应服务。
 *
 * @author zhaofz
 * @author DwArFeng
 * @since 2.1.0
 */
public interface ImportTemplateResponseService extends Service {

    boolean exists(TaskSettingItemKey key) throws ServiceException;

    ImportTemplateInfo get(TaskSettingItemKey key) throws ServiceException;

    PagedData<ImportTemplateInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ImportTemplateInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

    TaskSettingItemKey create(TemplateCreateInfo createInfo) throws ServiceException;

    Template download(TemplateDownloadInfo downloadInfo) throws ServiceException;

    TemplateStream downloadStream(TemplateStreamDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 请求导入模板流下载凭证。
     *
     * <p>
     * 通过插件 <code>DubboRestImportTemplateOperateService</code> 申请流式下载凭证，
     * 供后续 <code>downloadStreamByVoucher</code> 两阶段下载使用。
     *
     * @param downloadInfo 模板流下载信息。
     * @return 下载凭证主键。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    LongIdKey requestStreamVoucher(TemplateStreamDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 通过凭证下载导入模板流。
     *
     * @param voucherKey 下载凭证主键。
     * @return 模板流。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    TemplateStream downloadStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    void upload(TemplateUploadInfo uploadInfo) throws ServiceException;

    void uploadStream(TemplateStreamUploadInfo uploadInfo) throws ServiceException;

    void remove(TemplateRemoveInfo removeInfo) throws ServiceException;
}
