package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ResetResponseService;
import com.dwarfeng.fileio.impl.handler.resetter.DubboResetter.DubboResetService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("fileioResetResponseServiceImpl")
public class ResetResponseServiceImpl implements ResetResponseService {

    private final DubboResetService fileioDubboResetService;

    public ResetResponseServiceImpl(
            @Qualifier("fileioResetter.dubboResetService")
            DubboResetService fileioDubboResetService
    ) {
        this.fileioDubboResetService = fileioDubboResetService;
    }

    @Override
    public void resetExport() throws ServiceException {
        fileioDubboResetService.resetExport();
    }

    @Override
    public void resetImport() throws ServiceException {
        fileioDubboResetService.resetImport();
    }
}
