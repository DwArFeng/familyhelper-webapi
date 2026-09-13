package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.WriterSupportResponseService;
import com.dwarfeng.fileio.stack.bean.entity.WriterSupport;
import com.dwarfeng.fileio.stack.service.WriterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WriterSupportResponseServiceImpl implements WriterSupportResponseService {

    private final WriterSupportMaintainService writerSupportMaintainService;

    public WriterSupportResponseServiceImpl(
            @Qualifier("fileioWriterSupportMaintainService")
            WriterSupportMaintainService writerSupportMaintainService
    ) {
        this.writerSupportMaintainService = writerSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return writerSupportMaintainService.exists(key);
    }

    @Override
    public WriterSupport get(StringIdKey key) throws ServiceException {
        return writerSupportMaintainService.get(key);
    }

    @Override
    public PagedData<WriterSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return writerSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<WriterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return writerSupportMaintainService.lookup(
                WriterSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
