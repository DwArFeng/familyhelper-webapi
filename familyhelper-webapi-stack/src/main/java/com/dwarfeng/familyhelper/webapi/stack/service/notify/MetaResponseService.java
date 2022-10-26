package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispMeta;
import com.dwarfeng.notify.stack.bean.entity.Meta;
import com.dwarfeng.notify.stack.bean.entity.key.MetaKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 元数据响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface MetaResponseService extends Service {

    boolean exists(MetaKey key) throws ServiceException;

    Meta get(MetaKey key) throws ServiceException;

    MetaKey insert(Meta meta) throws ServiceException;

    void update(Meta meta) throws ServiceException;

    void delete(MetaKey key) throws ServiceException;

    PagedData<Meta> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Meta> childForNotifySettingTopicUser(
            LongIdKey notifySettingKey, StringIdKey topicKey, StringIdKey userKey, PagingInfo pagingInfo
    ) throws ServiceException;

    DispMeta getDisp(MetaKey key) throws ServiceException;

    PagedData<DispMeta> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispMeta> childForNotifySettingTopicUserDisp(
            LongIdKey notifySettingKey, StringIdKey topicKey, StringIdKey userKey, PagingInfo pagingInfo
    ) throws ServiceException;
}
