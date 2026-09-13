package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出元数据响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ExportMetadataResponseService extends Service {

    boolean exists(TaskItemKey key) throws ServiceException;

    ExportMetadata get(TaskItemKey key) throws ServiceException;

    TaskItemKey insert(ExportMetadata exportMetadata) throws ServiceException;

    void update(ExportMetadata exportMetadata) throws ServiceException;

    void delete(TaskItemKey key) throws ServiceException;

    PagedData<ExportMetadata> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ExportMetadata> childForTask(LongIdKey taskKey, PagingInfo pagingInfo)
            throws ServiceException;

}
