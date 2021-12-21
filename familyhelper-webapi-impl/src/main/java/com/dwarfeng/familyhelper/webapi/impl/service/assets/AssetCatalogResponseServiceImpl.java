package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.AssetCatalogCreateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.AssetCatalogUpdateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.PermissionUpsertInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.AssetCatalog;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.assets.stack.service.AssetCatalogMaintainService;
import com.dwarfeng.familyhelper.assets.stack.service.AssetCatalogOperateService;
import com.dwarfeng.familyhelper.assets.stack.service.PoacMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.AssetCatalogResponseService;
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
import java.util.Objects;
import java.util.Optional;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AssetCatalogResponseServiceImpl implements AssetCatalogResponseService {

    private final AssetCatalogMaintainService assetCatalogMaintainService;
    private final PoacMaintainService poacMaintainService;
    private final AssetCatalogOperateService assetCatalogOperateService;

    private final AccountResponseService accountResponseService;

    public AssetCatalogResponseServiceImpl(
            @Qualifier("familyhelperAssetsAssetCatalogMaintainService")
                    AssetCatalogMaintainService assetCatalogMaintainService,
            @Qualifier("familyhelperAssetsPoacMaintainService")
                    PoacMaintainService poacMaintainService,
            @Qualifier("familyhelperAssetsAssetCatalogOperateService")
                    AssetCatalogOperateService assetCatalogOperateService,
            AccountResponseService accountResponseService
    ) {
        this.assetCatalogMaintainService = assetCatalogMaintainService;
        this.poacMaintainService = poacMaintainService;
        this.assetCatalogOperateService = assetCatalogOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return assetCatalogMaintainService.exists(key);
    }

    @Override
    public AssetCatalog get(LongIdKey key) throws ServiceException {
        return assetCatalogMaintainService.get(key);
    }

    @Override
    public PagedData<AssetCatalog> all(PagingInfo pagingInfo) throws ServiceException {
        return assetCatalogMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispAssetCatalog getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        AssetCatalog assetCatalog = assetCatalogMaintainService.get(key);
        return dispAssetCatalogFromAssetCatalog(assetCatalog, inspectAccountKey);
    }

    private DispAssetCatalog dispAssetCatalogFromAssetCatalog(AssetCatalog assetCatalog, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<Poac> relatedPoacs = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ASSET_CATALOG, new Object[]{assetCatalog.getKey()}
        ).getData();
        Poac ownerPoac = relatedPoacs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Poac.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Poac myPoac = relatedPoacs.stream().filter(
                p -> Objects.equals(p.getKey().getStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoac)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPoac.getKey().getStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPoac).map(Poac::getPermissionLevel).orElse(null);
        return DispAssetCatalog.of(assetCatalog, ownerAccount, permissionLevel);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispAssetCatalog> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poac> lookup = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<DispAssetCatalog> dispAssetCatalogs = new ArrayList<>();
        for (Poac poac : lookup.getData()) {
            dispAssetCatalogs.add(dispAssetCatalogFromPoac(poac, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispAssetCatalogs
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispAssetCatalog> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poac> lookup = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Poac.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        List<DispAssetCatalog> dispAssetCatalogs = new ArrayList<>();
        for (Poac poac : lookup.getData()) {
            dispAssetCatalogs.add(dispAssetCatalogFromPoac(poac, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispAssetCatalogs
        );
    }

    private DispAssetCatalog dispAssetCatalogFromPoac(Poac poac, StringIdKey inspectAccountKey) throws ServiceException {
        AssetCatalog assetCatalog = assetCatalogMaintainService.get(new LongIdKey(poac.getKey().getLongId()));
        List<Poac> relatedPoacs = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ASSET_CATALOG, new Object[]{assetCatalog.getKey()}
        ).getData();
        Poac ownerPoac = relatedPoacs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Poac.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoac)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPoac.getKey().getStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = poac.getPermissionLevel();
        return DispAssetCatalog.of(assetCatalog, ownerAccount, permissionLevel);
    }

    @Override
    public LongIdKey createAssetCatalog(StringIdKey userKey, AssetCatalogCreateInfo assetCatalogCreateInfo)
            throws ServiceException {
        return assetCatalogOperateService.createAssetCatalog(userKey, assetCatalogCreateInfo);
    }

    @Override
    public void updateAssetCatalog(StringIdKey userKey, AssetCatalogUpdateInfo assetCatalogUpdateInfo)
            throws ServiceException {
        assetCatalogOperateService.updateAssetCatalog(userKey, assetCatalogUpdateInfo);
    }

    @Override
    public void removeAssetCatalog(StringIdKey userKey, LongIdKey assetCatalogKey) throws ServiceException {
        assetCatalogOperateService.removeAssetCatalog(userKey, assetCatalogKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, PermissionUpsertInfo permissionUpsertInfo) throws ServiceException {
        assetCatalogOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, PermissionRemoveInfo permissionRemoveInfo) throws ServiceException {
        assetCatalogOperateService.removePermission(userKey, permissionRemoveInfo);
    }
}
