package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicTextNodeInspectInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共文本节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicTextNodeInspectInfo implements Dto {

    private static final long serialVersionUID = -4111080814660053601L;

    public static PublicTextNodeInspectInfo toStackBean(WebInputPublicTextNodeInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicTextNodeInspectInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicTextNodeInspectInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicTextNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
