package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出任务设置响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ExportTaskSettingResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ExportTaskSetting get(LongIdKey key) throws ServiceException;

    LongIdKey insert(ExportTaskSetting exportTaskSetting) throws ServiceException;

    void update(ExportTaskSetting exportTaskSetting) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<ExportTaskSetting> all(PagingInfo pagingInfo) throws ServiceException;

}
