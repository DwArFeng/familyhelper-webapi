package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageBody;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.MessageBodyUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.MessageBodyInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 留言正文响应服务。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface MessageBodyResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    MessageBodyInfo get(LongIdKey key) throws ServiceException;

    PagedData<MessageBodyInfo> all(PagingInfo pagingInfo) throws ServiceException;

    MessageBody download(StringIdKey userKey, LongIdKey messageKey) throws ServiceException;

    void update(StringIdKey userKey, MessageBodyUpdateInfo messageBodyUpdateInfo) throws ServiceException;
}
