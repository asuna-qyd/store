package com.asuna.store.service.impl;

import com.asuna.store.entity.User;
import com.asuna.store.mapper.UserMapper;
import com.asuna.store.service.IUserService;
import com.asuna.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 用户模块业务层的实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用findByUsername(username)判断用户是否被注册过
        User byUsername = userMapper.findByUsername(username);
        //判断结果集是否为null,不等于null则抛出用户名被占用的异常
        if (byUsername != null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        /*
          密码加密处理作用:
          1.后端不再能直接看到用户的密码2.忽略了密码原来的强度,提升了数据安全性
          密码加密处理的实现:
          串+password+串->交给md5算法连续加密三次
          串就是数据库字段中的盐值,是一个随机字符串
         */

        String password = user.getPassword();
        //1.随机生成一个盐值(大写的随机字符串)
        String salt = java.util.UUID.randomUUID().toString().toUpperCase();
        //2.将密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(password, salt);
        //3.将盐值保存到数据库
        user.setSalt(salt);
        //4.将加密之后的密码重新补全设置到user对象当中
        user.setPassword(md5Password);


        //补全数据:is_delete 设置为 0
        user.setIsDelete(0);
        //补全数据:四个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();//java.util.Date
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 执行注册业务功能的实现(rows == 1)
        Integer rows = userMapper.insertUser(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }


    @Override
    public User login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User result = userMapper.findByUsername(username);

        if (result == null) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        /*
          检测用户的密码是否匹配:
          1.先获取数据库中加密之后的密码
          2.和用户传递过来的密码进行比较
           2.1先获取盐值
           2.2将获取的用户密码按照相同的md5算法加密
         */
        String oldPassword = result.getPassword();
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(password, salt);
        if (!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断is_delete字段的值是否为1,为1表示被标记为删除
        if (result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //方法login返回的用户数据是为了辅助其他页面做数据展示使用(只会用到uid,username,avatar)
        //所以可以new一个新的user只赋这三个变量的值,这样使层与层之间传输时数据体量变小,后台层与
        // 层之间传输时数据量越小性能越高,前端也是的,数据量小了前端响应速度就变快了
        User user1 = new User();
        user1.setUid(result.getUid());
        user1.setUsername(result.getUsername());
        user1.setAvatar(result.getAvatar());
        return user1;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {

        User result = userMapper.findByUid(uid);

        if (result == null || result.getIsDelete() == 1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        // 原始密码和数据库中的密码进行比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());

        if (!Objects.equals(result.getPassword(), oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        // 将新密码设置到数据库中，将新密码进行加密再进行更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {

        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        return result;
    }

    @Override
    public void changeInfo(Integer uid, User user) {

        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UsernameNotFoundException("用户数据不存在");
        }

        user.setUid(uid);
        user.setModifiedUser(user.getModifiedUser());
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new UpdateException("更新数据时产生异常");
        }
    }

    //密码MD5加密
    private String getMD5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;

    }
}
