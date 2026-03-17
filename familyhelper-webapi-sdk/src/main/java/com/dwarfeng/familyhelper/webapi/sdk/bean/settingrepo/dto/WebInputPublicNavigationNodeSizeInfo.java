package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicNavigationNodeSizeInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共导航节点大小信息。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public class WebInputPublicNavigationNodeSizeInfo implements Dto {

    private static final long serialVersionUID = -6499539470583111329L;

    public static PublicNavigationNodeSizeInfo toStackBean(WebInputPublicNavigationNodeSizeInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicNavigationNodeSizeInfo(
                    webInput.getCategory(),
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "category")
    @NotNull
    @NotEmpty
    private String category;

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicNavigationNodeSizeInfo() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicNavigationNodeSizeInfo{" +
                "category='" + category + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
