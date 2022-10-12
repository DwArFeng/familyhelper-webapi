package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.plugin.notify.bean.entity.SendExecutorSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 发送执行器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface SendExecutorSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    SendExecutorSupport get(StringIdKey key) throws ServiceException;

    PagedData<SendExecutorSupport> all(PagingInfo pagingInfo) throws ServiceException;
}
