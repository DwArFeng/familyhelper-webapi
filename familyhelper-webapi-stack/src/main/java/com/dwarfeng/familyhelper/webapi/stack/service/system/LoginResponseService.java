package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LoginResponseService extends Service {

    /**
     * 登录。
     *
     * @param info 动态登录信息。
     * @return 动态登录结果。
     * @throws ServiceException 服务异常。
     */
    LoginResult login(LoginInfo info) throws ServiceException;

    /**
     * 登出。
     *
     * <p>
     * 登出后该登录状态主键将失效。
     *
     * @param info 登出信息。
     * @throws ServiceException 服务异常。
     */
    void logout(LogoutInfo info) throws ServiceException;

    /**
     * 延期。
     *
     * <p>
     * 该操作可以将指定的登录状态的过期时间延长一段时间，从而保持登录状态的有效性，延长的时间由系统配置决定。
     *
     * @param info 延期信息。
     * @return 延期结果。
     * @throws ServiceException 服务异常。
     */
    PostponeResult postpone(PostponeInfo info) throws ServiceException;
}
