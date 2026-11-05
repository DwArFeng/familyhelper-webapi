package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeMek;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMekKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 国际化节点 Mek 响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface IahnNodeMekResponseService extends Service {

    boolean exists(IahnNodeMekKey key) throws ServiceException;

    IahnNodeMek get(IahnNodeMekKey key) throws ServiceException;

    PagedData<IahnNodeMek> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<IahnNodeMek> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<IahnNodeMek> childForNodeOrdered(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException;
}
