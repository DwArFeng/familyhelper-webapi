package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeLocale;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeLocaleKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 国际化节点地区响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface IahnNodeLocaleResponseService extends Service {

    boolean exists(IahnNodeLocaleKey key) throws ServiceException;

    IahnNodeLocale get(IahnNodeLocaleKey key) throws ServiceException;

    PagedData<IahnNodeLocale> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<IahnNodeLocale> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<IahnNodeLocale> childForNodeOrdered(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException;
}
