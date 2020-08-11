package com.francis.cors.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: francis
 * @description:
 * @date: 2020/8/11 22:05
 */
@RestController
//@CrossOrigin
public class CorsController {

    @GetMapping("/cors")
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>(4);
        map.put("code", "0");
        map.put("msg", "success");
        return map;
    }

}
