package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispWarmupDriverInfo;
import com.dwarfeng.settingrepo.sdk.bean.entity.JSFixedFastJsonWarmupSetting;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示预热驱动信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonDispWarmupDriverInfo implements Dto {

    private static final long serialVersionUID = -6964178575563064965L;

    public static JSFixedFastJsonDispWarmupDriverInfo of(DispWarmupDriverInfo dispWarmupDriverInfo) {
        if (Objects.isNull(dispWarmupDriverInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonDispWarmupDriverInfo(
                    JSFixedFastJsonLongIdKey.of(dispWarmupDriverInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispWarmupDriverInfo.getWarmupSettingKey()),
                    dispWarmupDriverInfo.isEnabled(),
                    dispWarmupDriverInfo.getType(),
                    dispWarmupDriverInfo.getParam(),
                    dispWarmupDriverInfo.getRemark(),
                    JSFixedFastJsonWarmupSetting.of(dispWarmupDriverInfo.getWarmupSetting())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "warmup_setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey warmupSettingKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "warmup_setting", ordinal = 7)
    private JSFixedFastJsonWarmupSetting warmupSetting;

    public JSFixedFastJsonDispWarmupDriverInfo() {
    }

    public JSFixedFastJsonDispWarmupDriverInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey warmupSettingKey, boolean enabled, String type,
            String param, String remark, JSFixedFastJsonWarmupSetting warmupSetting
    ) {
        this.key = key;
        this.warmupSettingKey = warmupSettingKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.warmupSetting = warmupSetting;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getWarmupSettingKey() {
        return warmupSettingKey;
    }

    public void setWarmupSettingKey(JSFixedFastJsonLongIdKey warmupSettingKey) {
        this.warmupSettingKey = warmupSettingKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonWarmupSetting getWarmupSetting() {
        return warmupSetting;
    }

    public void setWarmupSetting(JSFixedFastJsonWarmupSetting warmupSetting) {
        this.warmupSetting = warmupSetting;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispWarmupDriverInfo{" +
                "key=" + key +
                ", warmupSettingKey=" + warmupSettingKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                ", warmupSetting=" + warmupSetting +
                '}';
    }
}
