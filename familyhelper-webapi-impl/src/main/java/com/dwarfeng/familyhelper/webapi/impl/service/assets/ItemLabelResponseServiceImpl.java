package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemLabel;
import com.dwarfeng.familyhelper.assets.stack.service.ItemLabelMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemLabelResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ItemLabelResponseServiceImpl implements ItemLabelResponseService {

    private final ItemLabelMaintainService itemLabelMaintainService;

    public ItemLabelResponseServiceImpl(
            @Qualifier("familyhelperAssetsItemLabelMaintainService")
                    ItemLabelMaintainService itemLabelMaintainService
    ) {
        this.itemLabelMaintainService = itemLabelMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return itemLabelMaintainService.exists(key);
    }

    @Override
    public ItemLabel get(StringIdKey key) throws ServiceException {
        return itemLabelMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(ItemLabel itemLabel) throws ServiceException {
        return itemLabelMaintainService.insert(itemLabel);
    }

    @Override
    public void update(ItemLabel itemLabel) throws ServiceException {
        itemLabelMaintainService.update(itemLabel);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        itemLabelMaintainService.delete(key);
    }

    @Override
    public boolean allExits(List<StringIdKey> keys) throws ServiceException {
        return itemLabelMaintainService.allExists(keys);
    }

    @Override
    public PagedData<ItemLabel> all(PagingInfo pagingInfo) throws ServiceException {
        return itemLabelMaintainService.lookup(pagingInfo);
    }
}
