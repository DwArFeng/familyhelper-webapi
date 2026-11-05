package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.WarmupDriverSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 预热驱动器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WarmupDriverSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    WarmupDriverSupport get(StringIdKey key) throws ServiceException;

    PagedData<WarmupDriverSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<WarmupDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<WarmupDriverSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
