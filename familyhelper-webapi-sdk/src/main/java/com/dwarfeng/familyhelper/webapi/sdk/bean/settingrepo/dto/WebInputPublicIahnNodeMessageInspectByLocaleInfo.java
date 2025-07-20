package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicIahnNodeMessageInspectByLocaleInfo;
import com.dwarfeng.settingrepo.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共国际化节点基于地区批量查询消息信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicIahnNodeMessageInspectByLocaleInfo implements Dto {

    private static final long serialVersionUID = 4877578194655340886L;

    public static PublicIahnNodeMessageInspectByLocaleInfo toStackBean(
            WebInputPublicIahnNodeMessageInspectByLocaleInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicIahnNodeMessageInspectByLocaleInfo(
                    webInput.getArgs(),
                    webInput.getLanguage(),
                    webInput.getCountry(),
                    webInput.getVariant()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    @JSONField(name = "language")
    @NotNull
    @Length(min = Constraints.LENGTH_IAHN_NODE_LANGUAGE_MIN, max = Constraints.LENGTH_IAHN_NODE_LANGUAGE_MAX)
    private String language;

    @JSONField(name = "country")
    @NotNull
    @Length(min = Constraints.LENGTH_IAHN_NODE_COUNTRY_MIN, max = Constraints.LENGTH_IAHN_NODE_COUNTRY_MAX)
    private String country;

    @JSONField(name = "variant")
    @NotNull
    @Length(min = Constraints.LENGTH_IAHN_NODE_VARIANT_MIN, max = Constraints.LENGTH_IAHN_NODE_VARIANT_MAX)
    private String variant;

    public WebInputPublicIahnNodeMessageInspectByLocaleInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    @Override
    public String toString() {
        return "WebInputPublicIahnNodeMessageInspectByLocaleInfo{" +
                "args=" + Arrays.toString(args) +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", variant='" + variant + '\'' +
                '}';
    }
}
