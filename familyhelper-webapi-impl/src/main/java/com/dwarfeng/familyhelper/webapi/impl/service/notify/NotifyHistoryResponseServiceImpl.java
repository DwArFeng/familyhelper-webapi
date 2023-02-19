package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifyHistoryResponseService;
import com.dwarfeng.notify.stack.bean.entity.NotifyHistory;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.service.NotifyHistoryMaintainService;
import com.dwarfeng.notify.stack.service.NotifySettingMaintainService;
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
public class NotifyHistoryResponseServiceImpl implements NotifyHistoryResponseService {

    private final NotifyHistoryMaintainService notifyHistoryMaintainService;
    private final NotifySettingMaintainService notifySettingMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public NotifyHistoryResponseServiceImpl(
            @Qualifier("notifyNotifyHistoryMaintainService")
            NotifyHistoryMaintainService notifyHistoryMaintainService,
            @Qualifier("notifyNotifySettingMaintainService")
            NotifySettingMaintainService notifySettingMaintainService
    ) {
        this.notifyHistoryMaintainService = notifyHistoryMaintainService;
        this.notifySettingMaintainService = notifySettingMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return notifyHistoryMaintainService.exists(key);
    }

    @Override
    public NotifyHistory get(LongIdKey key) throws ServiceException {
        return notifyHistoryMaintainService.get(key);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        notifyHistoryMaintainService.delete(key);
    }

    @Override
    public PagedData<NotifyHistory> all(PagingInfo pagingInfo) throws ServiceException {
        return notifyHistoryMaintainService.lookup(
                NotifyHistoryMaintainService.HAPPENED_DATE_DESC, new Object[0], pagingInfo
        );
    }

    @Override
    public PagedData<NotifyHistory> childForNotifySetting(LongIdKey notifySettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        return notifyHistoryMaintainService.lookup(
                NotifyHistoryMaintainService.HAPPENED_DATE_DESC, new Object[]{notifySettingKey}, pagingInfo
        );
    }

    @Override
    public DispNotifyHistory getDisp(LongIdKey key) throws ServiceException {
        NotifyHistory notifyHistory = get(key);
        return toDisp(notifyHistory);
    }

    @Override
    public PagedData<DispNotifyHistory> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<NotifyHistory> lookup = all(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispNotifyHistory> childForNotifySettingDisp(LongIdKey notifySettingKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<NotifyHistory> lookup = childForNotifySetting(notifySettingKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    private DispNotifyHistory toDisp(NotifyHistory notifyHistory) throws ServiceException {
        LongIdKey notifySettingKey = notifyHistory.getNotifySettingKey();

        NotifySetting notifySetting = null;
        if (Objects.nonNull(notifySettingKey)) {
            notifySetting = notifySettingMaintainService.getIfExists(notifySettingKey);
        }

        return DispNotifyHistory.of(notifyHistory, notifySetting);
    }

    private PagedData<DispNotifyHistory> toDispPagedData(PagedData<NotifyHistory> lookup) throws ServiceException {
        List<DispNotifyHistory> dispNotifyHistories = new ArrayList<>();
        for (NotifyHistory notifyHistory : lookup.getData()) {
            dispNotifyHistories.add(toDisp(notifyHistory));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispNotifyHistories
        );
    }
}
