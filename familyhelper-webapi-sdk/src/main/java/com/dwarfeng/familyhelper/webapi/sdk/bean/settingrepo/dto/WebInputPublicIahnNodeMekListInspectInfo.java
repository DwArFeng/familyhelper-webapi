package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicIahnNodeMekListInspectInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共国际化节点 Mek 列表查询信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicIahnNodeMekListInspectInfo implements Dto {

    private static final long serialVersionUID = -1143622642101222100L;

    public static PublicIahnNodeMekListInspectInfo toStackBean(WebInputPublicIahnNodeMekListInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicIahnNodeMekListInspectInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicIahnNodeMekListInspectInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicIahnNodeMekListInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
