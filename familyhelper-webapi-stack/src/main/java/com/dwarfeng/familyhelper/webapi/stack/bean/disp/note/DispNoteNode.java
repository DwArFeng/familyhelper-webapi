package com.dwarfeng.familyhelper.webapi.stack.bean.disp.note;

import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteNode;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示笔记节点。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispNoteNode implements Dto {

    private static final long serialVersionUID = -2276021043308572726L;

    public static DispNoteNode of(NoteNode node, DispNoteBook set, boolean hasNoChild) {
        if (Objects.isNull(node)) {
            return null;
        } else {
            return new DispNoteNode(
                    node.getKey(), node.getParentKey(), node.getBookKey(), node.getName(), node.getRemark(), set,
                    Objects.isNull(node.getParentKey()), hasNoChild
            );
        }
    }

    private LongIdKey key;
    private LongIdKey parentKey;
    private LongIdKey bookKey;
    private String name;
    private String remark;
    private DispNoteBook book;
    private boolean rootFlag;
    private boolean hasNoChild;

    public DispNoteNode() {
    }

    public DispNoteNode(
            LongIdKey key, LongIdKey parentKey, LongIdKey bookKey, String name, String remark, DispNoteBook book,
            boolean rootFlag, boolean hasNoChild
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(LongIdKey parentKey) {
        this.parentKey = parentKey;
    }

    public LongIdKey getBookKey() {
        return bookKey;
    }

    public void setBookKey(LongIdKey bookKey) {
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

    public DispNoteBook getBook() {
        return book;
    }

    public void setBook(DispNoteBook book) {
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
        return "DispNoteNode{" +
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
