package com.jd.qz.controller;

import com.jd.qz.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 14:50
 * @Version 1.0
 */
@RestController
public class IndexController {

    @Autowired
    JobService jobService;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/quartz",method = RequestMethod.GET)
    public String index() {
        jobService.executeQuartzTask();
        logger.warn("xxxx");
        return "quartz";
    }

}
