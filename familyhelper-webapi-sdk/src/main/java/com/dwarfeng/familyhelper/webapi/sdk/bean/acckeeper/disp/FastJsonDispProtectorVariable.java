package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonProtectorInfo;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonProtectorVariableKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectorVariable;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 可展示的保护器变量。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispProtectorVariable implements Dto {

    private static final long serialVersionUID = -6249373832453415331L;

    public static FastJsonDispProtectorVariable of(DispProtectorVariable dispProtectorVariable) {
        if (Objects.isNull(dispProtectorVariable)) {
            return null;
        } else {
            return new FastJsonDispProtectorVariable(
                    FastJsonProtectorVariableKey.of(dispProtectorVariable.getKey()),
                    dispProtectorVariable.getValue(),
                    dispProtectorVariable.getRemark(),
                    FastJsonProtectorInfo.of(dispProtectorVariable.getProtectorInfo())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonProtectorVariableKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "protector_info", ordinal = 4)
    private FastJsonProtectorInfo protectorInfo;

    public FastJsonDispProtectorVariable() {
    }

    public FastJsonDispProtectorVariable(
            FastJsonProtectorVariableKey key, String value, String remark, FastJsonProtectorInfo protectorInfo
    ) {
        this.key = key;
        this.value = value;
        this.remark = remark;
        this.protectorInfo = protectorInfo;
    }

    public FastJsonProtectorVariableKey getKey() {
        return key;
    }

    public void setKey(FastJsonProtectorVariableKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FastJsonProtectorInfo getProtectorInfo() {
        return protectorInfo;
    }

    public void setProtectorInfo(FastJsonProtectorInfo protectorInfo) {
        this.protectorInfo = protectorInfo;
    }

    @Override
    public String toString() {
        return "FastJsonDispProtectorVariable{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                ", protectorInfo=" + protectorInfo +
                '}';
    }
}
