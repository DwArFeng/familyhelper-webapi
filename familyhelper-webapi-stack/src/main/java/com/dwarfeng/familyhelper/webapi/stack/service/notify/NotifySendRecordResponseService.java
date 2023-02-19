package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifySendRecord;
import com.dwarfeng.notify.stack.bean.entity.NotifySendRecord;
import com.dwarfeng.notify.stack.bean.key.NotifySendRecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 通知发送记录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public interface NotifySendRecordResponseService extends Service {

    boolean exists(NotifySendRecordKey key) throws ServiceException;

    NotifySendRecord get(NotifySendRecordKey key) throws ServiceException;

    void delete(NotifySendRecordKey key) throws ServiceException;

    PagedData<NotifySendRecord> childForNotifyHistory(LongIdKey notifyHistoryKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispNotifySendRecord getDisp(NotifySendRecordKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispNotifySendRecord> childForNotifyHistoryDisp(
            LongIdKey notifyHistoryKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
