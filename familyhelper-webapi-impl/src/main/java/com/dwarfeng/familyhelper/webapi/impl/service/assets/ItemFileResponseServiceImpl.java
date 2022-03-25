package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemFile;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemFileUpdateInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemFileUploadInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemFileInfo;
import com.dwarfeng.familyhelper.assets.stack.service.ItemFileInfoMaintainService;
import com.dwarfeng.familyhelper.assets.stack.service.ItemFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ItemFileResponseServiceImpl implements ItemFileResponseService {

    private final ItemFileInfoMaintainService itemFileInfoMaintainService;
    private final ItemFileOperateService itemFileOperateService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ItemFileResponseServiceImpl(
            @Qualifier("familyhelperAssetsItemFileInfoMaintainService")
                    ItemFileInfoMaintainService itemFileInfoMaintainService,
            @Qualifier("familyhelperAssetsItemFileOperateService")
                    ItemFileOperateService itemFileOperateService
    ) {
        this.itemFileInfoMaintainService = itemFileInfoMaintainService;
        this.itemFileOperateService = itemFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return itemFileInfoMaintainService.exists(key);
    }

    @Override
    public ItemFileInfo get(LongIdKey key) throws ServiceException {
        return itemFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ItemFileInfo> childForItem(LongIdKey itemKey, PagingInfo pagingInfo) throws ServiceException {
        return itemFileInfoMaintainService.lookup(
                ItemFileInfoMaintainService.CHILD_FOR_ITEM, new Object[]{itemKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ItemFileInfo> childForItemInspectedDateDesc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException {
        return itemFileInfoMaintainService.lookup(
                ItemFileInfoMaintainService.CHILD_FOR_ITEM_INSPECTED_DATE_DESC, new Object[]{itemKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ItemFileInfo> childForItemModifiedDateDesc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException {
        return itemFileInfoMaintainService.lookup(
                ItemFileInfoMaintainService.CHILD_FOR_ITEM_MODIFIED_DATE_DESC, new Object[]{itemKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ItemFileInfo> childForItemOriginNameAsc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException {
        return itemFileInfoMaintainService.lookup(
                ItemFileInfoMaintainService.CHILD_FOR_ITEM_ORIGIN_NAME_ASC, new Object[]{itemKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ItemFileInfo> childForItemCreatedDateAsc(LongIdKey itemKey, PagingInfo pagingInfo)
            throws ServiceException {
        return itemFileInfoMaintainService.lookup(
                ItemFileInfoMaintainService.CHILD_FOR_ITEM_CREATED_DATE_ASC, new Object[]{itemKey}, pagingInfo
        );
    }

    @Override
    public ItemFile downloadItemFile(StringIdKey userKey, LongIdKey itemFileKey) throws ServiceException {
        return itemFileOperateService.downloadItemFile(userKey, itemFileKey);
    }

    @Override
    public void uploadItemFile(StringIdKey userKey, ItemFileUploadInfo itemFileUploadInfo) throws ServiceException {
        itemFileOperateService.uploadItemFile(userKey, itemFileUploadInfo);
    }

    @Override
    public void updateItemFile(StringIdKey userKey, ItemFileUpdateInfo itemFileUpdateInfo) throws ServiceException {
        itemFileOperateService.updateItemFile(userKey, itemFileUpdateInfo);
    }

    @Override
    public void removeItemFile(StringIdKey userKey, LongIdKey itemFileKey) throws ServiceException {
        itemFileOperateService.removeItemFile(userKey, itemFileKey);
    }
}
