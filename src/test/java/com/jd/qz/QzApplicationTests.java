package com.jd.qz;

import com.jd.qz.domain.Menu;
import com.jd.qz.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QzApplicationTests {

    @Autowired
    MenuMapper menuMapper;


    @Test
    void contextLoads() {
        List<Menu> menus = menuMapper.selectAll();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }

}
