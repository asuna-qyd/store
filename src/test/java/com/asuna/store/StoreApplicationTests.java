package com.asuna.store;

import com.asuna.store.entity.User;
import com.asuna.store.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {

    @Autowired
    private DataSource dataSource;



    @Test
    void contextLoads() {
        User user = new User();

    }

    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
