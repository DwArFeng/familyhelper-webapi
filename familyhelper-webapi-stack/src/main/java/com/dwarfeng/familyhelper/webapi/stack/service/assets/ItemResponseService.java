package com.dwarfeng.familyhelper.webapi.stack.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCreateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemUpdateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.Item;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispItem;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 项目响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface ItemResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Item get(LongIdKey key) throws ServiceException;

    PagedData<Item> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Item> childForAssetCatalog(LongIdKey assetCatalogKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<Item> childForAssetCatalogRoot(LongIdKey assetCatalogKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Item> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Item> childForAssetCatalogNameLike(
            LongIdKey assetCatalogKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    DispItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispItem> childForAssetCatalogDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispItem> childForAssetCatalogRootDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispItem> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispItem> childForAssetCatalogNameLikeDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<Item> pathFromRoot(LongIdKey key) throws ServiceException;

    PagedData<DispItem> pathFromRootDisp(StringIdKey accountKey, LongIdKey key) throws ServiceException;

    LongIdKey createItem(StringIdKey userKey, ItemCreateInfo itemCreateInfo)
            throws ServiceException;

    void updateItem(StringIdKey userKey, ItemUpdateInfo itemUpdateInfo) throws ServiceException;

    void removeItem(StringIdKey userKey, LongIdKey itemKey) throws ServiceException;
}
