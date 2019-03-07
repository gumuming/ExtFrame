package com.example.mybatis.ibatis;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Builder
@Table(name = "t_siting_task_Id")
@Entity
@Data
@ToString
public class SitingTaskId {

    @Id
    //@KeySql(genId = UUIdGenId.class)
    @Column(name = "id")
    private String id;

    /**
     * 创建时间
     */
    @Column(name = "create_at", insertable = false, updatable = false)
    private Date createAt;

    /**
     * 更新时间
     */
    @Column(name = "update_at", insertable = false, updatable = false)
    private Date updateAt;

    @Column(name = "create_by")
    private String createBy = "system";

    @Column(name = "update_by")
    private String updateBy = "system";

    @Column(name = "c_task_id")
    private String taskId;

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "c_task_type")
    private String taskType;


}
