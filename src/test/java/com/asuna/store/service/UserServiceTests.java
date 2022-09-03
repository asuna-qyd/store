package com.asuna.store.service;

import com.asuna.store.entity.User;
import com.asuna.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    @Test
    public void reg() {
        /**
         * 进行插入时可能会出错抛出异常,这时需要捕获异常:
         * 1.选中    User user = new User();
         *           user.setUsername("张7");
         *           user.setPassword("123456");
         *           userService.reg(user);
         *           System.out.println("OK");
         * 2.点击导航栏的Code,然后依次点击SurroundWith->try/catch就可以捕获异常了
         * 3.Exception e没有问题,但这里我们知道是Service层的异常,所以可以改为ServiceException e
         * 4.System.out.println(e.getClass().getSimpleName());获取异常对象再获取类的名称然后输出
         * 5.System.out.println(e.getMessage());输出异常信息(是自己在ServiceException的子类类具体设置的信息)
         */
        try {
            User user = new User();
            user.setUsername("张7");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        //因为login方法可能抛出异常,所以应该捕获异常,但是测试时没必要写那么严谨
        User user1 = new User();
        user1.setUsername("张7");
        user1.setPassword("123456");
        User user = userService.login(user1);
        System.out.println(user);
    }

    @Test
    public void changePassword() {

        userService.changePassword(3, "管理员", "123" ,"123456");
    }

    @Test
    public void getByUid(){

        User result = userService.getByUid(3);
        System.out.println(result);
    }

    @Test
    public void changeInfo(){

        User user = new User();
        user.setPhone("123465");
        user.setEmail("11111@qq.com");
        user.setModifiedUser("管理员");
        user.setModifiedTime(new Date());
        userService.changeInfo(3, user);

    }

}
