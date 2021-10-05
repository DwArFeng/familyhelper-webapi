package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限表达式响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PexpResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Pexp get(LongIdKey key) throws ServiceException;

    LongIdKey insert(Pexp pexp) throws ServiceException;

    void update(Pexp pexp) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<Pexp> childForRole(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException;
}
