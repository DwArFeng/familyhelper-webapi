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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ItemResponseServiceImpl implements ItemResponseService {

    private final ItemMaintainService itemMaintainService;
    private final ItemOperateService itemOperateService;
    private final ItemTypeIndicatorMaintainService itemTypeIndicatorMaintainService;
    private final ItemLabelMaintainService itemLabelMaintainService;

    private final AssetCatalogResponseService assetCatalogResponseService;

    public ItemResponseServiceImpl(
            @Qualifier("familyhelperAssetsItemMaintainService")
            ItemMaintainService itemMaintainService,
            @Qualifier("familyhelperAssetsItemOperateService")
            ItemOperateService itemOperateService,
            @Qualifier("familyhelperAssetsItemTypeIndicatorMaintainService")
            ItemTypeIndicatorMaintainService itemTypeIndicatorMaintainService,
            @Qualifier("familyhelperAssetsItemLabelMaintainService")
            ItemLabelMaintainService itemLabelMaintainService,
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
    public PagedData<Item> childForAssetCatalogNameLike(LongIdKey assetCatalogKey, String pattern, PagingInfo pagingInfo) throws ServiceException {
        return itemMaintainService.lookup(
                ItemMaintainService.CHILD_FOR_ASSET_CATALOG_NAME_LIKE,
                new Object[]{assetCatalogKey, pattern},
                pagingInfo
        );
    }

    @Override
    public DispItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Item item = itemMaintainService.get(key);
        return toDisp(item, inspectAccountKey);
    }

    @Override
    public PagedData<DispItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<Item> lookup = all(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispItem> childForAssetCatalogDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Item> lookup = childForAssetCatalog(assetCatalogKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispItem> childForAssetCatalogRootDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Item> lookup = childForAssetCatalogRoot(assetCatalogKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispItem> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Item> lookup = childForParent(parentKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispItem> childForAssetCatalogNameLikeDisp(
            StringIdKey accountKey, LongIdKey assetCatalogKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Item> lookup = childForAssetCatalogNameLike(assetCatalogKey, pattern, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    private DispItem toDisp(Item item, StringIdKey inspectAccountKey)
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

    private PagedData<DispItem> toDispPagedData(PagedData<Item> itemPagedData, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispItem> dispItems = new ArrayList<>();
        for (Item item : itemPagedData.getData()) {
            dispItems.add(toDisp(item, inspectAccountKey));
        }
        return new PagedData<>(
                itemPagedData.getCurrentPage(), itemPagedData.getTotalPages(), itemPagedData.getRows(),
                itemPagedData.getCount(), dispItems
        );
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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public List<LongIdKey> pathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的权限组。
        Item item = itemMaintainService.get(key);

        // 如果权限组没有父节点，则返回空列表。
        if (Objects.isNull(item.getParentKey())) {
            return new ArrayList<>();
        }

        // 定义结果列表。
        List<LongIdKey> result = new ArrayList<>();

        // 获取权限组的父节点主键，作为当前节点主键。
        LongIdKey anchorKey = item.getParentKey();

        // 将当前节点主键添加到结果列表中。
        result.add(anchorKey);

        // 循环获取父节点的父节点，直到父节点为空。
        while (Objects.nonNull(anchorKey)) {
            // 获取当前节点。
            item = itemMaintainService.get(anchorKey);

            // 获取当前节点的父节点主键。
            LongIdKey parentKey = item.getParentKey();

            // 如果当前节点没有父节点，则跳出循环。
            if (Objects.isNull(parentKey)) {
                break;
            }

            // 将父节点主键添加到结果列表中。
            result.add(parentKey);

            // 将父节点主键作为当前节点主键。
            anchorKey = parentKey;
        }

        // 将结果列表反转，并返回。
        Collections.reverse(result);
        return result;
    }
}
