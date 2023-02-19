package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifyHistoryResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.NotifyInfoRecordResponseService;
import com.dwarfeng.notify.stack.bean.entity.NotifyInfoRecord;
import com.dwarfeng.notify.stack.bean.key.NotifyInfoRecordKey;
import com.dwarfeng.notify.stack.service.NotifyInfoRecordMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotifyInfoRecordResponseServiceImpl implements NotifyInfoRecordResponseService {

    private final NotifyInfoRecordMaintainService notifyInfoRecordMaintainService;
    private final NotifyHistoryResponseService notifyHistoryResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public NotifyInfoRecordResponseServiceImpl(
            @Qualifier("notifyNotifyInfoRecordMaintainService")
            NotifyInfoRecordMaintainService notifyInfoRecordMaintainService,
            NotifyHistoryResponseService notifyHistoryResponseService
    ) {
        this.notifyInfoRecordMaintainService = notifyInfoRecordMaintainService;
        this.notifyHistoryResponseService = notifyHistoryResponseService;
    }

    @Override
    public boolean exists(NotifyInfoRecordKey key) throws ServiceException {
        return notifyInfoRecordMaintainService.exists(key);
    }

    @Override
    public NotifyInfoRecord get(NotifyInfoRecordKey key) throws ServiceException {
        return notifyInfoRecordMaintainService.get(key);
    }

    @Override
    public void delete(NotifyInfoRecordKey key) throws ServiceException {
        notifyInfoRecordMaintainService.delete(key);
    }

    @Override
    public PagedData<NotifyInfoRecord> childForNotifyHistory(LongIdKey notifyHistoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        return notifyInfoRecordMaintainService.lookup(
                NotifyInfoRecordMaintainService.CHILD_FOR_NOTIFY_HISTORY_ORDERED,
                new Object[]{notifyHistoryKey},
                pagingInfo
        );
    }

    @Override
    public DispNotifyInfoRecord getDisp(NotifyInfoRecordKey key) throws ServiceException {
        NotifyInfoRecord notifyInfoRecord = get(key);
        return toDisp(notifyInfoRecord);
    }

    @Override
    public PagedData<DispNotifyInfoRecord> childForNotifyHistoryDisp(LongIdKey notifyHistoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<NotifyInfoRecord> lookup = childForNotifyHistory(notifyHistoryKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    private DispNotifyInfoRecord toDisp(NotifyInfoRecord notifyInfoRecord) throws ServiceException {
        LongIdKey notifyHistoryKey = new LongIdKey(notifyInfoRecord.getKey().getNotifyHistoryId());

        DispNotifyHistory dispNotifyHistory = notifyHistoryResponseService.getDisp(notifyHistoryKey);

        return DispNotifyInfoRecord.of(notifyInfoRecord, dispNotifyHistory);
    }

    private PagedData<DispNotifyInfoRecord> toDispPagedData(PagedData<NotifyInfoRecord> lookup) throws ServiceException {
        List<DispNotifyInfoRecord> dispNotifyInfoRecords = new ArrayList<>();
        for (NotifyInfoRecord notifyInfoRecord : lookup.getData()) {
            dispNotifyInfoRecords.add(toDisp(notifyInfoRecord));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispNotifyInfoRecords
        );
    }
}
