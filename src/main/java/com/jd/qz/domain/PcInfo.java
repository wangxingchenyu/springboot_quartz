package com.jd.qz.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 13:57
 * @Version 1.0
 */
@Data
@Table(name = "pc_info")
public class PcInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "system_producer")
    private String systemProducer;

    @Column(name = "system_type")
    private String systemType;

    @Column(name = "system_processer")
    private String systemProcesser;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "system_version")
    private String systemVersion;

    @Column(name = "physical_memory")
    private String physicalMemory;

    @Column(name = "mac_addr")
    private String macAddr;

    @Column(name = "c_area")
    private Integer cArea;

    @Column(name = "run_time")
    private Date runTime;

    @Column(name = "register_user")
    private String registerUser;

    /**
     * 0
     */
    @Column(name = "dongdong_dir_size")
    private Integer dongdongDirSize;

    @Column(name = "all_c_space")
    private String allCSpace;

    @Column(name = "ost_file_size")
    private Integer ostFileSize;

    @Column(name = "pre_login_time")
    private String preLoginTime;

    @Column(name = "disks")
    private String disks;

    @Column(name = "ip")
    private String ip;

    @Column(name = "c_used_rate")
    private Integer cUsedRate;

    @Column(name = "wulineicun")
    private String wulineicun;

    @Column(name = "sn")
    private String sn;

    @Column(name = "valiable_memory")
    private String valiableMemory;

    @Column(name = "start_time")
    private String startTime;
}