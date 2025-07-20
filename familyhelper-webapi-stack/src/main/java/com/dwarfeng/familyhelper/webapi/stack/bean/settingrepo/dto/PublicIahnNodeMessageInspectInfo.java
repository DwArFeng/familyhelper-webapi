package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 公共国际化节点消息查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class PublicIahnNodeMessageInspectInfo implements Dto {

    private static final long serialVersionUID = 3447562147937229906L;

    private String[] args;

    /**
     * 语言。
     *
     * <p>
     * 该值对应 <code>RFC 5646</code> 中的 <code>Language</code>。<br>
     * 其格式为：
     * <ul>
     *     <li> 2 位字母（如 zh 表示中文，en 表示英文）。</li>
     *     <li> 3 位字母（如 zho 表示中文，eng 表示英文）。</li>
     * </ul>
     */
    private String language;

    /**
     * 国家。
     *
     * <p>
     * 该值对应 <code>RFC 5646</code> 中的 <code>Region</code>。<br>
     * 其格式为：
     * <ul>
     *     <li> 2 位字母（如 CN 表示中国，US 表示美国）。</li>
     *     <li> 3 位数字（如 001 表示全球，142 表示亚洲）。</li>
     * </ul>
     */
    private String country;

    /**
     * 变体。
     *
     * <p>
     * 该值对应 <code>RFC 5646</code> 中的 <code>Variant</code>。<br>
     * 其格式为：
     * <ul>
     *     <li> 5-8 位数字或字母，首字符必须为字母（如 rozaj 表示 Resian 方言）。</li>
     * </ul>
     */
    private String variant;

    private String mekId;

    public PublicIahnNodeMessageInspectInfo() {
    }

    public PublicIahnNodeMessageInspectInfo(
            String[] args, String language, String country, String variant, String mekId
    ) {
        this.args = args;
        this.language = language;
        this.country = country;
        this.variant = variant;
        this.mekId = mekId;
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
        return "PublicIahnNodeMessageInspectInfo{" +
                "args=" + Arrays.toString(args) +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", variant='" + variant + '\'' +
                ", mekId='" + mekId + '\'' +
                '}';
    }
}
