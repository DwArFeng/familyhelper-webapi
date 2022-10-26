package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSendHistory;
import com.dwarfeng.notify.stack.bean.entity.SendHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 发送历史响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface SendHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    SendHistory get(LongIdKey key) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<SendHistory> all(PagingInfo pagingInfo) throws ServiceException;

    DispSendHistory getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispSendHistory> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey) throws ServiceException;
}
