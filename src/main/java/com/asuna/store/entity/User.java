package com.asuna.store.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity implements Serializable {

    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;

}
