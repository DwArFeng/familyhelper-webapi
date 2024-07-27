package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

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
}
