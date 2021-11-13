package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Nickname;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.NicknameKey;
import com.dwarfeng.familyhelper.clannad.stack.service.NicknameMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.NicknameResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class NicknameResponseServiceImpl implements NicknameResponseService {

    private final NicknameMaintainService nicknameMaintainService;

    public NicknameResponseServiceImpl(
            @Qualifier("familyhelperClannadNicknameMaintainService") NicknameMaintainService nicknameMaintainService
    ) {
        this.nicknameMaintainService = nicknameMaintainService;
    }

    @Override
    public boolean exists(NicknameKey key) throws ServiceException {
        return nicknameMaintainService.exists(key);
    }

    @Override
    public Nickname get(NicknameKey key) throws ServiceException {
        return nicknameMaintainService.get(key);
    }

    @Override
    public NicknameKey insert(Nickname nickname) throws ServiceException {
        return nicknameMaintainService.insert(nickname);
    }

    @Override
    public void update(Nickname nickname) throws ServiceException {
        nicknameMaintainService.update(nickname);
    }

    @Override
    public void delete(NicknameKey key) throws ServiceException {
        nicknameMaintainService.delete(key);
    }

    @Override
    public PagedData<Nickname> childForSubjectUser(StringIdKey subjectUserKey, PagingInfo pagingInfo)
            throws ServiceException {
        return nicknameMaintainService.lookup(
                NicknameMaintainService.CHILD_FOR_SUBJECT_USER, new Object[]{subjectUserKey}, pagingInfo
        );
    }
}
