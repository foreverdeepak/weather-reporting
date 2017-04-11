package com.deepak.wr.platform;

/**
 * Created by deepakc on 10/04/17.
 */
public interface Serializer<T> {

    byte[] serialize(T o);

    T deserialize(byte[] bytes);
}
