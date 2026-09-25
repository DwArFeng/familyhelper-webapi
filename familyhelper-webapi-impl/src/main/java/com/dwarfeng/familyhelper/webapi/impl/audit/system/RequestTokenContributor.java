package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.TokenHandlerHttpServletRequestContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 请求令牌贡献器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.requestTokenContributor")
public class RequestTokenContributor implements Contributor {

    private final TokenHandlerHttpServletRequestContributor contributor;

    public RequestTokenContributor(TokenHandler tokenHandler) {
        this.contributor = new TokenHandlerHttpServletRequestContributor(tokenHandler);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
