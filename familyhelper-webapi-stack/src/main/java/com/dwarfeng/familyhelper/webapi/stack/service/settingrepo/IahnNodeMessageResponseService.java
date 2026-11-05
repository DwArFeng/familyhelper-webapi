package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeMessage;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeLocaleKey;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMekKey;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMessageKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 国际化节点消息响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface IahnNodeMessageResponseService extends Service {

    boolean exists(IahnNodeMessageKey key) throws ServiceException;

    IahnNodeMessage get(IahnNodeMessageKey key) throws ServiceException;

    PagedData<IahnNodeMessage> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<IahnNodeMessage> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<IahnNodeMessage> childForLocale(IahnNodeLocaleKey localeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<IahnNodeMessage> childForMek(IahnNodeMekKey mekKey, PagingInfo pagingInfo) throws ServiceException;
}
