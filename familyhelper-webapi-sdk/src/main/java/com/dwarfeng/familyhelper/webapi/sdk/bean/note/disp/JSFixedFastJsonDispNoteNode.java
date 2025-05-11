package com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteNode;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示笔记节点。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispNoteNode implements Dto {

    private static final long serialVersionUID = -1894609730419890909L;

    public static JSFixedFastJsonDispNoteNode of(DispNoteNode dispNoteNode) {
        if (Objects.isNull(dispNoteNode)) {
            return null;
        } else {
            return new JSFixedFastJsonDispNoteNode(
                    JSFixedFastJsonLongIdKey.of(dispNoteNode.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispNoteNode.getParentKey()),
                    JSFixedFastJsonLongIdKey.of(dispNoteNode.getBookKey()),
                    dispNoteNode.getName(), dispNoteNode.getRemark(),
                    JSFixedFastJsonDispNoteBook.of(dispNoteNode.getBook()),
                    dispNoteNode.isRootFlag(), dispNoteNode.isHasNoChild()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey parentKey;

    @JSONField(name = "book_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey bookKey;

    @JSONField(name = "name", ordinal = 4)
    private String name;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    @JSONField(name = "book", ordinal = 6)
    private JSFixedFastJsonDispNoteBook book;

    @JSONField(name = "root_flag", ordinal = 7)
    private boolean rootFlag;

    @JSONField(name = "has_no_child", ordinal = 8)
    private boolean hasNoChild;

    public JSFixedFastJsonDispNoteNode() {
    }

    public JSFixedFastJsonDispNoteNode(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey parentKey, JSFixedFastJsonLongIdKey bookKey,
            String name, String remark, JSFixedFastJsonDispNoteBook book, boolean rootFlag, boolean hasNoChild
    ) {
        this.key = key;
        this.parentKey = parentKey;
        this.bookKey = bookKey;
        this.name = name;
        this.remark = remark;
        this.book = book;
        this.rootFlag = rootFlag;
        this.hasNoChild = hasNoChild;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(JSFixedFastJsonLongIdKey parentKey) {
        this.parentKey = parentKey;
    }

    public JSFixedFastJsonLongIdKey getBookKey() {
        return bookKey;
    }

    public void setBookKey(JSFixedFastJsonLongIdKey bookKey) {
        this.bookKey = bookKey;
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

    public JSFixedFastJsonDispNoteBook getBook() {
        return book;
    }

    public void setBook(JSFixedFastJsonDispNoteBook book) {
        this.book = book;
    }

    public boolean isRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(boolean rootFlag) {
        this.rootFlag = rootFlag;
    }

    public boolean isHasNoChild() {
        return hasNoChild;
    }

    public void setHasNoChild(boolean hasNoChild) {
        this.hasNoChild = hasNoChild;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispNoteNode{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", bookKey=" + bookKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", book=" + book +
                ", rootFlag=" + rootFlag +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
