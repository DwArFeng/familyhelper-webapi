package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.logicengine.stack.bean.entity.PerformerSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 执行器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface PerformerSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    PerformerSupport get(StringIdKey key) throws ServiceException;

    PagedData<PerformerSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PerformerSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PerformerSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
