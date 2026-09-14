package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计作业响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionJobResponseService extends Service {

    /**
     * 执行自动审计作业。
     *
     * @param info 自动审计作业执行信息。
     * @throws ServiceException 服务异常。
     */
    void execute(InspectionJobExecuteInfo info) throws ServiceException;
}
