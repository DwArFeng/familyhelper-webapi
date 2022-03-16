package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.NotificationCreateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Notification;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 通知响应服务。
 *
 * @author DwArFeng
 * @since 1.0.4
 */
public interface NotificationResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Notification get(LongIdKey key) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<Notification> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Notification> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Notification> childForUserUnread(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createNotification(NotificationCreateInfo notificationCreateInfo) throws ServiceException;

    void readNotification(List<LongIdKey> notificationKeys) throws ServiceException;
}
