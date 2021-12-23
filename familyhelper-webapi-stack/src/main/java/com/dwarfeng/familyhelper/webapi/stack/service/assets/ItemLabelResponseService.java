package com.dwarfeng.familyhelper.webapi.stack.service.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemLabel;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 项目标签响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface ItemLabelResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ItemLabel get(StringIdKey key) throws ServiceException;

    StringIdKey insert(ItemLabel itemLabel) throws ServiceException;

    void update(ItemLabel itemLabel) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<ItemLabel> all(PagingInfo pagingInfo) throws ServiceException;
}
