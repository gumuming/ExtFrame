package com.example.mybatis.db;

import lombok.Data;

@Data
public class DbBean {
    // 连接池属性
    private String driverName;
    private String url;
    private String userName;
    private String password;
    // 连接池名字
    private String poolName;
    private int minConnections = 1; // 空闲池，最小连接数
    private int maxConnections = 10; // 空闲池，最大连接数

    private int initConnections = 5;// 初始化连接数

    private long connTimeOut = 1000;// 重复获得连接的频率

    private int maxActiveConnections = 100;// 最大允许的连接数，和数据库对应

    private long connectionTimeOut = 1000 * 60 * 20;// 连接超时时间，默认20分钟

    private boolean isCurrentConnection = true; // 是否获得当前连接，默认true

    private boolean isCheckPool = true; // 是否定时检查连接池
    private long lazyCheck = 1000 * 60 * 60;// 延迟多少时间后开始 检查
    private long periodCheck = 1000 * 60 * 60; // 检查频率

}
