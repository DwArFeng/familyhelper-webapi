package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

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
}
