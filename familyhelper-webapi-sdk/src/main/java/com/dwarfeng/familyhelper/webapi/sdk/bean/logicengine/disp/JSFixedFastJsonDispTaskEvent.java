package com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTaskEvent;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonTask;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示任务事件。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class JSFixedFastJsonDispTaskEvent implements Dto {

    private static final long serialVersionUID = 2770548520913754135L;

    public static JSFixedFastJsonDispTaskEvent of(DispTaskEvent dispTaskEvent) {
        if (Objects.isNull(dispTaskEvent)) {
            return null;
        } else {
            return new JSFixedFastJsonDispTaskEvent(
                    JSFixedFastJsonLongIdKey.of(dispTaskEvent.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispTaskEvent.getTaskKey()),
                    dispTaskEvent.getHappenedDate(),
                    dispTaskEvent.getMessage(),
                    JSFixedFastJsonTask.of(dispTaskEvent.getTask())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "task_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey taskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    @JSONField(name = "task", ordinal = 5)
    private JSFixedFastJsonTask task;

    public JSFixedFastJsonDispTaskEvent() {
    }

    public JSFixedFastJsonDispTaskEvent(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey taskKey, Date happenedDate, String message,
            JSFixedFastJsonTask task
    ) {
        this.key = key;
        this.taskKey = taskKey;
        this.happenedDate = happenedDate;
        this.message = message;
        this.task = task;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(JSFixedFastJsonLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSFixedFastJsonTask getTask() {
        return task;
    }

    public void setTask(JSFixedFastJsonTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispTaskEvent{" +
                "key=" + key +
                ", taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                ", task=" + task +
                '}';
    }
}
