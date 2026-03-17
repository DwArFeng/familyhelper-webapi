package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicNavigationNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicNavigationNodeSizeInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.NavigationNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导航节点响应服务。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public interface NavigationNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    NavigationNode get(StringIdKey key) throws ServiceException;

    PagedData<NavigationNode> all(PagingInfo pagingInfo) throws ServiceException;

    NavigationNodeSizeResult size(NavigationNodeSizeInfo info) throws ServiceException;

    NavigationNodeInspectResult inspect(NavigationNodeInspectInfo info) throws ServiceException;

    void updateNode(NavigationNodeUpdateInfo info) throws ServiceException;

    NavigationNodeItemInsertResult insertItem(NavigationNodeItemInsertInfo info) throws ServiceException;

    void updateItem(NavigationNodeItemUpdateInfo info) throws ServiceException;

    void removeItem(NavigationNodeItemRemoveInfo info) throws ServiceException;

    void formatIndex(NavigationNodeFormatIndexInfo info) throws ServiceException;

    /**
     * 公共导航节点的大小。
     *
     * @param info 公共导航节点大小信息。
     * @return 导航节点大小结果。
     * @throws ServiceException 服务异常。
     */
    NavigationNodeSizeResult sizeForPublic(PublicNavigationNodeSizeInfo info) throws ServiceException;

    /**
     * 查看公共导航列表节点。
     *
     * @param info 公共导航列表节点查看信息。
     * @return 公共导航列表节点查看结果。
     * @throws ServiceException 服务异常。
     */
    NavigationNodeInspectResult inspectForPublic(PublicNavigationNodeInspectInfo info) throws ServiceException;
}
