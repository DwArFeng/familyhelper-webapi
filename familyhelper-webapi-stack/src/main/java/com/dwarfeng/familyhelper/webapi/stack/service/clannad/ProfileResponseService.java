package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.ProfileUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispProfile;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.Collection;

/**
 * 银行卡响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProfileResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Profile get(StringIdKey key) throws ServiceException;

    PagedData<Profile> childForPermittedAccount(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    DispProfile getDisp(StringIdKey key) throws ServiceException;

    PagedData<DispProfile> childForPermittedAccountDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    void updateProfile(StringIdKey userKey, ProfileUpdateInfo profileUpdateInfo) throws ServiceException;

    void addGuestPermission(StringIdKey ownerUserKey, StringIdKey guestUserKey) throws ServiceException;

    void removeGuestPermission(StringIdKey ownerUserKey, StringIdKey guestUserKey) throws ServiceException;

    void resetGuestPermission(StringIdKey ownerUserKey, Collection<StringIdKey> guestUserKeys) throws ServiceException;
}
