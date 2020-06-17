package cn.siney.service.impl;

import cn.siney.provider.annotation.FasterService;
import cn.siney.service.ServiceThree;

/**
 * @author siney
 * @createTime 2020-06-17
 **/
@FasterService
public class ServiceThreeImpl implements ServiceThree {
    @Override
    public String order(int id) {
        return "select id = " + id + ", result = OK";
    }
}
