package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.familyhelper.webapi.stack.service.system.TimeResponseService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimeResponseServiceImpl implements TimeResponseService {

    @Override
    public Date currentDate() {
        return new Date();
    }
}
