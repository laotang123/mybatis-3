package org.mybatis.example.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Locale;
import java.util.Properties;

/**
 * @author: ljf
 * @date: 2021/9/9 17:17
 * @description: 限制sql都只能至多返回1000行记录
 * 该插件修改运行参数
 * @modified By:
 * @version: $ 1.0
 */
//Signature来决定拦截器作用在那些对象上，粒度到指定方法上
@Intercepts(@Signature(
        type = StatementHandler.class, //确定要拦截的对象
        method = "prepare",//拦截的方法
        args = {Connection.class, Integer.class} //拦截方法对应的参数
))
public class LimitResultPlugin implements Interceptor {
    Properties props = null;

    //默认限制查询返回行
    private int limit;

    //数据库类型
    private String dbType;

    //限制表中间别名，避免重复
    private static final String LMT_TABLE_NAME = "limit_table_name_xxx";

    /**
     * 代替拦截对象方法的内容
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.err.println("intercept before...");
        //这里的target是RoutingStatementHandler，它的属性delegate 是实际的jdbc statement
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

        //多个拦截器会形成多次代理，循环分离出最原始的目标类
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }

        //找到查询的sql，进行修改。目标类为RoutingStatementHandler.delegate.boundSql.sql
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

        //数据库类型为mysql，且sql没有被插件重写过，且为查询语句
        if (sql != null && "mysql".equals(this.dbType) && !sql.contains(LMT_TABLE_NAME) && sql.toLowerCase().trim().indexOf("select") == 0) {
            //通过重写sql实现，这里起一个奇怪的表名，避免与表重复
            sql = "select * from (" + sql.trim() + ") " + LMT_TABLE_NAME + " limit " + limit;
            metaStatementHandler.setValue("delegate.boundSql.sql", sql);
        }
        final Object obj = invocation.proceed();
        System.err.println("intercept after...");
        return obj;
    }

    @Override
    public Object plugin(Object target) {
        System.err.println("动态代理生成对象");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.err.println(this.getClass().getName() + " properties: " + properties);
        this.limit = Integer.parseInt(properties.getProperty("limit", "50"));
        this.dbType = properties.getProperty("dbType", "mysql");
        this.props = properties;
    }
}
