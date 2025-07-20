package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicSettingNodeInspectInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共设置节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicSettingNodeInspectInfo implements Dto {

    private static final long serialVersionUID = -3182123447810139170L;

    public static PublicSettingNodeInspectInfo toStackBean(WebInputPublicSettingNodeInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicSettingNodeInspectInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicSettingNodeInspectInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicSettingNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
