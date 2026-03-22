package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 访问响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface AccessResponseService extends Service {

    /**
     * 动态登录。
     *
     * <p>
     * 请求成功后返回过期时间较短的登录状态，需要客户端定期延期以保持登录状态有效。
     *
     * @param info 动态登录信息。
     * @return 动态登录结果。
     * @throws ServiceException 服务异常。
     */
    DynamicLoginResult dynamicLogin(DynamicLoginInfo info) throws ServiceException;

    /**
     * 静态登录。
     *
     * <p>
     * 请求成功后返回指定过期时间的登录状态。
     *
     * @param info 静态登录信息。
     * @return 静态登录结果。
     * @throws ServiceException 服务异常。
     */
    StaticLoginResult staticLogin(StaticLoginInfo info) throws ServiceException;

    /**
     * 登出指定登录状态主键对应的登录状态。
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
     * <p>
     * 该方法仅对动态登录有效；对于静态登录不进行任何修改，结果中的过期时间保持不变。
     *
     * @param info 延期信息。
     * @return 延期结果。
     * @throws ServiceException 服务异常。
     */
    PostponeResult postpone(PostponeInfo info) throws ServiceException;
}
