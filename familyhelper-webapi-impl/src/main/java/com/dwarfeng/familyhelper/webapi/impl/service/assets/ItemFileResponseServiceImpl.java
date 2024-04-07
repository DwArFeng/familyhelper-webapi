package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.dto.*;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemFileInfo;
import com.dwarfeng.familyhelper.assets.stack.service.ItemFileInfoMaintainService;
import com.dwarfeng.familyhelper.assets.stack.service.ItemFileOperateService;
import com.dwarfeng.familyhelper.plugin.assets.bean.dto.*;
import com.dwarfeng.familyhelper.plugin.assets.service.DubboRestItemFileOperateService;
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
    private final DubboRestItemFileOperateService dubboRestItemFileOperateService;

    public ItemFileResponseServiceImpl(
            @Qualifier("familyhelperAssetsItemFileInfoMaintainService")
                    ItemFileInfoMaintainService itemFileInfoMaintainService,
            @Qualifier("familyhelperAssetsItemFileOperateService")
            ItemFileOperateService itemFileOperateService,
            @Qualifier("familyhelperPluginDubboRestItemFileOperateService")
            DubboRestItemFileOperateService dubboRestItemFileOperateService
    ) {
        this.itemFileInfoMaintainService = itemFileInfoMaintainService;
        this.itemFileOperateService = itemFileOperateService;
        this.dubboRestItemFileOperateService = dubboRestItemFileOperateService;
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
    public LongIdKey requestItemFileStreamVoucher(StringIdKey userKey, LongIdKey itemFileKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestItemFileOperateService.requestItemFileStreamVoucher(
                new DubboRestItemFileStreamDownloadInfo(userKey.getStringId(), itemFileKey.getLongId())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public ItemFileStream downloadItemFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestItemFileStream dubboRestItemFileStream =
                dubboRestItemFileOperateService.downloadItemFileStreamByVoucher(voucherIdWrapper);
        return new ItemFileStream(
                dubboRestItemFileStream.getOriginName(), dubboRestItemFileStream.getLength(),
                dubboRestItemFileStream.getContent()
        );
    }

    @Override
    public void uploadItemFile(StringIdKey userKey, ItemFileUploadInfo itemFileUploadInfo) throws ServiceException {
        itemFileOperateService.uploadItemFile(userKey, itemFileUploadInfo);
    }

    @Override
    public void uploadItemFileStream(StringIdKey userKey, ItemFileStreamUploadInfo itemFileStreamUploadInfo)
            throws ServiceException {
        dubboRestItemFileOperateService.uploadItemFileStream(new DubboRestItemFileStreamUploadInfo(
                userKey.getStringId(), itemFileStreamUploadInfo.getItemKey().getLongId(),
                itemFileStreamUploadInfo.getOriginName(), itemFileStreamUploadInfo.getLength(),
                itemFileStreamUploadInfo.getContent()
        ));
    }

    @Override
    public void updateItemFile(StringIdKey userKey, ItemFileUpdateInfo itemFileUpdateInfo) throws ServiceException {
        itemFileOperateService.updateItemFile(userKey, itemFileUpdateInfo);
    }

    @Override
    public void updateItemFileStream(StringIdKey userKey, ItemFileStreamUpdateInfo itemFileStreamUpdateInfo)
            throws ServiceException {
        dubboRestItemFileOperateService.updateItemFileStream(new DubboRestItemFileStreamUpdateInfo(
                userKey.getStringId(), itemFileStreamUpdateInfo.getItemFileKey().getLongId(),
                itemFileStreamUpdateInfo.getOriginName(), itemFileStreamUpdateInfo.getLength(),
                itemFileStreamUpdateInfo.getContent()
        ));
    }

    @Override
    public void removeItemFile(StringIdKey userKey, LongIdKey itemFileKey) throws ServiceException {
        itemFileOperateService.removeItemFile(userKey, itemFileKey);
    }
}
