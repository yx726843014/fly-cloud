package com.fly.service;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.fly.task.GenerateTask;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

/**
 * youxiong
 */
@Service
public class GenerateService {

    private GlobalConfig globalConfig;

    private DataSourceConfig dataSourceConfig;

    private StrategyConfig strategyConfig;

    private PackageConfig packageConfig;

    public void init(GenerateTask generateTask) throws SQLException {
        globalConfig = new GlobalConfig();
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(false);
        // XML 二级缓存
        globalConfig.setEnableCache(false);
        // XML ResultMap
        globalConfig.setBaseResultMap(true);
        // XML columList
        globalConfig.setBaseColumnList(true);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");

        // 数据源配置
        dataSourceConfig = new DataSourceConfig();
        // mysql数据库
        if (DbType.MYSQL.getValue().equals(generateTask.getDbType())) {
            dataSourceConfig.setDbType(DbType.MYSQL);
            dataSourceConfig.setTypeConvert(new MySqlTypeConvert() {
                // 自定义数据库表字段类型转换【可选】
                @Override
                public DbColumnType processTypeConvert(String fieldType) {
                    System.out.println("转换类型：" + fieldType);
                    // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                    return super.processTypeConvert(fieldType);
                }
            });
            dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        }
        dataSourceConfig.setUsername(generateTask.getUsername());
        dataSourceConfig.setPassword(generateTask.getPassword());
        dataSourceConfig.setUrl(generateTask.getSqlUrl());

        // 策略配置
        strategyConfig = new StrategyConfig();
        // 表名生成策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        // 自定义 service 父类
        //strategyConfig.setSuperServiceClass("com.honvay.cola.cloud.framework.base.service.BaseService");
        // 自定义 service 实现类父类
        //strategyConfig.setSuperServiceImplClass("com.honvay.cola.cloud.framework.base.service.impl.BaseServiceImpl");
        // 自定义 controller 父类
        //strategyConfig.setSuperControllerClass("com.honvay.cola.cloud.framework.base.controller.BaseController");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityBuilderModel(true);

        packageConfig = new PackageConfig();
        packageConfig.setController("controller");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");

    }


    /**
     * 生成代码
     *
     * @param generateTask
     */
    public void generate(GenerateTask generateTask) throws SQLException, ClassNotFoundException {
        init(generateTask);
        AutoGenerator autoGenerator = new AutoGenerator();
        //只生成实体类
        if (generateTask.getOnlyGenerateEntity() != null && generateTask.getOnlyGenerateEntity()) {
            TemplateConfig templateConfig = new TemplateConfig();
            templateConfig.setXml(null);
            templateConfig.setController(null);
            templateConfig.setMapper(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
            autoGenerator.setTemplate(templateConfig);
        } else {
            // 关闭默认 xml 生成，调整生成 至 根目录
            TemplateConfig templateConfig = new TemplateConfig();
            //templateConfig.setXml(null);
            autoGenerator.setTemplate(templateConfig);
        }

        // 全局配置
        globalConfig.setOutputDir(generateTask.getProjectLocation() + "/src/main/java");
        globalConfig.setAuthor(generateTask.getDeveloper());
        // 此处可以修改为您的表前缀
        strategyConfig.setTablePrefix(new String[]{generateTask.getTablePrefix()});
        // 配置表
        if (generateTask.getTableName().contains("*")) {
            if (StringUtils.isNotBlank(generateTask.getFilterName())) {
                strategyConfig.setExclude(generateTask.getFilterName().split(","));
            }
        } else {
            int j = 0;
            String[] tableNames = generateTask.getTableName().split(",");
            String[] newTableNames = new String[tableNames.length];
            for (String tableName : tableNames) {
                newTableNames[j] = tableName;
                j++;
            }
            strategyConfig.setInclude(newTableNames);
        }
        // 包配置
        packageConfig.setParent(generateTask.getPackageName());
        packageConfig.setModuleName(generateTask.getModule());
        // 执行生成
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.execute();
    }


}
