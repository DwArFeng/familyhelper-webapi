package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifySettingResponseService;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.service.NotifySettingMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotifySettingResponseServiceImpl implements NotifySettingResponseService {

    private final NotifySettingMaintainService notifySettingMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public NotifySettingResponseServiceImpl(
            @Qualifier("notifyNotifySettingMaintainService")
            NotifySettingMaintainService notifySettingMaintainService
    ) {
        this.notifySettingMaintainService = notifySettingMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return notifySettingMaintainService.exists(key);
    }

    @Override
    public NotifySetting get(LongIdKey key) throws ServiceException {
        return notifySettingMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(NotifySetting notifySetting) throws ServiceException {
        return notifySettingMaintainService.insert(notifySetting);
    }

    @Override
    public void update(NotifySetting notifySetting) throws ServiceException {
        notifySettingMaintainService.update(notifySetting);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        notifySettingMaintainService.delete(key);
    }

    @Override
    public PagedData<NotifySetting> all(PagingInfo pagingInfo) throws ServiceException {
        return notifySettingMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<NotifySetting> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return notifySettingMaintainService.lookup(
                NotifySettingMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
