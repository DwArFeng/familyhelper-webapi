package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeSizeInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共图片列表节点大小信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicImageListNodeSizeInfo implements Dto {

    private static final long serialVersionUID = -463160618977645767L;

    public static PublicImageListNodeSizeInfo toStackBean(WebInputPublicImageListNodeSizeInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicImageListNodeSizeInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicImageListNodeSizeInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicImageListNodeSizeInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
