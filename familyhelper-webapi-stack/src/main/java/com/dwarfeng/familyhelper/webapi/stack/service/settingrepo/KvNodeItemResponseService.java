package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.KvNodeItem;
import com.dwarfeng.settingrepo.stack.bean.key.KvNodeItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 键值对节点条目响应服务。
 *
 * @author DwArFeng
 * @since 2.0.1
 */
public interface KvNodeItemResponseService extends Service {

    boolean exists(KvNodeItemKey key) throws ServiceException;

    KvNodeItem get(KvNodeItemKey key) throws ServiceException;

    PagedData<KvNodeItem> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<KvNodeItem> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException;
}
