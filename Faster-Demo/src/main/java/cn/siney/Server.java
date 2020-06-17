package cn.siney;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author siney
 * @Date 2020/6/17 14:37
 * @Version 1.0
 */
public class Server {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(Config.class);
        ac.refresh();
    }
}
