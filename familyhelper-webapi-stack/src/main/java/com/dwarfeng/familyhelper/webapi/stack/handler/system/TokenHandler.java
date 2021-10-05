package com.dwarfeng.familyhelper.webapi.stack.handler.system;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import javax.servlet.http.HttpServletRequest;

/**
 * Token处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TokenHandler extends Handler {

    long getTokenId(HttpServletRequest httpServletRequest) throws HandlerException;

    StringIdKey getAccountKey(HttpServletRequest httpServletRequest) throws HandlerException;
}
