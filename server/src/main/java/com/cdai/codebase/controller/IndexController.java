package com.cdai.codebase.controller;

import org.eclipse.jetty.server.Dispatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 */
@Controller
public class IndexController {

    /*@RequestMapping("/index")
    @ResponseBody
    String index() {
        return "Welcome!!!";
    }

    @RequestMapping("/hello")
    String hello(Map<String, Object> model) {
        return "helloworld.html";
    }*/

    @RequestMapping("/small")
    String smallObject(Map<String, Object> model) {
        return "workload/smallobject";
    }

    @RequestMapping("/small8")
    String smallObject8(Map<String, Object> model) {
        return "workload/smallobject8";
    }

    @RequestMapping("/small16")
    String smallObject16(Map<String, Object> model) {
        return "workload/smallobject16";
    }

    @RequestMapping("/small32")
    String smallObject32(Map<String, Object> model) {
        return "workload/smallobject32";
    }

    @RequestMapping("/big")
    String bigObject(Map<String, Object> model) {
        return "workload/bigobject";
    }

    @RequestMapping("/big2")
    String bigObject2(Map<String, Object> model) {
        return "workload/bigobject2";
    }

    @RequestMapping("/big3")
    String bigObject3(Map<String, Object> model) {
        return "workload/bigobject3";
    }
}
