package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

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
     * 重置保护。
     *
     * @throws ServiceException 服务异常。
     */
    void resetProtect() throws ServiceException;
}
