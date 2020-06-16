package cn.siney;


import cn.siney.local.LocalRegistry;
import cn.siney.server.FasterServer;
import cn.siney.service.Service;
import cn.siney.service.impl.ServiceImpl;
import cn.siney.spring.config.FasterConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(FasterConfig.class);
        ac.refresh();
//        LocalRegistry.getInstance().putInterface(Service.class.getName(), ServiceImpl.class);
//        new FasterServer(8888).run();
    }
}
