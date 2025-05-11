package com.dwarfeng.familyhelper.webapi.stack.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.assets.stack.bean.key.PoacKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp.DispPoac;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 资产目录权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface PoacResponseService extends Service {

    boolean exists(PoacKey key) throws ServiceException;

    Poac get(PoacKey key) throws ServiceException;

    DispPoac getDisp(PoacKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Poac> childForAssetCatalog(LongIdKey assetCatalogKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPoac> childForAssetCatalogDisp(
            LongIdKey assetCatalogKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
