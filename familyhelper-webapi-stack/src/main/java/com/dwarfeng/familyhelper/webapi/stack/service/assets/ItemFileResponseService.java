package com.dwarfeng.familyhelper.webapi.stack.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.*;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemFileInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 项目文件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface ItemFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ItemFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<ItemFileInfo> childForItem(LongIdKey itemKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<ItemFileInfo> childForItemInspectedDateDesc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ItemFileInfo> childForItemModifiedDateDesc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ItemFileInfo> childForItemOriginNameAsc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ItemFileInfo> childForItemCreatedDateAsc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException;

    ItemFile downloadItemFile(StringIdKey userKey, LongIdKey itemFileKey) throws ServiceException;

    LongIdKey requestItemFileStreamVoucher(StringIdKey userKey, LongIdKey itemFileKey) throws ServiceException;

    ItemFileStream downloadItemFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    void uploadItemFile(StringIdKey userKey, ItemFileUploadInfo itemFileUploadInfo) throws ServiceException;

    void uploadItemFileStream(StringIdKey userKey, ItemFileStreamUploadInfo itemFileStreamUploadInfo)
            throws ServiceException;

    void updateItemFile(StringIdKey userKey, ItemFileUpdateInfo itemFileUpdateInfo) throws ServiceException;

    void updateItemFileStream(StringIdKey userKey, ItemFileStreamUpdateInfo itemFileStreamUpdateInfo)
            throws ServiceException;

    void removeItemFile(StringIdKey userKey, LongIdKey itemFileKey) throws ServiceException;
}
