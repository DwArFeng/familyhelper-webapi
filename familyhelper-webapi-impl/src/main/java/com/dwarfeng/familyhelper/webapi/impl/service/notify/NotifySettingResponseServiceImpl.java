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
    private final com.dwarfeng.familyhelper.clannad.stack.service.NotifySettingMaintainService
            clannadNotifySettingMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public NotifySettingResponseServiceImpl(
            @Qualifier("notifyNotifySettingMaintainService")
            NotifySettingMaintainService notifySettingMaintainService,
            @Qualifier("familyhelperClannadNotifySettingMaintainService")
            com.dwarfeng.familyhelper.clannad.stack.service.NotifySettingMaintainService
                    clannadNotifySettingMaintainService
    ) {
        this.notifySettingMaintainService = notifySettingMaintainService;
        this.clannadNotifySettingMaintainService = clannadNotifySettingMaintainService;
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
        LongIdKey notifySettingKey = notifySettingMaintainService.insert(notifySetting);
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting clannadNotifySetting
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting(
                notifySettingKey, "通过 notify 模块插入/更新自动生成"
        );
        clannadNotifySettingMaintainService.insertOrUpdate(clannadNotifySetting);
        return notifySettingKey;
    }

    @Override
    public void update(NotifySetting notifySetting) throws ServiceException {
        notifySettingMaintainService.update(notifySetting);
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting clannadNotifySetting
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting(
                notifySetting.getKey(), "通过 notify 模块插入/更新自动生成"
        );
        clannadNotifySettingMaintainService.insertOrUpdate(clannadNotifySetting);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        notifySettingMaintainService.delete(key);
        clannadNotifySettingMaintainService.deleteIfExists(key);
    }

    @Override
    public PagedData<NotifySetting> all(PagingInfo pagingInfo) throws ServiceException {
        return notifySettingMaintainService.lookup(pagingInfo);
    }
}
