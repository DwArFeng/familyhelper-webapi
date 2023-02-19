package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyHistory;
import com.dwarfeng.notify.stack.bean.entity.NotifyHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 通知历史响应服务。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public interface NotifyHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    NotifyHistory get(LongIdKey key) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<NotifyHistory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<NotifyHistory> childForNotifySetting(LongIdKey notifySettingKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispNotifyHistory getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispNotifyHistory> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispNotifyHistory> childForNotifySettingDisp(LongIdKey notifySettingKey, PagingInfo pagingInfo)
            throws ServiceException;
}
