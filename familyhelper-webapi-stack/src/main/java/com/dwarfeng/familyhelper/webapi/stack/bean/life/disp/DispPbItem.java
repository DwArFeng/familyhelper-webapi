package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.PbItem;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示个人最佳项目。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispPbItem implements Dto {

    private static final long serialVersionUID = -5506498983382156964L;

    public static DispPbItem of(PbItem item, DispPbNode node) {
        if (Objects.isNull(item)) {
            return null;
        } else {
            return new DispPbItem(
                    item.getKey(), item.getNodeKey(), item.getName(), item.getUnit(), item.getComparator(),
                    item.getRemark(), node
            );
        }
    }

    private LongIdKey key;
    private LongIdKey nodeKey;
    private String name;
    private String unit;
    private Integer comparator;
    private String remark;
    private DispPbNode node;

    public DispPbItem() {
    }

    public DispPbItem(
            LongIdKey key, LongIdKey nodeKey, String name, String unit, Integer comparator, String remark,
            DispPbNode node
    ) {
        this.key = key;
        this.nodeKey = nodeKey;
        this.name = name;
        this.unit = unit;
        this.comparator = comparator;
        this.remark = remark;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getComparator() {
        return comparator;
    }

    public void setComparator(Integer comparator) {
        this.comparator = comparator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DispPbNode getNode() {
        return node;
    }

    public void setNode(DispPbNode node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "DispPbItem{" +
                "key=" + key +
                ", nodeKey=" + nodeKey +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", comparator=" + comparator +
                ", remark='" + remark + '\'' +
                ", node=" + node +
                '}';
    }
}
