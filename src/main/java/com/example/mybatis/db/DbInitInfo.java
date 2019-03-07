package com.example.mybatis.db;

import java.util.ArrayList;
import java.util.List;

public class DbInitInfo {
	public  static List<DbBean> beans = null;
	static{
		beans = new ArrayList<DbBean>();
		// 这里数据 可以从xml 等配置文件进行获取
		// 为了测试，这里我直接写死
		DbBean beanOracle = new DbBean();
		beanOracle.setDriverName("com.mysql.jdbc.Driver");
		beanOracle.setUrl("jdbc:mysql://mysql.test.masterscm.com:33306/sta_worker?autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=false&characterEncoding=utf-8&useAffectedRows=true&allowMultiQueries=true");
		beanOracle.setUserName("sta_worker");
		beanOracle.setPassword("m123456");
		
		beanOracle.setMinConnections(5);
		beanOracle.setMaxConnections(100);
		beanOracle.setPoolName("testPool");
		beans.add(beanOracle);
	}
}