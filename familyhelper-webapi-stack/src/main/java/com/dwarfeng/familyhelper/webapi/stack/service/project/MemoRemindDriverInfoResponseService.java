package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.MemoRemindDriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 备忘录提醒驱动器信息响应服务。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public interface MemoRemindDriverInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    MemoRemindDriverInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(MemoRemindDriverInfo memoRemindDriverInfo) throws ServiceException;

    void update(MemoRemindDriverInfo memoRemindDriverInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<MemoRemindDriverInfo> childForMemo(LongIdKey memoKey, PagingInfo pagingInfo) throws ServiceException;
}
