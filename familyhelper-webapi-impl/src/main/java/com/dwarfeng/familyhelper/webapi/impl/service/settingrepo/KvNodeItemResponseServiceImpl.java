package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.KvNodeItemResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.KvNodeItem;
import com.dwarfeng.settingrepo.stack.bean.key.KvNodeItemKey;
import com.dwarfeng.settingrepo.stack.service.KvNodeItemMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KvNodeItemResponseServiceImpl implements KvNodeItemResponseService {

    private final KvNodeItemMaintainService kvNodeItemMaintainService;

    public KvNodeItemResponseServiceImpl(
            @Qualifier("settingrepoKvNodeItemMaintainService") KvNodeItemMaintainService kvNodeItemMaintainService
    ) {
        this.kvNodeItemMaintainService = kvNodeItemMaintainService;
    }

    @Override
    public boolean exists(KvNodeItemKey key) throws ServiceException {
        return kvNodeItemMaintainService.exists(key);
    }

    @Override
    public KvNodeItem get(KvNodeItemKey key) throws ServiceException {
        return kvNodeItemMaintainService.get(key);
    }

    @Override
    public PagedData<KvNodeItem> all(PagingInfo pagingInfo) throws ServiceException {
        return kvNodeItemMaintainService.lookup(
                KvNodeItemMaintainService.STRING_ID_ASC,
                new Object[0],
                pagingInfo
        );
    }

    @Override
    public PagedData<KvNodeItem> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException {
        return kvNodeItemMaintainService.lookup(
                KvNodeItemMaintainService.CHILD_FOR_NODE_ITEM_STRING_ID_ASC,
                new Object[]{nodeKey},
                pagingInfo
        );
    }
}
