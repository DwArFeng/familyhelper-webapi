package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.FormatterSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 格式化器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface FormatterSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    FormatterSupport get(StringIdKey key) throws ServiceException;

    PagedData<FormatterSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<FormatterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
