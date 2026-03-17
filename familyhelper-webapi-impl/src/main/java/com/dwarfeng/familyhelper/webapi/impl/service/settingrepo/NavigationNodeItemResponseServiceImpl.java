package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispNavigationNodeItem;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.NavigationNodeItemResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.NavigationNodeItem;
import com.dwarfeng.settingrepo.stack.service.NavigationNodeItemMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
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

/**
 * 导航节点条目响应服务实现。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
@Service
public class NavigationNodeItemResponseServiceImpl implements NavigationNodeItemResponseService {

    private final NavigationNodeItemMaintainService navigationNodeItemMaintainService;

    public NavigationNodeItemResponseServiceImpl(
            @Qualifier("settingrepoNavigationNodeItemMaintainService")
            NavigationNodeItemMaintainService navigationNodeItemMaintainService
    ) {
        this.navigationNodeItemMaintainService = navigationNodeItemMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return navigationNodeItemMaintainService.exists(key);
    }

    @Override
    public NavigationNodeItem get(LongIdKey key) throws ServiceException {
        return navigationNodeItemMaintainService.get(key);
    }

    @Override
    public PagedData<NavigationNodeItem> childForNodeNameLike(
            StringIdKey nodeKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return navigationNodeItemMaintainService.lookup(
                NavigationNodeItemMaintainService.CHILD_FOR_NODE_NAME_LIKE,
                new Object[]{nodeKey, pattern}, pagingInfo
        );
    }

    @Override
    public PagedData<NavigationNodeItem> childForNodeChildForRoot(StringIdKey nodeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return navigationNodeItemMaintainService.lookup(
                NavigationNodeItemMaintainService.CHILD_FOR_NODE_CHILD_FOR_PARENT_INDEX_ASC,
                new Object[]{nodeKey, null},
                pagingInfo
        );
    }

    @Override
    public PagedData<NavigationNodeItem> childForParent(LongIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException {
        return navigationNodeItemMaintainService.lookup(
                NavigationNodeItemMaintainService.CHILD_FOR_PARENT_INDEX_ASC, new Object[]{parentKey}, pagingInfo
        );
    }

    @Override
    public PagedData<NavigationNodeItem> pathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的导航节点条目作为锚点。
        NavigationNodeItem anchor = navigationNodeItemMaintainService.get(key);

        // 定义结果列表。
        List<NavigationNodeItem> result = new ArrayList<>();

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的导航节点条目。
            anchor = navigationNodeItemMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public DispNavigationNodeItem getDisp(LongIdKey key) throws ServiceException {
        NavigationNodeItem navigationNodeItem = get(key);
        return toDisp(navigationNodeItem);
    }

    @Override
    public PagedData<DispNavigationNodeItem> childForNodeNameLikeDisp(
            StringIdKey nodeKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NavigationNodeItem> lookup = childForNodeNameLike(nodeKey, pattern, pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispNavigationNodeItem> childForNodeChildForRootDisp(StringIdKey nodeKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<NavigationNodeItem> lookup = childForNodeChildForRoot(nodeKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispNavigationNodeItem> childForParentDisp(LongIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<NavigationNodeItem> lookup = childForParent(parentKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispNavigationNodeItem> pathFromRootDisp(LongIdKey key) throws ServiceException {
        PagedData<NavigationNodeItem> pathFromRoot = pathFromRoot(key);
        return toDispPagedData(pathFromRoot);
    }

    private DispNavigationNodeItem toDisp(NavigationNodeItem navigationNodeItem) throws ServiceException {
        NavigationNodeItem parentNavigationNodeItem = null;
        if (Objects.nonNull(navigationNodeItem.getParentKey())) {
            parentNavigationNodeItem = navigationNodeItemMaintainService.getIfExists(navigationNodeItem.getParentKey());
        }
        boolean hasNoChild = navigationNodeItemMaintainService.lookup(
                NavigationNodeItemMaintainService.CHILD_FOR_PARENT,
                new Object[]{navigationNodeItem.getKey()},
                new PagingInfo(0, 1)
        ).getCount() <= 0;
        return DispNavigationNodeItem.of(navigationNodeItem, parentNavigationNodeItem, hasNoChild);
    }

    private PagedData<DispNavigationNodeItem> toDispPagedData(PagedData<NavigationNodeItem> lookup)
            throws ServiceException {
        List<DispNavigationNodeItem> dispNavigationNodeItems = new ArrayList<>();
        for (NavigationNodeItem navigationNodeItem : lookup.getData()) {
            dispNavigationNodeItems.add(toDisp(navigationNodeItem));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispNavigationNodeItems
        );
    }
}
