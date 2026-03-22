package com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示的保护器变量。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispProtectorVariable implements Dto {

    private static final long serialVersionUID = -1450827833503172901L;

    public static DispProtectorVariable of(ProtectorVariable protectorVariable, ProtectorInfo protectorInfo) {
        if (Objects.isNull(protectorVariable)) {
            return null;
        } else {
            return new DispProtectorVariable(
                    protectorVariable.getKey(),
                    protectorVariable.getValue(),
                    protectorVariable.getRemark(),
                    protectorInfo
            );
        }
    }

    private ProtectorVariableKey key;
    private String value;
    private String remark;
    private ProtectorInfo protectorInfo;

    public DispProtectorVariable() {
    }

    public DispProtectorVariable(ProtectorVariableKey key, String value, String remark, ProtectorInfo protectorInfo) {
        this.key = key;
        this.value = value;
        this.remark = remark;
        this.protectorInfo = protectorInfo;
    }

    public ProtectorVariableKey getKey() {
        return key;
    }

    public void setKey(ProtectorVariableKey key) {
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

    public ProtectorInfo getProtectorInfo() {
        return protectorInfo;
    }

    public void setProtectorInfo(ProtectorInfo protectorInfo) {
        this.protectorInfo = protectorInfo;
    }

    @Override
    public String toString() {
        return "DispProtectorVariable{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                ", protectorInfo=" + protectorInfo +
                '}';
    }
}
