package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.sdk.util.ValidInspectorVariableValueType;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计器变量插入/更新信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class WebInputInspectorVariableUpsertInfo implements Bean {

    private static final long serialVersionUID = -5752490101867564847L;

    public static InspectorVariableUpsertInfo toStackBean(WebInputInspectorVariableUpsertInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new InspectorVariableUpsertInfo(
                    WebInputLongIdKey.toStackBean(webInput.getInspectorInfoKey()),
                    webInput.getInspectorVariableId(),
                    webInput.getValueType(),
                    webInput.getValueString()
            );
        }
    }

    @JSONField(name = "inspector_info_key")
    @Valid
    @NotNull
    private WebInputLongIdKey inspectorInfoKey;

    @JSONField(name = "inspector_variable_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_STRING_ID)
    private String inspectorVariableId;

    @JSONField(name = "value_type")
    @ValidInspectorVariableValueType
    private int valueType;

    @JSONField(name = "value_string")
    private String valueString;

    public WebInputInspectorVariableUpsertInfo() {
    }

    public WebInputLongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(WebInputLongIdKey inspectorInfoKey) {
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

    @Override
    public String toString() {
        return "WebInputInspectorVariableUpsertInfo{" +
                "inspectorInfoKey=" + inspectorInfoKey +
                ", inspectorVariableId='" + inspectorVariableId + '\'' +
                ", valueType=" + valueType +
                ", valueString='" + valueString + '\'' +
                '}';
    }
}
