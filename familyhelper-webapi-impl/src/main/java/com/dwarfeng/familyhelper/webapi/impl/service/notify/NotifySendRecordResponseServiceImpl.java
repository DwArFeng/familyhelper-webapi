package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifySendRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifyHistoryResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifySendRecordResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.notify.stack.bean.entity.NotifySendRecord;
import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.notify.stack.bean.key.NotifySendRecordKey;
import com.dwarfeng.notify.stack.service.NotifySendRecordMaintainService;
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

@Service
public class NotifySendRecordResponseServiceImpl implements NotifySendRecordResponseService {

    private final NotifySendRecordMaintainService notifySendRecordMaintainService;
    private final TopicMaintainService topicMaintainService;

    private final NotifyHistoryResponseService notifyHistoryResponseService;
    private final AccountResponseService accountResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public NotifySendRecordResponseServiceImpl(
            @Qualifier("notifyNotifySendRecordMaintainService")
            NotifySendRecordMaintainService notifySendRecordMaintainService,
            @Qualifier("notifyTopicMaintainService")
            TopicMaintainService topicMaintainService,
            NotifyHistoryResponseService notifyHistoryResponseService,
            AccountResponseService accountResponseService
    ) {
        this.notifySendRecordMaintainService = notifySendRecordMaintainService;
        this.topicMaintainService = topicMaintainService;
        this.notifyHistoryResponseService = notifyHistoryResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(NotifySendRecordKey key) throws ServiceException {
        return notifySendRecordMaintainService.exists(key);
    }

    @Override
    public NotifySendRecord get(NotifySendRecordKey key) throws ServiceException {
        return notifySendRecordMaintainService.get(key);
    }

    @Override
    public void delete(NotifySendRecordKey key) throws ServiceException {
        notifySendRecordMaintainService.delete(key);
    }

    @Override
    public PagedData<NotifySendRecord> childForNotifyHistory(LongIdKey notifyHistoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        return notifySendRecordMaintainService.lookup(
                NotifySendRecordMaintainService.CHILD_FOR_NOTIFY_HISTORY_ORDERED,
                new Object[]{notifyHistoryKey},
                pagingInfo
        );
    }

    @Override
    public DispNotifySendRecord getDisp(NotifySendRecordKey key, StringIdKey inspectAccountKey) throws ServiceException {
        NotifySendRecord notifySendRecord = get(key);
        return toDisp(notifySendRecord, inspectAccountKey);
    }

    @Override
    public PagedData<DispNotifySendRecord> childForNotifyHistoryDisp(
            LongIdKey notifyHistoryKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<NotifySendRecord> lookup = childForNotifyHistory(notifyHistoryKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    private DispNotifySendRecord toDisp(NotifySendRecord notifySendRecord, StringIdKey inspectAccountKey)
            throws ServiceException {
        LongIdKey notifyHistoryKey = new LongIdKey(notifySendRecord.getKey().getNotifyHistoryId());
        StringIdKey topicKey = new StringIdKey(notifySendRecord.getKey().getTopicId());
        StringIdKey accountKey = new StringIdKey(notifySendRecord.getKey().getUserId());

        DispNotifyHistory dispNotifyHistory = notifyHistoryResponseService.getDisp(notifyHistoryKey);
        Topic topic = topicMaintainService.getIfExists(topicKey);
        DispAccount dispAccount = accountResponseService.getDisp(accountKey, inspectAccountKey);

        return DispNotifySendRecord.of(notifySendRecord, dispNotifyHistory, topic, dispAccount);
    }

    private PagedData<DispNotifySendRecord> toDispPagedData(
            PagedData<NotifySendRecord> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispNotifySendRecord> dispNotifySendRecords = new ArrayList<>();
        for (NotifySendRecord notifySendRecord : lookup.getData()) {
            dispNotifySendRecords.add(toDisp(notifySendRecord, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispNotifySendRecords
        );
    }
}
