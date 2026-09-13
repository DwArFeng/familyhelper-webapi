package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ImporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入器信息响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ImporterInfoResponseService extends Service {

    boolean exists(TaskSettingItemKey key) throws ServiceException;

    ImporterInfo get(TaskSettingItemKey key) throws ServiceException;

    TaskSettingItemKey insert(ImporterInfo importerInfo) throws ServiceException;

    void update(ImporterInfo importerInfo) throws ServiceException;

    void delete(TaskSettingItemKey key) throws ServiceException;

    PagedData<ImporterInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ImporterInfo> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

}
