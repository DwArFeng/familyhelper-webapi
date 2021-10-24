package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.LoginInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.LoginResponse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface LoginResponseService extends Service {

    /**
     * 判断指定的登录主键是否登录。
     *
     * @param longIdKey 指定的登录主键。
     * @return 指定的登录主键是否登录。
     * @throws ServiceException 服务异常。
     */
    boolean isLogin(LongIdKey longIdKey) throws ServiceException;

    /**
     * 获取个人登录信息。
     *
     * @param longIdKey 指定的登录主键。
     * @return 登录状态响应。
     * @throws ServiceException 服务异常。
     */
    LoginResponse inspectLoginState(LongIdKey longIdKey) throws ServiceException;

    /**
     * 登录。
     *
     * @param loginInfo 登录信息。
     * @return 登录状态响应。
     * @throws ServiceException 服务异常。
     */
    LoginResponse login(LoginInfo loginInfo) throws ServiceException;

    /**
     * 登出。
     *
     * @param longIdKey 指定的登录主键。
     * @throws ServiceException 服务异常。
     */
    void logout(LongIdKey longIdKey) throws ServiceException;

    /**
     * 延长指定登录主键的登录时间。
     *
     * @param longIdKey 指定的登录主键。
     * @throws ServiceException 服务异常。
     */
    LoginResponse postpone(LongIdKey longIdKey) throws ServiceException;
}
