package com.dwarfeng.familyhelper.webapi.impl.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemTypeIndicator;
import com.dwarfeng.familyhelper.assets.stack.service.ItemTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeIndicatorResponseServiceImpl implements ItemTypeIndicatorResponseService {

    private final ItemTypeIndicatorMaintainService itemTypeIndicatorMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ItemTypeIndicatorResponseServiceImpl(
            @Qualifier("familyhelperAssetsItemTypeIndicatorMaintainService")
                    ItemTypeIndicatorMaintainService itemTypeIndicatorMaintainService
    ) {
        this.itemTypeIndicatorMaintainService = itemTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return itemTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public ItemTypeIndicator get(StringIdKey key) throws ServiceException {
        return itemTypeIndicatorMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(ItemTypeIndicator itemTypeIndicator) throws ServiceException {
        return itemTypeIndicatorMaintainService.insert(itemTypeIndicator);
    }

    @Override
    public void update(ItemTypeIndicator itemTypeIndicator) throws ServiceException {
        itemTypeIndicatorMaintainService.update(itemTypeIndicator);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        itemTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<ItemTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return itemTypeIndicatorMaintainService.lookup(pagingInfo);
    }
}
