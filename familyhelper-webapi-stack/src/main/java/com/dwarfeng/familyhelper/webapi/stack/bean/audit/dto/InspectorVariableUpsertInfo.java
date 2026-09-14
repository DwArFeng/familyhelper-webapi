package com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 审计器变量插入/更新信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class InspectorVariableUpsertInfo {

    private LongIdKey inspectorInfoKey;
    private String inspectorVariableId;

    /**
     * 审计器变量值类型。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>文本</li>
     *     <li>整数</li>
     *     <li>浮点数</li>
     *     <li>布尔值</li>
     *     <li>日期值</li>
     * </ol>
     * 详细值参考 audit-sdk 模块的常量工具类。
     */
    private int valueType;

    /**
     * 审计器变量值的字符串形式。
     */
    private String valueString;

    public InspectorVariableUpsertInfo() {
    }

    public InspectorVariableUpsertInfo(
            LongIdKey inspectorInfoKey, String inspectorVariableId, int valueType, String valueString
    ) {
        this.inspectorInfoKey = inspectorInfoKey;
        this.inspectorVariableId = inspectorVariableId;
        this.valueType = valueType;
        this.valueString = valueString;
    }

    public LongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(LongIdKey inspectorInfoKey) {
        this.inspectorInfoKey = inspectorInfoKey;
    }

    public String getInspectorVariableId() {
        return inspectorVariableId;
    }

    public void setInspectorVariableId(String inspectorVariableId) {
        this.inspectorVariableId = inspectorVariableId;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
