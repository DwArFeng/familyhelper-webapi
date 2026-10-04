package com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp;

import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskEvent;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示任务事件。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class DispTaskEvent implements Dto {

    private static final long serialVersionUID = 282847405583185745L;

    public static DispTaskEvent of(TaskEvent taskEvent, Task task) {
        if (Objects.isNull(taskEvent)) {
            return null;
        } else {
            return new DispTaskEvent(
                    taskEvent.getKey(),
                    taskEvent.getTaskKey(),
                    taskEvent.getHappenedDate(),
                    taskEvent.getMessage(),
                    task
            );
        }
    }

    private LongIdKey key;
    private LongIdKey taskKey;
    private Date happenedDate;
    private String message;
    private Task task;

    public DispTaskEvent() {
    }

    public DispTaskEvent(LongIdKey key, LongIdKey taskKey, Date happenedDate, String message, Task task) {
        this.key = key;
        this.taskKey = taskKey;
        this.happenedDate = happenedDate;
        this.message = message;
        this.task = task;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "DispTaskEvent{" +
                "key=" + key +
                ", taskKey=" + taskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                ", task=" + task +
                '}';
    }
}
