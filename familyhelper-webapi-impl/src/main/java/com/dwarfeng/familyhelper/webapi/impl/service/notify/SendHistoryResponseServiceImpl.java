package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSendHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.SendHistoryResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.bean.entity.SendHistory;
import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.notify.stack.service.NotifySettingMaintainService;
import com.dwarfeng.notify.stack.service.SendHistoryMaintainService;
import com.dwarfeng.notify.stack.service.TopicMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SendHistoryResponseServiceImpl implements SendHistoryResponseService {

    private final SendHistoryMaintainService sendHistoryMaintainService;
    private final NotifySettingMaintainService notifySettingMaintainService;
    private final TopicMaintainService topicMaintainService;

    private final AccountResponseService accountResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SendHistoryResponseServiceImpl(
            @Qualifier("notifySendHistoryMaintainService") SendHistoryMaintainService sendHistoryMaintainService,
            @Qualifier("notifyNotifySettingMaintainService") NotifySettingMaintainService notifySettingMaintainService,
            @Qualifier("notifyTopicMaintainService") TopicMaintainService topicMaintainService,
            AccountResponseService accountResponseService
    ) {
        this.sendHistoryMaintainService = sendHistoryMaintainService;
        this.notifySettingMaintainService = notifySettingMaintainService;
        this.topicMaintainService = topicMaintainService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return sendHistoryMaintainService.exists(key);
    }

    @Override
    public SendHistory get(LongIdKey key) throws ServiceException {
        return sendHistoryMaintainService.get(key);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        sendHistoryMaintainService.delete(key);
    }

    @Override
    public PagedData<SendHistory> all(PagingInfo pagingInfo) throws ServiceException {
        return sendHistoryMaintainService.lookup(
                SendHistoryMaintainService.HAPPENED_DATE_DESC, new Object[0], pagingInfo
        );
    }

    @Override
    public DispSendHistory getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        SendHistory sendHistory = get(key);
        return toDisp(sendHistory, inspectAccountKey);
    }

    @Override
    public PagedData<DispSendHistory> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        PagedData<SendHistory> lookup = all(pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    private DispSendHistory toDisp(SendHistory sendHistory, StringIdKey inspectAccountKey) throws ServiceException {
        NotifySetting notifySetting = null;
        if (Objects.nonNull(sendHistory.getNotifySettingKey())) {
            notifySetting = notifySettingMaintainService.getIfExists(sendHistory.getNotifySettingKey());
        }
        Topic topic = null;
        if (Objects.nonNull(sendHistory.getTopicKey())) {
            topic = topicMaintainService.getIfExists(sendHistory.getTopicKey());
        }
        DispAccount account = null;
        if (Objects.nonNull(sendHistory.getUserKey())) {
            account = accountResponseService.getDisp(sendHistory.getUserKey(), inspectAccountKey);
        }
        return DispSendHistory.of(sendHistory, notifySetting, topic, account);
    }

    private PagedData<DispSendHistory> toDispPagedData(PagedData<SendHistory> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispSendHistory> dispSendHistories = new ArrayList<>();
        for (SendHistory sendHistory : lookup.getData()) {
            dispSendHistories.add(toDisp(sendHistory, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispSendHistories
        );
    }
}
