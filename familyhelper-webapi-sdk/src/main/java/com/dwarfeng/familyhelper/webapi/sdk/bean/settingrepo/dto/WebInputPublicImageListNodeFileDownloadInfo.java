package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicImageListNodeFileDownloadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 公共图片列表节点文件下载信息。
 *
 * @author DwArFeng
 * @since 1.7.0
 */
public class WebInputPublicImageListNodeFileDownloadInfo implements Dto {

    private static final long serialVersionUID = 3646002814334355677L;

    public static PublicImageListNodeFileDownloadInfo toStackBean(
            WebInputPublicImageListNodeFileDownloadInfo webInput
    ) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PublicImageListNodeFileDownloadInfo(
                    webInput.getArgs(),
                    webInput.getIndex()
            );
        }
    }

    @JSONField(name = "args")
    @NotNull
    private String[] args;

    @JSONField(name = "index")
    @PositiveOrZero
    private int index;

    public WebInputPublicImageListNodeFileDownloadInfo() {
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "WebInputPublicImageListNodeFileDownloadInfo{" +
                "args=" + Arrays.toString(args) +
                ", index=" + index +
                '}';
    }
}
