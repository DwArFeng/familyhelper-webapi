package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ReaderInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 读取器信息响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ReaderInfoResponseService extends Service {

    boolean exists(TaskSettingItemKey key) throws ServiceException;

    ReaderInfo get(TaskSettingItemKey key) throws ServiceException;

    TaskSettingItemKey insert(ReaderInfo readerInfo) throws ServiceException;

    void update(ReaderInfo readerInfo) throws ServiceException;

    void delete(TaskSettingItemKey key) throws ServiceException;

    PagedData<ReaderInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ReaderInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

}
