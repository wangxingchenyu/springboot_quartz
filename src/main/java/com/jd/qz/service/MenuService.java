package com.jd.qz.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jd.qz.mapper.MenuMapper;
/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 13:57
 * @Version 1.0
 */
@Service
public class MenuService{

    @Resource
    private MenuMapper menuMapper;

}
