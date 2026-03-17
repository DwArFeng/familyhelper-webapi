package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispNavigationNodeItem;
import com.dwarfeng.settingrepo.stack.bean.entity.NavigationNodeItem;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导航节点条目响应服务。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public interface NavigationNodeItemResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    NavigationNodeItem get(LongIdKey key) throws ServiceException;

    PagedData<NavigationNodeItem> childForNodeNameLike(StringIdKey nodeKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<NavigationNodeItem> childForNodeChildForRoot(StringIdKey nodeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<NavigationNodeItem> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<NavigationNodeItem> pathFromRoot(LongIdKey key) throws ServiceException;

    DispNavigationNodeItem getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispNavigationNodeItem> childForNodeNameLikeDisp(
            StringIdKey nodeKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispNavigationNodeItem> childForNodeChildForRootDisp(StringIdKey nodeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispNavigationNodeItem> childForParentDisp(LongIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispNavigationNodeItem> pathFromRootDisp(LongIdKey key) throws ServiceException;
}
