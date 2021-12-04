package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.Date;

/**
 * 时间响应服务。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public interface TimeResponseService extends Service {

    Date currentDate() throws ServiceException;
}
