package cn.siney.service.impl;

import cn.siney.provider.annotation.FasterService;
import cn.siney.service.ServiceTwo;

/**
 * @Author siney
 * @Date 2020/6/17 14:13
 * @Version 1.0
 */
@FasterService
public class ServiceTwoImpl implements ServiceTwo {
    @Override
    public String sayHi(String name) {
        return "Hi " + name;
    }
}
