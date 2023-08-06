package org.mybatis.example.plugins;

import org.apache.ibatis.plugin.*;

import java.util.Arrays;

/**
 * @author: ljf
 * @date: 2021/9/9 13:38
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class PluginDemo {
    public interface ParentDemo {
        void sayHello(String world);

        void sayHi();
    }

    static class Demo implements ParentDemo {
        public void sayHello(String world) {
            System.out.println("hello " + world);
        }

        public void sayHi() {
            System.out.println("hi");
        }
    }


    @Intercepts({@Signature(type = ParentDemo.class, method = "sayHello", args = String.class)})
    static class MyInterceptor implements Interceptor {

        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            System.out.println("拦截器拦截，方法名：" + invocation.getMethod());
            return invocation.proceed();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        Object wrap = Plugin.wrap(demo, new MyInterceptor());
        System.out.println(wrap);
        System.out.println(Arrays.toString(wrap.getClass().getInterfaces()));
        ((ParentDemo) wrap).sayHello("world");
    }
}
