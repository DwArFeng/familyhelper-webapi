package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入任务设置响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ImportTaskSettingResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ImportTaskSetting get(LongIdKey key) throws ServiceException;

    LongIdKey insert(ImportTaskSetting importTaskSetting) throws ServiceException;

    void update(ImportTaskSetting importTaskSetting) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<ImportTaskSetting> all(PagingInfo pagingInfo) throws ServiceException;

}
