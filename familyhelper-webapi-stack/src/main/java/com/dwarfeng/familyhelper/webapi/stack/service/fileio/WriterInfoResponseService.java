package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.WriterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 写入器信息响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface WriterInfoResponseService extends Service {

    boolean exists(TaskSettingItemKey key) throws ServiceException;

    WriterInfo get(TaskSettingItemKey key) throws ServiceException;

    TaskSettingItemKey insert(WriterInfo writerInfo) throws ServiceException;

    void update(WriterInfo writerInfo) throws ServiceException;

    void delete(TaskSettingItemKey key) throws ServiceException;

    PagedData<WriterInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<WriterInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

}
