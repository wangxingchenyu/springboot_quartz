package com.jd.qz.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 13:57
 * @Version 1.0
 */
@Data
@Table(name = "menu")
@ToString
public class Menu {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * url名称
     */
    @Column(name = "url_name")
    private String urlName;

    /**
     * 前端路由
     */
    @Column(name = "frontEndRouter")
    private String frontendrouter;

    /**
     * 父节点id
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * 路由导航
     */
    @Column(name = "RouteGuidance")
    private String routeguidance;

    /**
     * 导航icon
     */
    @Column(name = "menuIncon")
    private String menuincon;
}