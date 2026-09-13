package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入元数据响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ImportMetadataResponseService extends Service {

    boolean exists(TaskItemKey key) throws ServiceException;

    ImportMetadata get(TaskItemKey key) throws ServiceException;

    TaskItemKey insert(ImportMetadata importMetadata) throws ServiceException;

    void update(ImportMetadata importMetadata) throws ServiceException;

    void delete(TaskItemKey key) throws ServiceException;

    PagedData<ImportMetadata> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ImportMetadata> childForTask(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException;

}
