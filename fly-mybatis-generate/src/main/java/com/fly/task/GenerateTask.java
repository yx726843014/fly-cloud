package com.fly.task;

import lombok.Data;

/**
 * youxiong
 */
@Data
public class GenerateTask {

    private String projectLocation;

    private String tablePrefix;

    private String packageName;

    private String tableName;

    private String entityName;

    private String module;

    private String developer;

    private Boolean onlyGenerateEntity;

    private String sqlUrl;

    private String dbType;

    private String username;

    private String password;

    private String filterName;
}
