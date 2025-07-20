package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeInspectInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共图片节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicImageNodeInspectInfo implements Dto {

    private static final long serialVersionUID = 8291447357675711981L;

    public static PublicImageNodeInspectInfo toStackBean(WebInputPublicImageNodeInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicImageNodeInspectInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicImageNodeInspectInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicImageNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
