package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.fileio.stack.bean.entity.ImporterSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入器支持响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ImporterSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ImporterSupport get(StringIdKey key) throws ServiceException;

    PagedData<ImporterSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ImporterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
