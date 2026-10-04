package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 重置响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface ResetResponseService extends Service {

    void resetSupervise() throws ServiceException;

    void resetJob() throws ServiceException;
}
