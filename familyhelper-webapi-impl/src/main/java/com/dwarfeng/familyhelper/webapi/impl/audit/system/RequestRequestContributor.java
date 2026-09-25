package com.dwarfeng.familyhelper.webapi.impl.audit.system;

import com.dwarfeng.subgrade.sdk.interceptor.audit.AuditRecordContext;
import com.dwarfeng.subgrade.sdk.interceptor.audit.Contributor;
import com.dwarfeng.subgrade.sdk.interceptor.audit.contributor.GeneralHttpServletRequestContributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 请求请求贡献器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Component("system.requestRequestContributor")
public class RequestRequestContributor implements Contributor {

    private static final List<GeneralHttpServletRequestContributor.Rule> CONTRIBUTOR_RULES;

    static {
        List<GeneralHttpServletRequestContributor.Rule> CONTRIBUTOR_RULES_DEJA_VU = new ArrayList<>();
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.of(
                "remote_addr", HttpServletRequest::getRemoteAddr
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.header(
                "user_agent", "User-Agent"
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.of(
                "method", HttpServletRequest::getMethod
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.of(
                "request_uri", HttpServletRequest::getRequestURI
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.of(
                "scheme", HttpServletRequest::getScheme
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.header(
                "accept_language", "Accept-Language"
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.header(
                "referer", "Referer"
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.header(
                "x_forwarded_for", "X-Forwarded-For"
        ));
        CONTRIBUTOR_RULES_DEJA_VU.add(GeneralHttpServletRequestContributor.Rule.header(
                "x_real_ip", "X-Real-IP"
        ));
        CONTRIBUTOR_RULES = Collections.unmodifiableList(CONTRIBUTOR_RULES_DEJA_VU);
    }

    private final GeneralHttpServletRequestContributor contributor;

    public RequestRequestContributor() {
        this.contributor = new GeneralHttpServletRequestContributor(CONTRIBUTOR_RULES);
    }

    @Override
    public @NonNull Map<String, Object> resolveProperties(@NonNull AuditRecordContext context, @Nullable Object source)
            throws Exception {
        return contributor.resolveProperties(context, source);
    }
}
