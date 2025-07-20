package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicIahnNodeMessageInspectInfo;
import com.dwarfeng.settingrepo.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共国际化节点消息查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicIahnNodeMessageInspectInfo implements Dto {

    private static final long serialVersionUID = 3988963975799922737L;

    public static PublicIahnNodeMessageInspectInfo toStackBean(WebInputPublicIahnNodeMessageInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicIahnNodeMessageInspectInfo(
                    webInput.getArgs(),
                    webInput.getLanguage(),
                    webInput.getCountry(),
                    webInput.getVariant(),
                    webInput.getMekId()
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

    @JSONField(name = "mek_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_IAHN_NODE_MEK_ID)
    private String mekId;

    public WebInputPublicIahnNodeMessageInspectInfo() {
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

    public String getMekId() {
        return mekId;
    }

    public void setMekId(String mekId) {
        this.mekId = mekId;
    }

    @Override
    public String toString() {
        return "WebInputPublicIahnNodeMessageInspectInfo{" +
                "args=" + Arrays.toString(args) +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", variant='" + variant + '\'' +
                ", mekId='" + mekId + '\'' +
                '}';
    }
}
