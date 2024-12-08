package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.Avatar;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.AvatarUploadInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.AvatarInfo;
import com.dwarfeng.familyhelper.clannad.stack.service.AvatarInfoMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.AvatarOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.AvatarResponseService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AvatarResponseServiceImpl implements AvatarResponseService {

    private final AvatarInfoMaintainService avatarInfoMaintainService;
    private final AvatarOperateService avatarOperateService;

    public AvatarResponseServiceImpl(
            @Qualifier("familyhelperClannadAvatarInfoMaintainService")
            AvatarInfoMaintainService avatarInfoMaintainService,
            @Qualifier("familyhelperClannadAvatarOperateService") AvatarOperateService avatarOperateService
    ) {
        this.avatarInfoMaintainService = avatarInfoMaintainService;
        this.avatarOperateService = avatarOperateService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return avatarInfoMaintainService.exists(key);
    }

    @Override
    public AvatarInfo get(StringIdKey key) throws ServiceException {
        return avatarInfoMaintainService.get(key);
    }

    @Override
    public Avatar downloadAvatar(StringIdKey userKey) throws ServiceException {
        return avatarOperateService.downloadAvatar(userKey);
    }

    @Override
    public void uploadAvatar(StringIdKey userKey, AvatarUploadInfo avatarUploadInfo) throws ServiceException {
        avatarOperateService.uploadAvatar(userKey, avatarUploadInfo);
    }
}
