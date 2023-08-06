package org.mybatis.example.demo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.example.mapper.UserMapper;
import org.mybatis.example.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * liujf
 * 基本demo，测试数据库能联通，能执行查询语句
 */
public class SimpleSqlSession {
  public static void main(String[] args) throws IOException {
    // 1.获取配置文件
    InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
    // 2.加载解析配置文件并获取SqlSessionFactory对象
    // SqlSessionFactory 的实例我们没有通过 DefaultSqlSessionFactory直接来获取
    // 而是通过一个Builder对象来建造的
    // SqlSessionFactory 生产 SqlSession 对象的  SqlSessionFactory 应该是单例
    // 全局配置文件和映射文件 也只需要在 系统启动的时候完成加载操作
    // 通过建造者模式来 构建复杂的对象  1.完成配置文件的加载解析  2.完成SqlSessionFactory的创建
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
    // 3.根据SqlSessionFactory对象获取SqlSession对象
    SqlSession sqlSession = factory.openSession();
    // 4.通过SqlSession中提供的 API方法来操作数据库
    // List<User> list = sqlSession.selectList("com.boge.mapper.UserMapper.selectUserList");
    // 获取接口的代码对象  得到的其实是 通过JDBC代理模式获取的一个代理对象
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//    PageHelper.startPage(1,2);
    List<User> list = mapper.selectUserList();
    System.out.println("list.size() = " + list.size());
    System.out.println("-------------");
    System.out.println("list.size() = " + list.size());
    // 5.关闭会话
    sqlSession.close(); // 关闭session  清空一级缓存
  }
}
