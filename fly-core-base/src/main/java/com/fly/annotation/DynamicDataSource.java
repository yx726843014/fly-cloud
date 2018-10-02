package com.fly.annotation;

import com.fly.config.ds.DatabaseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 游雄
 * @describe
 * @create 11:29 2018/10/2 0002
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicDataSource {
    DatabaseType type();
}
