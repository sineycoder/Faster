package cn.siney.service.impl;

import cn.siney.service.Service;

public class ServiceImpl implements Service {
    @Override
    public String sayHello(String name) {
        return "Hello my friend " + name;
    }
}
