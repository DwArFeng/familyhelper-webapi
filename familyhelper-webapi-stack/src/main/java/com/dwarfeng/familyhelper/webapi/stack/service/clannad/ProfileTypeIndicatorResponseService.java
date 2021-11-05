package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.ProfileTypeIndicatorKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 个人简介类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public interface ProfileTypeIndicatorResponseService extends Service {

    boolean exists(ProfileTypeIndicatorKey key) throws ServiceException;

    ProfileTypeIndicator get(ProfileTypeIndicatorKey key) throws ServiceException;

    ProfileTypeIndicatorKey insert(ProfileTypeIndicator profileTypeIndicator) throws ServiceException;

    void update(ProfileTypeIndicator profileTypeIndicator) throws ServiceException;

    void delete(ProfileTypeIndicatorKey key) throws ServiceException;

    PagedData<ProfileTypeIndicator> childForCategory(String category, PagingInfo pagingInfo) throws ServiceException;
}
