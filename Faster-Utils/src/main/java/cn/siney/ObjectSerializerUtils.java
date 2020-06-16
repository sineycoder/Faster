package cn.siney;

import java.io.*;

public class ObjectSerializerUtils {

    public static byte[] serialize(Object object){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(oos != null)
                    oos.close();
            }catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static Object unSerialize(byte[] bytes){
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            if(o != null){
                return o;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
