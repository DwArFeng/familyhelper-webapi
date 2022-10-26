package com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify;

import com.dwarfeng.notify.stack.bean.entity.Meta;
import com.dwarfeng.notify.stack.bean.entity.MetaIndicator;
import com.dwarfeng.notify.stack.bean.entity.key.MetaKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示元数据。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispMeta implements Dto {

    private static final long serialVersionUID = 4012526024053941416L;

    public static DispMeta of(Meta meta, MetaIndicator metaIndicator) {
        if (Objects.isNull(meta)) {
            return null;
        } else {
            return new DispMeta(
                    meta.getKey(), meta.getValue(), meta.getRemark(),
                    metaIndicator
            );
        }
    }

    private MetaKey key;
    private String value;
    private String remark;
    private MetaIndicator indicator;

    public DispMeta() {
    }

    public DispMeta(MetaKey key, String value, String remark, MetaIndicator indicator) {
        this.key = key;
        this.value = value;
        this.remark = remark;
        this.indicator = indicator;
    }

    public MetaKey getKey() {
        return key;
    }

    public void setKey(MetaKey key) {
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

    public MetaIndicator getIndicator() {
        return indicator;
    }

    public void setIndicator(MetaIndicator indicator) {
        this.indicator = indicator;
    }

    @Override
    public String toString() {
        return "DispMeta{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                ", indicator=" + indicator +
                '}';
    }
}
