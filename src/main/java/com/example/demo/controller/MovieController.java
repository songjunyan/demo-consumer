package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.remote.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 * @author sls
 * @date 2018/1/3
 */
@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    protected DiscoveryClient discoveryClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("user/{id}")
    public User findById(@PathVariable Long id){
        return restTemplate.getForObject("http://localhost:9000/user/"+id,User.class);
    }
    @GetMapping("remoteuser/{id}")
    public User findRemoteById(@PathVariable Long id){
        return userFeignClient.findById(id);
    }
    @GetMapping("userinstance")
    public List<ServiceInstance> showInfo(){
        return discoveryClient.getInstances("demo-provider");
    }

    @GetMapping("loadinstance")
    public String loadInstance(){
        ServiceInstance choose = loadBalancerClient.choose("demo-provider");
        return choose.getServiceId()+"&"+choose.getHost()+"&"+choose.getPort();
    }
}
