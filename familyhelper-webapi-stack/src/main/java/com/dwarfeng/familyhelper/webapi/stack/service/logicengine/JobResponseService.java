package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.logicengine.stack.bean.dto.JobExecuteInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 作业响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface JobResponseService extends Service {

    /**
     * 执行作业。
     *
     * @param info 作业执行信息。
     * @throws ServiceException 服务异常。
     */
    void execute(JobExecuteInfo info) throws ServiceException;
}
