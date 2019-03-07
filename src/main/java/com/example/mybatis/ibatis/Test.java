package com.example.mybatis.ibatis;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) {
        try {
            SitingTaskIdMapper mapper = SqlSession.getMapper(SitingTaskIdMapper.class);
            SitingTaskId bean = mapper.selectSitingById("402880ec66fbc4d90166fbc5299c00d5", "PT97", "2c920a8f67f8f2b10167fda397800033");
            System.out.println(bean);
        } catch (Exception e) {
            log.info("exception:{}",e.getMessage());
        }
    }
}
