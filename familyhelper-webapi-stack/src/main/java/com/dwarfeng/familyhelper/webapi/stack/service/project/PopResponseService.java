package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.Pop;
import com.dwarfeng.familyhelper.project.stack.bean.key.PopKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispPop;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 工程权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public interface PopResponseService extends Service {

    boolean exists(PopKey key) throws ServiceException;

    Pop get(PopKey key) throws ServiceException;

    DispPop getDisp(PopKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Pop> childForProject(LongIdKey projectKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPop> childForProjectDisp(
            LongIdKey projectKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
