package com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispMeta;
import com.dwarfeng.notify.sdk.bean.entity.FastJsonMetaIndicator;
import com.dwarfeng.notify.sdk.bean.key.JSFixedFastJsonMetaKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示元数据。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispMeta implements Dto {

    private static final long serialVersionUID = -5504135002996791491L;

    public static JSFixedFastJsonDispMeta of(DispMeta dispMeta) {
        if (Objects.isNull(dispMeta)) {
            return null;
        } else {
            return new JSFixedFastJsonDispMeta(
                    JSFixedFastJsonMetaKey.of(dispMeta.getKey()),
                    dispMeta.getValue(), dispMeta.getRemark(),
                    FastJsonMetaIndicator.of(dispMeta.getIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonMetaKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "indicator", ordinal = 4)
    private FastJsonMetaIndicator indicator;

    public JSFixedFastJsonDispMeta() {
    }

    public JSFixedFastJsonDispMeta(
            JSFixedFastJsonMetaKey key, String value, String remark, FastJsonMetaIndicator indicator
    ) {
        this.key = key;
        this.value = value;
        this.remark = remark;
        this.indicator = indicator;
    }

    public JSFixedFastJsonMetaKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonMetaKey key) {
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

    public FastJsonMetaIndicator getIndicator() {
        return indicator;
    }

    public void setIndicator(FastJsonMetaIndicator indicator) {
        this.indicator = indicator;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispMeta{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", remark='" + remark + '\'' +
                ", indicator=" + indicator +
                '}';
    }
}
