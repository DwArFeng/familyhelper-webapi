package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeInspectInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共图片列表节点查看信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicImageListNodeInspectInfo implements Dto {

    private static final long serialVersionUID = -4867707722733464467L;

    public static PublicImageListNodeInspectInfo toStackBean(WebInputPublicImageListNodeInspectInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicImageListNodeInspectInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicImageListNodeInspectInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicImageListNodeInspectInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
