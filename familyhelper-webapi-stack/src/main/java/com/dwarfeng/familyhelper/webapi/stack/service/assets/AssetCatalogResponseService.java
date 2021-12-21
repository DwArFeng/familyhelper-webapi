package com.dwarfeng.familyhelper.webapi.stack.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.AssetCatalogCreateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.AssetCatalogUpdateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.PermissionUpsertInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.AssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 资产目录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface AssetCatalogResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    AssetCatalog get(LongIdKey key) throws ServiceException;

    PagedData<AssetCatalog> all(PagingInfo pagingInfo) throws ServiceException;

    DispAssetCatalog getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispAssetCatalog> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispAssetCatalog> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createAssetCatalog(StringIdKey userKey, AssetCatalogCreateInfo assetCatalogCreateInfo)
            throws ServiceException;

    void updateAssetCatalog(StringIdKey userKey, AssetCatalogUpdateInfo assetCatalogUpdateInfo) throws ServiceException;

    void removeAssetCatalog(StringIdKey userKey, LongIdKey assetCatalogKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, PermissionUpsertInfo permissionUpsertInfo) throws ServiceException;

    void removePermission(StringIdKey userKey, PermissionRemoveInfo permissionRemoveInfo) throws ServiceException;
}
