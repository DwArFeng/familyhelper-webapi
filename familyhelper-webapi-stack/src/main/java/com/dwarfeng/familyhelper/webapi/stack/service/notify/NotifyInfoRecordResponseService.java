package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyInfoRecord;
import com.dwarfeng.notify.stack.bean.entity.NotifyInfoRecord;
import com.dwarfeng.notify.stack.bean.key.NotifyInfoRecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 通知信息记录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public interface NotifyInfoRecordResponseService extends Service {

    boolean exists(NotifyInfoRecordKey key) throws ServiceException;

    NotifyInfoRecord get(NotifyInfoRecordKey key) throws ServiceException;

    void delete(NotifyInfoRecordKey key) throws ServiceException;

    PagedData<NotifyInfoRecord> childForNotifyHistory(LongIdKey notifyHistoryKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispNotifyInfoRecord getDisp(NotifyInfoRecordKey key) throws ServiceException;

    PagedData<DispNotifyInfoRecord> childForNotifyHistoryDisp(LongIdKey notifyHistoryKey, PagingInfo pagingInfo)
            throws ServiceException;
}
