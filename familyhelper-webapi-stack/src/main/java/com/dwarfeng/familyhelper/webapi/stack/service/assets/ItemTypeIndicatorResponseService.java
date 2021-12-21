package com.dwarfeng.familyhelper.webapi.stack.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 项目类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface ItemTypeIndicatorResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ItemTypeIndicator get(StringIdKey key) throws ServiceException;

    StringIdKey insert(ItemTypeIndicator itemTypeIndicator) throws ServiceException;

    void update(ItemTypeIndicator itemTypeIndicator) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<ItemTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException;
}
