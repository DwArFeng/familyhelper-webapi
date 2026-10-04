package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.JobResponseService;
import com.dwarfeng.logicengine.stack.bean.dto.JobExecuteInfo;
import com.dwarfeng.logicengine.stack.service.JobService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JobResponseServiceImpl implements JobResponseService {

    private final JobService jobService;

    public JobResponseServiceImpl(
            @Qualifier("logicengineJobService") JobService jobService
    ) {
        this.jobService = jobService;
    }

    @Override
    public void execute(JobExecuteInfo info) throws ServiceException {
        jobService.execute(info);
    }
}
