package cn.siney.protocol;

import java.util.Arrays;

public class Protocol {

    private int len;//字节长度
    private byte[] content;//字节内容

    @Override
    public String toString() {
        return "Protocol{" +
                "len=" + len +
                ", content=" + Arrays.toString(content) +
                '}';
    }

    public Protocol(int len, byte[] content) {
        this.len = len;
        this.content = content;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
