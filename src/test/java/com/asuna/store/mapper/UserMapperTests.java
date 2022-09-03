package com.asuna.store.mapper;

import com.asuna.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
//@RunWith:启动这个单元测试类,需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)

public class UserMapperTests {

    //idea有检测的功能，接口是不能够直接创建Bean的(动态代理技术来解决)
    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试方法可以独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰必须是public
     */

    @Test
    public void insert(){

        User user = new User();
        user.setUsername("Asuna");
        user.setPassword("123456");
        Integer integer = userMapper.insertUser(user);
        if (integer > 0){
            System.out.println("插入数据成功");
        }else {
            System.out.println("插入数据失败");
        }
    }

    @Test
    public void select(){
        User user = userMapper.findByUsername("asuna");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){

        Integer result = userMapper.updatePasswordByUid(2, "123", "系统管理员", new Date());
        if (result > 0){
            System.out.println("密码修改成功");
        }else {
            System.out.println("密码修改失败");
        }
    }

    @Test
    public void findByUid(){

        User result = userMapper.findByUid(2);
        System.out.println(result);
    }

    @Test
    public void updateInfoByUid(){

        User user = new User();
        user.setUsername("亚丝娜");
        user.setUid(3);
        user.setPhone("123456789");
        user.setEmail("112121245@qq.com");
        user.setGender(1);
        Integer result = userMapper.updateInfoByUid(user);
        if (result > 0){
            System.out.println("个人资料修改成功");
        }else {
            System.out.println("个人资料修改失败");
        }
    }

}
