package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCreateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemUpdateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.Item;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemLabel;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemTypeIndicator;
import com.dwarfeng.familyhelper.assets.stack.service.ItemLabelMaintainService;
import com.dwarfeng.familyhelper.assets.stack.service.ItemMaintainService;
import com.dwarfeng.familyhelper.assets.stack.service.ItemOperateService;
import com.dwarfeng.familyhelper.assets.stack.service.ItemTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispItem;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.AssetCatalogResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemResponseService;
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

@Service
public class ItemResponseServiceImpl implements ItemResponseService {

    private final ItemMaintainService itemMaintainService;
    private final ItemOperateService itemOperateService;
    private final ItemTypeIndicatorMaintainService itemTypeIndicatorMaintainService;
    private final ItemLabelMaintainService itemLabelMaintainService;

    private final AssetCatalogResponseService assetCatalogResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ItemResponseServiceImpl(
            @Qualifier("familyhelperAssetsItemMaintainService") ItemMaintainService itemMaintainService,
            @Qualifier("familyhelperAssetsItemOperateService") ItemOperateService itemOperateService,
            @Qualifier("familyhelperAssetsItemTypeIndicatorMaintainService")
                    ItemTypeIndicatorMaintainService itemTypeIndicatorMaintainService,
            @Qualifier("familyhelperAssetsItemLabelMaintainService") ItemLabelMaintainService itemLabelMaintainService,
            AssetCatalogResponseService assetCatalogResponseService
    ) {
        this.itemMaintainService = itemMaintainService;
        this.itemOperateService = itemOperateService;
        this.itemTypeIndicatorMaintainService = itemTypeIndicatorMaintainService;
        this.itemLabelMaintainService = itemLabelMaintainService;
        this.assetCatalogResponseService = assetCatalogResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return itemMaintainService.exists(key);
    }

    @Override
    public Item get(LongIdKey key) throws ServiceException {
        return itemMaintainService.get(key);
    }

    @Override
    public PagedData<Item> all(PagingInfo pagingInfo) throws ServiceException {
        return itemMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Item> childForAssetCatalog(LongIdKey assetCatalogKey, PagingInfo pagingInfo)
            throws ServiceException {
        return itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_ASSET_CATALOG, new Object[]{assetCatalogKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Item> childForAssetCatalogRoot(LongIdKey assetCatalogKey, PagingInfo pagingInfo) throws ServiceException {
        return itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_ASSET_CATALOG_ROOT, new Object[]{assetCatalogKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Item> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException {
        return itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo
        );
    }

    @Override
    public DispItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Item item = itemMaintainService.get(key);
        return dispItemFromItem(item, inspectAccountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<Item> lookup = itemMaintainService.lookup(pagingInfo);
        List<DispItem> dispItems = new ArrayList<>();
        for (Item item : lookup.getData()) {
            dispItems.add(dispItemFromItem(item, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispItems
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispItem> childForAssetCatalogDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Item> lookup = itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_ASSET_CATALOG, new Object[]{assetCatalogKey}, pagingInfo
        );
        List<DispItem> dispItems = new ArrayList<>();
        for (Item item : lookup.getData()) {
            dispItems.add(dispItemFromItem(item, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispItems
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispItem> childForAssetCatalogRootDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Item> lookup = itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_ASSET_CATALOG_ROOT, new Object[]{assetCatalogKey}, pagingInfo
        );
        List<DispItem> dispItems = new ArrayList<>();
        for (Item item : lookup.getData()) {
            dispItems.add(dispItemFromItem(item, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispItems
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispItem> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Item> lookup = itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo
        );
        List<DispItem> dispItems = new ArrayList<>();
        for (Item item : lookup.getData()) {
            dispItems.add(dispItemFromItem(item, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispItems
        );
    }

    private DispItem dispItemFromItem(Item item, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispAssetCatalog assetCatalog = null;
        if (Objects.nonNull(item.getAssetCatalogKey())) {
            assetCatalog = assetCatalogResponseService.getDisp(item.getAssetCatalogKey(), inspectAccountKey);
        }

        ItemTypeIndicator typeIndicator = null;
        if (Objects.nonNull(item.getItemType())) {
            typeIndicator = itemTypeIndicatorMaintainService.getIfExists(new StringIdKey(item.getItemType()));
        }

        List<ItemLabel> itemLabels = itemLabelMaintainService.lookup(
                ItemLabelMaintainService.CHILD_FOR_ITEM, new Object[]{item.getKey()}
        ).getData();

        boolean hasNoChild = itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_PARENT, new Object[]{item.getKey()}, new PagingInfo(0, 1)
        ).getCount() <= 0;

        return DispItem.of(item, assetCatalog, typeIndicator, itemLabels, hasNoChild);
    }

    @Override
    public LongIdKey createItem(StringIdKey userKey, ItemCreateInfo itemCreateInfo) throws
            ServiceException {
        return itemOperateService.createItem(userKey, itemCreateInfo);
    }

    @Override
    public void updateItem(StringIdKey userKey, ItemUpdateInfo itemUpdateInfo) throws ServiceException {
        itemOperateService.updateItem(userKey, itemUpdateInfo);
    }

    @Override
    public void removeItem(StringIdKey userKey, LongIdKey itemKey) throws ServiceException {
        itemOperateService.removeItem(userKey, itemKey);
    }
}
