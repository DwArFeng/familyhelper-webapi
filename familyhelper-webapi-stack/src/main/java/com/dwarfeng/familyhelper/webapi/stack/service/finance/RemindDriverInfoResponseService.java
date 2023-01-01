package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.RemindDriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 提醒驱动器信息响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface RemindDriverInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    RemindDriverInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(RemindDriverInfo remindDriverInfo) throws ServiceException;

    void update(RemindDriverInfo remindDriverInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<RemindDriverInfo> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException;
}
