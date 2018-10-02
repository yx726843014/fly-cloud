package com.fly.aop;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author 游雄
 * @describe
 * @create 11:33 2018/10/2 0002
 */
@Aspect
@Component
@Order(-1)
@Slf4j
public class DynamicAop {

    @Pointcut(" execution(* com.fly.service..*(..))")
    public void dynamicDs() {

    }

    @Before("dynamicDs()")
    public void before(JoinPoint joinPoint) {
        DynamicDataSource dataSource = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), DynamicDataSource.class);
        if (dataSource == null) {
            log.error("未配置数据源");
            throw new RuntimeException("请选择需要使用的数据源");
        }
        log.info("数据源:" + dataSource.type());
        DatabaseContextHolder.setDatabaseType(dataSource.type());
    }

    @After("dynamicDs()")
    public void after() {
        log.info("清除数据源：" + DatabaseContextHolder.getDatabaseType());
        DatabaseContextHolder.clearDatabase();
    }
}
