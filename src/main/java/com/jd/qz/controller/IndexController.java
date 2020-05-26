package com.jd.qz.controller;

import com.jd.qz.service.JobService;
import com.jd.qz.service.MyJobService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	MyJobService myJobService;

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@GetMapping(value = {"/quartz"})
	public String index(@RequestParam(name = "cron", required = false) String cron) {
		myJobService.doJob(cron);
		return "quartz";
	}


	@GetMapping(value = {"/change_quartz"})
	public String change(@RequestParam(name = "cron", required = false) String cron) {
		myJobService.modifyJobTime(cron);
		return "quartz";
	}


	@GetMapping("/pause")
	public String pause() {
		jobService.pauseJob();
		return "pause";
	}

	@GetMapping("/restart")
	public String restart() throws SchedulerException {
		jobService.restartJob();
		return "restart";
	}

}
