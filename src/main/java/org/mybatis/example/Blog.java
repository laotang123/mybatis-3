package org.mybatis.example;

/**
 * @author: ljf
 * @date: 2021/9/3 23:21
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class Blog {
    public int id;
    public String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
