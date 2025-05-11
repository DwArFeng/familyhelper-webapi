package com.dwarfeng.familyhelper.webapi.sdk.bean.note.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.note.sdk.util.Constraints;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteNodeUpdateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 兼容性笔记节点更新信息。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public class WebInputCompatibleNoteNodeUpdateInfo implements Dto {

    private static final long serialVersionUID = 3460502846097495929L;

    public static NoteNodeUpdateInfo toStackBean(WebInputCompatibleNoteNodeUpdateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            WebInputLongIdKey parentKey = webInput.getParentKey();
            if (Objects.isNull(parentKey)) {
                parentKey = webInput.getCompatibleParentKey();
            }
            return new NoteNodeUpdateInfo(
                    WebInputLongIdKey.toStackBean(webInput.getKey()),
                    WebInputLongIdKey.toStackBean(parentKey),
                    webInput.getName(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "parent_key")
    @Valid
    private WebInputLongIdKey parentKey;

    @JSONField(name = "set_key")
    @Valid
    private WebInputLongIdKey compatibleParentKey;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputCompatibleNoteNodeUpdateInfo() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(WebInputLongIdKey parentKey) {
        this.parentKey = parentKey;
    }

    public WebInputLongIdKey getCompatibleParentKey() {
        return compatibleParentKey;
    }

    public void setCompatibleParentKey(WebInputLongIdKey compatibleParentKey) {
        this.compatibleParentKey = compatibleParentKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputCompatibleNoteNodeUpdateInfo{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", compatibleParentKey=" + compatibleParentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
