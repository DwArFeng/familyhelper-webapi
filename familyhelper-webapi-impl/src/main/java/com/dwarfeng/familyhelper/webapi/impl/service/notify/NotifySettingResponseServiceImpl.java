package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.NotifySetting;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifySettingResponseService;
import com.dwarfeng.notify.stack.service.NotifySettingMaintainService;
import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotifySettingResponseServiceImpl implements NotifySettingResponseService {

    private final com.dwarfeng.notify.stack.service.NotifySettingMaintainService
            notifyNotifySettingMaintainService;
    private final com.dwarfeng.familyhelper.clannad.stack.service.NotifySettingMaintainService
            clannadNotifySettingMaintainService;

    private final LongIdService longIdService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public NotifySettingResponseServiceImpl(
            @Qualifier("notifyNotifySettingMaintainService")
            NotifySettingMaintainService
                    notifyNotifySettingMaintainService,
            @Qualifier("familyhelperClannadNotifySettingMaintainService")
            com.dwarfeng.familyhelper.clannad.stack.service.NotifySettingMaintainService
                    clannadNotifySettingMaintainService,
            @Qualifier("longIdService") LongIdService longIdService
    ) {
        this.notifyNotifySettingMaintainService = notifyNotifySettingMaintainService;
        this.clannadNotifySettingMaintainService = clannadNotifySettingMaintainService;
        this.longIdService = longIdService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return notifyNotifySettingMaintainService.exists(key);
    }

    @Override
    public NotifySetting get(LongIdKey key) throws ServiceException {
        com.dwarfeng.notify.stack.bean.entity.NotifySetting notifyNotifySetting
                = notifyNotifySettingMaintainService.get(key);
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting clannadNotifySetting
                = clannadNotifySettingMaintainService.get(key);
        return new NotifySetting(
                key, notifyNotifySetting.getLabel(), notifyNotifySetting.isEnabled(),
                clannadNotifySetting.getRequiredPermission(), notifyNotifySetting.getRemark()
        );
    }

    @Override
    public LongIdKey insert(NotifySetting notifySetting) throws ServiceException {
        LongIdKey key = notifySetting.getKey();
        if (Objects.isNull(key)) {
            key = longIdService.nextLongIdKey();
        }

        com.dwarfeng.notify.stack.bean.entity.NotifySetting notifyNotifySetting
                = new com.dwarfeng.notify.stack.bean.entity.NotifySetting(
                key, notifySetting.getLabel(), notifySetting.getRemark(), notifySetting.isEnabled()
        );

        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting clannadNotifySetting
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting(
                key, "通过 notifySetting 插入/更新自动生成", notifySetting.getRequiredPermission()
        );

        notifyNotifySettingMaintainService.insert(notifyNotifySetting);
        clannadNotifySettingMaintainService.insertOrUpdate(clannadNotifySetting);

        return key;
    }

    @Override
    public void update(NotifySetting notifySetting) throws ServiceException {
        com.dwarfeng.notify.stack.bean.entity.NotifySetting notifyNotifySetting
                = new com.dwarfeng.notify.stack.bean.entity.NotifySetting(
                notifySetting.getKey(), notifySetting.getLabel(), notifySetting.getRemark(), notifySetting.isEnabled()
        );

        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting clannadNotifySetting
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting(
                notifySetting.getKey(), "通过 notifySetting 插入/更新自动生成", notifySetting.getRequiredPermission()
        );

        notifyNotifySettingMaintainService.update(notifyNotifySetting);
        clannadNotifySettingMaintainService.insertOrUpdate(clannadNotifySetting);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        notifyNotifySettingMaintainService.delete(key);
        clannadNotifySettingMaintainService.deleteIfExists(key);
    }

    @Override
    public PagedData<NotifySetting> all(PagingInfo pagingInfo) throws ServiceException {
        PagedData<com.dwarfeng.notify.stack.bean.entity.NotifySetting> notifyLookup
                = notifyNotifySettingMaintainService.lookup(pagingInfo);
        return toVoPagedData(notifyLookup);
    }

    private NotifySetting toVo(
            com.dwarfeng.notify.stack.bean.entity.NotifySetting notifyNotifySetting
    ) throws ServiceException {
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifySetting clannadNotifySetting
                = clannadNotifySettingMaintainService.get(notifyNotifySetting.getKey());
        return new NotifySetting(
                notifyNotifySetting.getKey(), notifyNotifySetting.getLabel(), notifyNotifySetting.isEnabled(),
                clannadNotifySetting.getRequiredPermission(), notifyNotifySetting.getRemark()
        );
    }

    private PagedData<NotifySetting> toVoPagedData(
            PagedData<com.dwarfeng.notify.stack.bean.entity.NotifySetting> notifyLookup
    ) throws ServiceException {
        List<NotifySetting> notifySettings = new ArrayList<>();
        for (com.dwarfeng.notify.stack.bean.entity.NotifySetting notifyNotifySetting : notifyLookup.getData()) {
            notifySettings.add(toVo(notifyNotifySetting));
        }
        return new PagedData<>(
                notifyLookup.getCurrentPage(), notifyLookup.getTotalPages(), notifyLookup.getRows(),
                notifyLookup.getCount(), notifySettings
        );
    }
}
