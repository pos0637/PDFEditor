package com.example.pdfdemo1.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改日志
 *
 * @author etrit
 */
@Getter
@Setter
public class ModifyLog {
    /**
     * 旧值
     */
    private String oldValue = "";

    /**
     * 新值
     */
    private String newValue = "";

    /**
     * 时间
     */
    private String time;

    /**
     * 用户名
     */
    private String user;

    /**
     * 域名
     */
    private String fieldName;
}
