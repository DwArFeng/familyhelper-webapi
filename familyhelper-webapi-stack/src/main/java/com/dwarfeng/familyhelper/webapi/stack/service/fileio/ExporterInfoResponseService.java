package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ExporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出器信息响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ExporterInfoResponseService extends Service {

    boolean exists(TaskSettingItemKey key) throws ServiceException;

    ExporterInfo get(TaskSettingItemKey key) throws ServiceException;

    TaskSettingItemKey insert(ExporterInfo exporterInfo) throws ServiceException;

    void update(ExporterInfo exporterInfo) throws ServiceException;

    void delete(TaskSettingItemKey key) throws ServiceException;

    PagedData<ExporterInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ExporterInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

}
