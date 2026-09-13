package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 重置响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ResetResponseService extends Service {

    void resetExport() throws ServiceException;

    void resetImport() throws ServiceException;

}
