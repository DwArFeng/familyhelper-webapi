package com.dwarfeng.familyhelper.webapi.impl.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.service.fileio.ReaderSupportResponseService;
import com.dwarfeng.fileio.stack.bean.entity.ReaderSupport;
import com.dwarfeng.fileio.stack.service.ReaderSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReaderSupportResponseServiceImpl implements ReaderSupportResponseService {

    private final ReaderSupportMaintainService readerSupportMaintainService;

    public ReaderSupportResponseServiceImpl(
            @Qualifier("fileioReaderSupportMaintainService")
            ReaderSupportMaintainService readerSupportMaintainService
    ) {
        this.readerSupportMaintainService = readerSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return readerSupportMaintainService.exists(key);
    }

    @Override
    public ReaderSupport get(StringIdKey key) throws ServiceException {
        return readerSupportMaintainService.get(key);
    }

    @Override
    public PagedData<ReaderSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return readerSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ReaderSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return readerSupportMaintainService.lookup(
                ReaderSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
