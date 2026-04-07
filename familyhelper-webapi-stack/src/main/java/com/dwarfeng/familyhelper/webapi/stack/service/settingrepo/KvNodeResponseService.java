package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicKvNodeCountInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicKvNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicKvNodeItemInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.KvNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 键值对节点响应服务。
 *
 * @author DwArFeng
 * @since 2.0.1
 */
public interface KvNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    KvNode get(StringIdKey key) throws ServiceException;

    PagedData<KvNode> all(PagingInfo pagingInfo) throws ServiceException;

    KvNodeCountResult count(KvNodeCountInfo info) throws ServiceException;

    KvNodeInspectResult inspect(KvNodeInspectInfo info) throws ServiceException;

    KvNodeItemInspectResult inspectItem(KvNodeItemInspectInfo info) throws ServiceException;

    void putItem(KvNodeItemPutInfo info) throws ServiceException;

    void removeItem(KvNodeItemRemoveInfo info) throws ServiceException;

    void clear(KvNodeClearInfo info) throws ServiceException;

    /**
     * 查询公共键值对节点数量。
     *
     * @param info 公共键值对节点数量信息。
     * @return 键值对节点数量结果。
     * @throws ServiceException 服务异常。
     */
    KvNodeCountResult countForPublic(PublicKvNodeCountInfo info) throws ServiceException;

    /**
     * 查看公共键值对节点。
     *
     * @param info 公共键值对节点查看信息。
     * @return 键值对节点查看结果。
     * @throws ServiceException 服务异常。
     */
    KvNodeInspectResult inspectForPublic(PublicKvNodeInspectInfo info) throws ServiceException;

    /**
     * 查看公共键值对节点中的指定条目。
     *
     * @param info 公共键值对节点条目查看信息。
     * @return 键值对节点条目查看结果。
     * @throws ServiceException 服务异常。
     */
    KvNodeItemInspectResult inspectItemForPublic(PublicKvNodeItemInspectInfo info) throws ServiceException;
}
