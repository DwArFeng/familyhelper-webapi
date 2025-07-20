package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageNodeFileDownloadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共图片节点文件下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicImageNodeFileDownloadInfo implements Dto {

    private static final long serialVersionUID = -3728169635062656583L;

    public static PublicImageNodeFileDownloadInfo toStackBean(WebInputPublicImageNodeFileDownloadInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicImageNodeFileDownloadInfo(
                    webInput.getArgs()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    public WebInputPublicImageNodeFileDownloadInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "WebInputPublicImageNodeFileDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
