package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageBody;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageBodyUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageBodyInfo;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageBodyInfoMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.MessageBodyOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.MessageBodyResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessageBodyResponseServiceImpl implements MessageBodyResponseService {

    private final MessageBodyInfoMaintainService messageBodyInfoMaintainService;
    private final MessageBodyOperateService messageBodyOperateService;

    public MessageBodyResponseServiceImpl(
            @Qualifier("familyhelperClannadMessageBodyInfoMaintainService")
            MessageBodyInfoMaintainService messageBodyInfoMaintainService,
            @Qualifier("familyhelperClannadMessageBodyOperateService")
            MessageBodyOperateService messageBodyOperateService
    ) {
        this.messageBodyInfoMaintainService = messageBodyInfoMaintainService;
        this.messageBodyOperateService = messageBodyOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return messageBodyInfoMaintainService.exists(key);
    }

    @Override
    public MessageBodyInfo get(LongIdKey key) throws ServiceException {
        return messageBodyInfoMaintainService.get(key);
    }

    @Override
    public PagedData<MessageBodyInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return messageBodyInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public MessageBody download(StringIdKey userKey, LongIdKey messageKey) throws ServiceException {
        return messageBodyOperateService.download(userKey, messageKey);
    }

    @Override
    public void update(StringIdKey userKey, MessageBodyUpdateInfo messageBodyUpdateInfo) throws ServiceException {
        messageBodyOperateService.update(userKey, messageBodyUpdateInfo);
    }
}
