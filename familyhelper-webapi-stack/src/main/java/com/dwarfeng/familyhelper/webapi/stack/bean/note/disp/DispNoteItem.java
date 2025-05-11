package com.dwarfeng.familyhelper.webapi.stack.bean.note.disp;

import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteItem;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示笔记项目。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispNoteItem implements Dto {

    private static final long serialVersionUID = -736587265501407365L;

    public static DispNoteItem of(NoteItem item, DispNoteBook book, DispNoteNode node) {
        if (Objects.isNull(item)) {
            return null;
        } else {
            return new DispNoteItem(
                    item.getKey(), item.getNodeKey(), item.getBookKey(), item.getName(), item.getRemark(),
                    item.getLength(), item.getCreatedDate(), item.getModifiedDate(), item.getInspectedDate(),
                    item.getIndex(), book, node
            );
        }
    }

    private LongIdKey key;
    private LongIdKey nodeKey;
    private LongIdKey bookKey;
    private String name;
    private String remark;
    private long length;
    private Date createdDate;
    private Date modifiedDate;
    private Date inspectedDate;
    private int index;
    private DispNoteBook book;
    private DispNoteNode node;

    public DispNoteItem() {
    }

    public DispNoteItem(
            LongIdKey key, LongIdKey nodeKey, LongIdKey bookKey, String name, String remark, long length,
            Date createdDate, Date modifiedDate, Date inspectedDate, int index, DispNoteBook book, DispNoteNode node
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(LongIdKey nodeKey) {
        this.nodeKey = nodeKey;
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

    public DispNoteBook getBook() {
        return book;
    }

    public void setBook(DispNoteBook book) {
        this.book = book;
    }

    public DispNoteNode getNode() {
        return node;
    }

    public void setNode(DispNoteNode node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "DispNoteItem{" +
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
