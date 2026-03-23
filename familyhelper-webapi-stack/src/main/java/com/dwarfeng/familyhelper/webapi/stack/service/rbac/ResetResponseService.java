package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 重置响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ResetResponseService extends Service {

    /**
     * 重置过滤功能。
     *
     * @throws ServiceException 服务异常。
     */
    void resetFilter() throws ServiceException;

    /**
     * 重置分析结果。
     *
     * @throws ServiceException 服务异常。
     */
    void resetAnalysis() throws ServiceException;
}
