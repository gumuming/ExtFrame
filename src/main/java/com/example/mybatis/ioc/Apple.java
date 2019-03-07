package com.example.mybatis.ioc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ExtService
public class Apple {

    private String color = "testIoc";

    private int weight = 100;
}
