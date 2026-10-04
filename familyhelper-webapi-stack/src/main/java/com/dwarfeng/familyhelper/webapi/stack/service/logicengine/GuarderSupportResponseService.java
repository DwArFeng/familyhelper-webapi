package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 守卫器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface GuarderSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    GuarderSupport get(StringIdKey key) throws ServiceException;

    PagedData<GuarderSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<GuarderSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<GuarderSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
