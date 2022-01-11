package com.dwarfeng.familyhelper.webapi.stack.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCover;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCoverUploadInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemCoverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 项目封面响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface ItemCoverResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ItemCoverInfo get(LongIdKey key) throws ServiceException;

    PagedData<ItemCoverInfo> childForItem(LongIdKey itemKey, PagingInfo pagingInfo) throws ServiceException;

    ItemCover downloadItemCover(StringIdKey userKey, LongIdKey itemCoverKey) throws ServiceException;

    void uploadItemCover(StringIdKey userKey, ItemCoverUploadInfo itemCoverUploadInfo) throws ServiceException;

    void removeItemCover(StringIdKey userKey, LongIdKey itemCoverKey) throws ServiceException;

    void updateItemCoverOrder(StringIdKey userKey, ItemCoverOrderUpdateInfo itemCoverOrderUpdateInfo)
            throws ServiceException;
}
