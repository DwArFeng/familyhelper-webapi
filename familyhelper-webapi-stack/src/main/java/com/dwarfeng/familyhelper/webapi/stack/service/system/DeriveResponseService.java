package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.DeriveResponse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 派生响应服务。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public interface DeriveResponseService extends Service {

    /**
     * 动态派生。
     *
     * @param dynamicDeriveInfo 动态派生信息。
     * @return 派生状态响应。
     * @throws ServiceException 服务异常。
     */
    DeriveResponse dynamicDerive(DynamicDeriveInfo dynamicDeriveInfo) throws ServiceException;

    /**
     * 静态派生。
     *
     * @param staticDeriveInfo 静态派生信息。
     * @return 派生状态响应。
     * @throws ServiceException 服务异常。
     */
    DeriveResponse staticDerive(StaticDeriveInfo staticDeriveInfo) throws ServiceException;
}
