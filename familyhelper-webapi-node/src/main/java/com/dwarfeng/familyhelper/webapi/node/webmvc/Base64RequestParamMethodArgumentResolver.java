package com.dwarfeng.familyhelper.webapi.node.webmvc;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * Base64JsonRequestParam 方法参数解析器。
 *
 * @author DwArFeng
 * @since 1.3.0
 */
public class Base64RequestParamMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @Override
    public boolean supportsParameter(@Nonnull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Base64RequestParam.class);
    }

    @Nonnull
    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        Base64RequestParam ann = parameter.getParameterAnnotation(Base64RequestParam.class);
        return (ann != null ? new Base64RequestParamNamedValueInfo(ann) : new Base64RequestParamNamedValueInfo());
    }

    @Override
    protected Object resolveName(
            @Nonnull String name, @Nonnull MethodParameter parameter, @Nonnull NativeWebRequest request
    ) {
        // 获取 Base64 编码的参数值。
        String encodedValue = request.getParameter(name);

        // 空值处理。
        if (Objects.isNull(encodedValue)) {
            return null;
        }

        // 解码并返回。
        byte[] decodedBytes = Base64.getDecoder().decode(encodedValue.getBytes(DEFAULT_CHARSET));
        // 测试。
        String json = new String(decodedBytes, DEFAULT_CHARSET);
        return JSON.parseObject(json, parameter.getNestedParameterType());
    }

    @Override
    protected void handleMissingValue(
            @Nonnull String name, @Nonnull MethodParameter parameter, @Nonnull NativeWebRequest request
    ) throws Exception {
        handleMissingValueInternal(name, parameter, false);
    }

    @Override
    protected void handleMissingValueAfterConversion(
            @Nonnull String name, @Nonnull MethodParameter parameter, @Nonnull NativeWebRequest request
    ) throws Exception {
        handleMissingValueInternal(name, parameter, true);
    }

    protected void handleMissingValueInternal(
            String name, MethodParameter parameter, boolean missingAfterConversion
    ) throws Exception {
        throw new MissingServletRequestParameterException(
                name, parameter.getNestedParameterType().getSimpleName(), missingAfterConversion
        );
    }

    @Override
    protected void handleResolvedValue(
            Object arg, @Nonnull String name, @Nonnull MethodParameter parameter,
            ModelAndViewContainer mavContainer, @Nonnull NativeWebRequest webRequest
    ) {
        super.handleResolvedValue(arg, name, parameter, mavContainer, webRequest);
    }

    private static class Base64RequestParamNamedValueInfo extends NamedValueInfo {

        public Base64RequestParamNamedValueInfo() {
            super("", false, ValueConstants.DEFAULT_NONE);
        }

        public Base64RequestParamNamedValueInfo(Base64RequestParam annotation) {
            super(annotation.name(), annotation.required(), ValueConstants.DEFAULT_NONE);
        }
    }
}
