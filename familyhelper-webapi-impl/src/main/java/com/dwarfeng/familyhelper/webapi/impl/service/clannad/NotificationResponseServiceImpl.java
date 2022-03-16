package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.NotificationCreateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Notification;
import com.dwarfeng.familyhelper.clannad.stack.service.NotificationMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.NotificationOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.NotificationResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class NotificationResponseServiceImpl implements NotificationResponseService {

    private final NotificationMaintainService notificationMaintainService;
    private final NotificationOperateService notificationOperateService;

    public NotificationResponseServiceImpl(
            @Qualifier("familyhelperClannadNotificationMaintainService")
                    NotificationMaintainService notificationMaintainService,
            @Qualifier("familyhelperClannadNotificationOperateService")
                    NotificationOperateService notificationOperateService
    ) {
        this.notificationMaintainService = notificationMaintainService;
        this.notificationOperateService = notificationOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return notificationMaintainService.exists(key);
    }

    @Override
    public Notification get(LongIdKey key) throws ServiceException {
        return notificationMaintainService.get(key);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        notificationMaintainService.delete(key);
    }

    @Override
    public PagedData<Notification> all(PagingInfo pagingInfo) throws ServiceException {
        return notificationMaintainService.lookup();
    }

    @Override
    public PagedData<Notification> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException {
        return notificationMaintainService.lookup(
                NotificationMaintainService.CHILD_FOR_USER, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public PagedData<Notification> childForUserUnread(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException {
        return notificationMaintainService.lookup(
                NotificationMaintainService.CHILD_FOR_USER_UNREAD, new Object[]{userKey}, pagingInfo
        );
    }

    @Override
    public LongIdKey createNotification(NotificationCreateInfo notificationCreateInfo) throws ServiceException {
        return notificationOperateService.createNotification(notificationCreateInfo);
    }

    @Override
    public void readNotification(List<LongIdKey> notificationKeys) throws ServiceException {
        notificationOperateService.readNotification(notificationKeys);
    }
}
