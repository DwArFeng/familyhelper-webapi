package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp;

import com.dwarfeng.settingrepo.stack.bean.entity.WarmupDriverInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.WarmupSetting;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示预热驱动信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispWarmupDriverInfo implements Dto {

    private static final long serialVersionUID = -6936102736134968730L;

    public static DispWarmupDriverInfo of(WarmupDriverInfo warmupDriverInfo, WarmupSetting warmupSetting) {
        if (Objects.isNull(warmupDriverInfo)) {
            return null;
        } else {
            return new DispWarmupDriverInfo(
                    warmupDriverInfo.getKey(),
                    warmupDriverInfo.getWarmupSettingKey(),
                    warmupDriverInfo.isEnabled(),
                    warmupDriverInfo.getType(),
                    warmupDriverInfo.getParam(),
                    warmupDriverInfo.getRemark(),
                    warmupSetting
            );
        }
    }

    private LongIdKey key;
    private LongIdKey warmupSettingKey;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;
    private WarmupSetting warmupSetting;

    public DispWarmupDriverInfo() {
    }

    public DispWarmupDriverInfo(
            LongIdKey key, LongIdKey warmupSettingKey, boolean enabled, String type, String param, String remark,
            WarmupSetting warmupSetting
    ) {
        this.key = key;
        this.warmupSettingKey = warmupSettingKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.warmupSetting = warmupSetting;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getWarmupSettingKey() {
        return warmupSettingKey;
    }

    public void setWarmupSettingKey(LongIdKey warmupSettingKey) {
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

    public WarmupSetting getWarmupSetting() {
        return warmupSetting;
    }

    public void setWarmupSetting(WarmupSetting warmupSetting) {
        this.warmupSetting = warmupSetting;
    }

    @Override
    public String toString() {
        return "DispWarmupDriverInfo{" +
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
