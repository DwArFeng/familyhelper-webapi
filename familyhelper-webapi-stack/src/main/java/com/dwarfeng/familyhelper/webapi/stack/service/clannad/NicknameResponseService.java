package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Nickname;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.NicknameKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 昵称响应服务。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public interface NicknameResponseService extends Service {

    boolean exists(NicknameKey key) throws ServiceException;

    Nickname get(NicknameKey key) throws ServiceException;

    NicknameKey insert(Nickname nickname) throws ServiceException;

    void update(Nickname nickname) throws ServiceException;

    void delete(NicknameKey key) throws ServiceException;

    PagedData<Nickname> childForSubjectUser(StringIdKey subjectUserKey, PagingInfo pagingInfo) throws ServiceException;
}
