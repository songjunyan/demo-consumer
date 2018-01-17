package com.example.demo.remote;

import com.example.demo.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author sls
 * @date 2018/1/17
 */
@FeignClient(name="demo-provider")
public interface UserFeignClient {
    //要求写规范一点
    @RequestMapping(value = "user/{id}",method= RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);
}