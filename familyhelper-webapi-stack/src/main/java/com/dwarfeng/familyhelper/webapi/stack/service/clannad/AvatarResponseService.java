package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.Avatar;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.AvatarUploadInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.AvatarInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 头像响应服务。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public interface AvatarResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    AvatarInfo get(StringIdKey key) throws ServiceException;

    Avatar downloadAvatar(StringIdKey userKey) throws ServiceException;

    void uploadAvatar(StringIdKey userKey, AvatarUploadInfo avatarUploadInfo) throws ServiceException;
}
