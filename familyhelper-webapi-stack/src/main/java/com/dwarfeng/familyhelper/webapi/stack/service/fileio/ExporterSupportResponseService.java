package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ExporterSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出器支持响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ExporterSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ExporterSupport get(StringIdKey key) throws ServiceException;

    PagedData<ExporterSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ExporterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
