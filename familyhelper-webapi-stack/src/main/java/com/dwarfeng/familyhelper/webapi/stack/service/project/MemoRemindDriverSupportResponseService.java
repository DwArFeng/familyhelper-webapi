package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.MemoRemindDriverSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 备忘录提醒驱动器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public interface MemoRemindDriverSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    MemoRemindDriverSupport get(StringIdKey key) throws ServiceException;

    PagedData<MemoRemindDriverSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<MemoRemindDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
