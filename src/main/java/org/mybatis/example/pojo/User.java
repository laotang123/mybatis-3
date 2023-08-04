package org.mybatis.example.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer id;
    private Integer c;
    private Integer d;

}
