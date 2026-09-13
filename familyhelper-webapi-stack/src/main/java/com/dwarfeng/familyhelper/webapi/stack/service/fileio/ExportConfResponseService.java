package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出配置响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ExportConfResponseService extends Service {

    boolean exists(TaskSettingItemKey key) throws ServiceException;

    ExportConf get(TaskSettingItemKey key) throws ServiceException;

    TaskSettingItemKey insert(ExportConf exportConf) throws ServiceException;

    void update(ExportConf exportConf) throws ServiceException;

    void delete(TaskSettingItemKey key) throws ServiceException;

    PagedData<ExportConf> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ExportConf> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

}
