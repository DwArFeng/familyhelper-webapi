package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.NotifySetting;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 通知设置响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface NotifySettingResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    NotifySetting get(LongIdKey key) throws ServiceException;

    LongIdKey insert(NotifySetting notifySetting) throws ServiceException;

    void update(NotifySetting notifySetting) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<NotifySetting> all(PagingInfo pagingInfo) throws ServiceException;
}
