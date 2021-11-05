package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispProfile;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 银行卡响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ProfileResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Profile get(StringIdKey key) throws ServiceException;

    DispProfile getDisp(StringIdKey key) throws ServiceException;

    void updateProfile(Profile profile) throws ServiceException;
}
