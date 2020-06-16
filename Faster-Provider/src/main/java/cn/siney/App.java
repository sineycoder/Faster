package cn.siney;


import cn.siney.local.LocalRegistry;
import cn.siney.server.FasterServer;
import cn.siney.service.Service;
import cn.siney.service.impl.ServiceImpl;

public class App {
    public static void main(String[] args) {
        LocalRegistry.getInstance().putInterface(Service.class.getName(), ServiceImpl.class);
        new FasterServer(8888).run();
    }
}
