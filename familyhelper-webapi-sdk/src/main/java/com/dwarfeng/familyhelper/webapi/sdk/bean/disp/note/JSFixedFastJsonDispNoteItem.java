package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteItem;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示笔记项目。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispNoteItem implements Dto {

    private static final long serialVersionUID = -2903986769298444282L;

    public static JSFixedFastJsonDispNoteItem of(DispNoteItem dispNoteItem) {
        if (Objects.isNull(dispNoteItem)) {
            return null;
        } else {
            return new JSFixedFastJsonDispNoteItem(
                    JSFixedFastJsonLongIdKey.of(dispNoteItem.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispNoteItem.getNodeKey()),
                    JSFixedFastJsonLongIdKey.of(dispNoteItem.getBookKey()),
                    dispNoteItem.getName(), dispNoteItem.getRemark(), dispNoteItem.getLength(),
                    dispNoteItem.getModifiedDate(), dispNoteItem.getInspectedDate(),
                    dispNoteItem.getInspectedDate(), dispNoteItem.getIndex(),
                    JSFixedFastJsonDispNoteBook.of(dispNoteItem.getBook()),
                    JSFixedFastJsonDispNoteNode.of(dispNoteItem.getNode())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "node_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey nodeKey;

    @JSONField(name = "book_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey bookKey;

    @JSONField(name = "name", ordinal = 4)
    private String name;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    @JSONField(name = "length", ordinal = 6)
    private long length;

    @JSONField(name = "created_date", ordinal = 7)
    private Date createdDate;

    @JSONField(name = "modified_date", ordinal = 8)
    private Date modifiedDate;

    @JSONField(name = "inspected_date", ordinal = 9)
    private Date inspectedDate;

    @JSONField(name = "index", ordinal = 10)
    private int index;

    @JSONField(name = "book", ordinal = 11)
    private JSFixedFastJsonDispNoteBook book;

    @JSONField(name = "node", ordinal = 12)
    private JSFixedFastJsonDispNoteNode node;

    public JSFixedFastJsonDispNoteItem() {
    }

    public JSFixedFastJsonDispNoteItem(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey nodeKey, JSFixedFastJsonLongIdKey bookKey,
            String name, String remark, long length, Date createdDate, Date modifiedDate, Date inspectedDate,
            int index, JSFixedFastJsonDispNoteBook book, JSFixedFastJsonDispNoteNode node
    ) {
        this.key = key;
        this.nodeKey = nodeKey;
        this.bookKey = bookKey;
        this.name = name;
        this.remark = remark;
        this.length = length;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.inspectedDate = inspectedDate;
        this.index = index;
        this.book = book;
        this.node = node;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(JSFixedFastJsonLongIdKey nodeKey) {
        this.nodeKey = nodeKey;
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

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getInspectedDate() {
        return inspectedDate;
    }

    public void setInspectedDate(Date inspectedDate) {
        this.inspectedDate = inspectedDate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public JSFixedFastJsonDispNoteBook getBook() {
        return book;
    }

    public void setBook(JSFixedFastJsonDispNoteBook book) {
        this.book = book;
    }

    public JSFixedFastJsonDispNoteNode getNode() {
        return node;
    }

    public void setNode(JSFixedFastJsonDispNoteNode node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispNoteItem{" +
                "key=" + key +
                ", nodeKey=" + nodeKey +
                ", bookKey=" + bookKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", length=" + length +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", inspectedDate=" + inspectedDate +
                ", index=" + index +
                ", book=" + book +
                ", node=" + node +
                '}';
    }
}
