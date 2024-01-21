package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.assets.stack.bean.key.PoacKey;
import com.dwarfeng.familyhelper.assets.stack.service.PoacMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispPoac;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.AssetCatalogResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.PoacResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("assetsPoacResponseServiceImpl")
public class PoacResponseServiceImpl implements PoacResponseService {

    private final PoacMaintainService poacMaintainService;

    private final AssetCatalogResponseService assetCatalogResponseService;
    private final AccountResponseService accountResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public PoacResponseServiceImpl(
            @Qualifier("familyhelperAssetsPoacMaintainService") PoacMaintainService poacMaintainService,
            AssetCatalogResponseService assetCatalogResponseService,
            AccountResponseService accountResponseService
    ) {
        this.poacMaintainService = poacMaintainService;
        this.assetCatalogResponseService = assetCatalogResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PoacKey key) throws ServiceException {
        return poacMaintainService.exists(key);
    }

    @Override
    public Poac get(PoacKey key) throws ServiceException {
        return poacMaintainService.get(key);
    }

    @Override
    public DispPoac getDisp(PoacKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Poac poac = poacMaintainService.get(key);
        return dispPoacFromPoac(poac, inspectAccountKey);
    }

    @Override
    public PagedData<Poac> childForAssetCatalog(LongIdKey assetCatalogKey, PagingInfo pagingInfo)
            throws ServiceException {
        return poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ASSET_CATALOG, new Object[]{assetCatalogKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPoac> childForAssetCatalogDisp(
            LongIdKey assetCatalogKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Poac> lookup = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ASSET_CATALOG, new Object[]{assetCatalogKey}, pagingInfo
        );
        List<DispPoac> dispPoacs = new ArrayList<>();
        for (Poac poac : lookup.getData()) {
            dispPoacs.add(dispPoacFromPoac(poac, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPoacs
        );
    }

    private DispPoac dispPoacFromPoac(Poac poac, StringIdKey inspectAccountKey) throws ServiceException {
        DispAssetCatalog assetCatalog = assetCatalogResponseService.getDisp(
                new LongIdKey(poac.getKey().getLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(poac.getKey().getStringId()), inspectAccountKey
        );
        return DispPoac.of(poac, assetCatalog, account);
    }
}
