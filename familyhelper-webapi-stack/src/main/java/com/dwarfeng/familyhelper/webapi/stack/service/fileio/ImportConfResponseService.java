package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入配置响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ImportConfResponseService extends Service {

    boolean exists(TaskSettingItemKey key) throws ServiceException;

    ImportConf get(TaskSettingItemKey key) throws ServiceException;

    TaskSettingItemKey insert(ImportConf importConf) throws ServiceException;

    void update(ImportConf importConf) throws ServiceException;

    void delete(TaskSettingItemKey key) throws ServiceException;

    PagedData<ImportConf> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ImportConf> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

}
