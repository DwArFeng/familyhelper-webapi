package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCover;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCoverUploadInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemCoverInfo;
import com.dwarfeng.familyhelper.assets.stack.service.ItemCoverInfoMaintainService;
import com.dwarfeng.familyhelper.assets.stack.service.ItemCoverOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemCoverResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ItemCoverResponseServiceImpl implements ItemCoverResponseService {

    private final ItemCoverInfoMaintainService itemCoverInfoMaintainService;
    private final ItemCoverOperateService itemCoverOperateService;

    public ItemCoverResponseServiceImpl(
            @Qualifier("familyhelperAssetsItemCoverInfoMaintainService")
                    ItemCoverInfoMaintainService itemCoverInfoMaintainService,
            @Qualifier("familyhelperAssetsItemCoverOperateService")
                    ItemCoverOperateService itemCoverOperateService
    ) {
        this.itemCoverInfoMaintainService = itemCoverInfoMaintainService;
        this.itemCoverOperateService = itemCoverOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return itemCoverInfoMaintainService.exists(key);
    }

    @Override
    public ItemCoverInfo get(LongIdKey key) throws ServiceException {
        return itemCoverInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ItemCoverInfo> childForItem(LongIdKey itemKey, PagingInfo pagingInfo) throws ServiceException {
        return itemCoverInfoMaintainService.lookup(
                ItemCoverInfoMaintainService.CHILD_FOR_ITEM_INDEX_ASC, new Object[]{itemKey}, pagingInfo
        );
    }

    @Override
    public ItemCover downloadItemCover(StringIdKey userKey, LongIdKey itemCoverKey) throws ServiceException {
        return itemCoverOperateService.downloadItemCover(userKey, itemCoverKey);
    }

    @Override
    public void uploadItemCover(StringIdKey userKey, ItemCoverUploadInfo itemCoverUploadInfo) throws ServiceException {
        itemCoverOperateService.uploadItemCover(userKey, itemCoverUploadInfo);
    }

    @Override
    public void removeItemCover(StringIdKey userKey, LongIdKey itemCoverKey) throws ServiceException {
        itemCoverOperateService.removeItemCover(userKey, itemCoverKey);
    }

    @Override
    public void updateItemCoverOrder(StringIdKey userKey, ItemCoverOrderUpdateInfo itemCoverOrderUpdateInfo)
            throws ServiceException {
        itemCoverOperateService.updateItemCoverOrder(userKey, itemCoverOrderUpdateInfo);
    }
}
