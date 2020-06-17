package cn.siney.consumer.handler;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 当接收到数据的时候，在这里进行处理
 */
public class FasterReceiveHandler {

    private static FasterReceiveHandler instance = new FasterReceiveHandler();

    private static final int MAX_RETRIES = 10;

    AtomicReference<Object> data = new AtomicReference<>();

    private FasterReceiveHandler(){}

    public static FasterReceiveHandler getInstance(){
        return instance;
    }

    public void cas(Object o){
        int retry = 0;
        while (!data.compareAndSet(null, o)) {
            if (++retry > MAX_RETRIES) {
                retry = 0;
                Thread.yield();
            }
        }
    }

    public Object get() {
        LockSupport.park();
        Object o = data.get();
        data.set(null);//相当于解锁
        return o;
    }
}
