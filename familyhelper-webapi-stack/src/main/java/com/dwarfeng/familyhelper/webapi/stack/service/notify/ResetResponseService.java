package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 重置响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface ResetResponseService extends Service {

    void resetRoute() throws ServiceException;

    void resetDispatch() throws ServiceException;

    void resetSend() throws ServiceException;
}
