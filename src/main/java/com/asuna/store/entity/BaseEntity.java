package com.asuna.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseEntity implements Serializable {
    private String createUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}
