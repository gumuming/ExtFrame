package com.example.mybatis.ibatis;


import java.util.List;

public interface SitingTaskIdMapper  {

     @ExtSelect("select * from t_task_id where c_userId = ? and create_by = ? and update_by = ?")
    SitingTaskId selectSitingById(@ExtParam("userId") String userId, @ExtParam("sysNo") String sysNo,
                                                 @ExtParam("projectId") String projectId);

}
