package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 重置响应服务。
 *
 * @author DwArFeng
 * @since 1.0.8
 */
public interface ResetResponseService extends Service {

    void resetFormat() throws ServiceException;
}
