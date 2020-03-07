package com.jd.qz.service;

import com.jd.qz.mapper.PcInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 13:57
 * @Version 1.0
 */
@Service
public class PcInfoService{

    @Resource
    private PcInfoMapper pcInfoMapper;

}
