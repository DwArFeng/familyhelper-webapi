package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicIahnNodeLocaleListInspectInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共国际化节点地区列表查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicIahnNodeLocaleListInspectInfo implements Dto {

    private static final long serialVersionUID = -9067124551438378798L;

    public static PublicIahnNodeLocaleListInspectInfo toStackBean(
            WebInputPublicIahnNodeLocaleListInspectInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicIahnNodeLocaleListInspectInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicIahnNodeLocaleListInspectInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicIahnNodeLocaleListInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
