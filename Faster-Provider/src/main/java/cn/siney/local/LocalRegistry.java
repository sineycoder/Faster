package cn.siney.local;

import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {

    private ConcurrentHashMap<String, Class<?>> registry = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singleObjects = new ConcurrentHashMap<>();
    private static LocalRegistry instance = new LocalRegistry();

    private LocalRegistry(){}

    public static LocalRegistry getInstance(){
        return instance;
    }

    public void putInterface(String interfaceName, Class<?> clazz){
        registry.put(interfaceName, clazz);
    }

    public Class<?> getInterface(String interfaceName){
        return registry.get(interfaceName);
    }

    public void putSingleObject(String interfaceName, Object object){
        singleObjects.put(interfaceName, object);
    }

    public Object getSingleton(String interfaceName){
        return singleObjects.get(interfaceName);
    }



}
