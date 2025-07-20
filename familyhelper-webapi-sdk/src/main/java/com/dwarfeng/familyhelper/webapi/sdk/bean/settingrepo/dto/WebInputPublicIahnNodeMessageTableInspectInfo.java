package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicIahnNodeMessageTableInspectInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共国际化节点消息表查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicIahnNodeMessageTableInspectInfo implements Dto {

    private static final long serialVersionUID = 1648599110465388226L;

    public static PublicIahnNodeMessageTableInspectInfo toStackBean(
            WebInputPublicIahnNodeMessageTableInspectInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicIahnNodeMessageTableInspectInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicIahnNodeMessageTableInspectInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicIahnNodeMessageTableInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
