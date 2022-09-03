package com.asuna.store.controller;

import com.asuna.store.entity.User;
import com.asuna.store.service.IUserService;
import com.asuna.store.service.ex.InsertException;
import com.asuna.store.service.ex.UsernameDuplicatedException;
import com.asuna.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@Controller
@RestController
@RequestMapping(value = "users")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "reg")
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user) {
//        //创建响应结果对象即JsonResult对象
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            //调用userService的reg方法时可能出现异常,所以需要捕获异常
//            userService.reg(user);
//            result.setState(200);
//            result.setMessage("用户注册成功");
//        } catch (UsernameDuplicatedException e) {
//            result.setState(4000);
//            result.setMessage("用户名被占用");
//        } catch (InsertException e) {
//            result.setState(5000);
//            result.setMessage("注册时产生未知的异常");
//        }
//        return result;

        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping(value = "login")
    public JsonResult<User> login(User user, HttpSession session){

        User login = userService.login(user);

        //向session对象中完成数据的绑定(这个session是全局的,项目的任何位置都可以访问)
        session.setAttribute("uid",login.getUid());
        session.setAttribute("username",login.getUsername());

        //测试能否正常获取session中存储的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK, login);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }


    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){

        Integer uid = getUidFromSession(session);
        User result = userService.getByUid(uid);
        return new JsonResult<>(OK, result);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,
                                       HttpSession session){
        Integer uid = getUidFromSession(session);
        userService.changeInfo(uid, user);
        return new JsonResult<>(OK);
    }
}
