package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.LoginRequest;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.LoginResponse;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.system.Account;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 登录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface LoginResponseService extends Service {

    /**
     * 登录。
     *
     * @param loginRequest 登录信息。
     * @return 登录状态响应。
     * @throws ServiceException 服务异常。
     */
    LoginResponse login(LoginRequest loginRequest) throws ServiceException;

    /**
     * 获取指定登录主键的登录信息。
     *
     * @param longIdKey 指定的登录主键。
     * @return 登录状态响应。
     * @throws ServiceException 服务异常。
     */
    LoginResponse getLoginResponse(LongIdKey longIdKey) throws ServiceException;

    /**
     * 获取指定的登录主键对应的权限信息。
     *
     * @param longIdKey 指定的登录主键。
     * @return 指定的登录主键对应的权限信息。
     * @throws ServiceException 服务异常。
     */
    List<String> getPermissions(LongIdKey longIdKey) throws ServiceException;

    /**
     * 判断指定的登录主键是否登录。
     *
     * @param longIdKey 指定的登录主键。
     * @return 指定的登录主键是否登录。
     * @throws ServiceException 服务异常。
     */
    boolean isLogin(LongIdKey longIdKey) throws ServiceException;

    /**
     * 延长指定登录主键的登录时间。
     *
     * @param longIdKey 指定的登录主键。
     * @throws ServiceException 服务异常。
     */
    void postpone(LongIdKey longIdKey) throws ServiceException;

    /**
     * 登出。
     *
     * @param longIdKey 指定的登录主键。
     * @throws ServiceException 服务异常。
     */
    void logout(LongIdKey longIdKey) throws ServiceException;

    /**
     * 我是谁(获取当前登录账号的详细信息)。
     *
     * @param longIdKey 指定的登录主键。
     * @throws ServiceException 服务异常。
     */
    Account whoAmI(LongIdKey longIdKey) throws ServiceException;
}
