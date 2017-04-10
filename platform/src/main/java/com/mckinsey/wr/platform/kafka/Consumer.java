package com.mckinsey.wr.platform.kafka;

/**
 * Created by deepakc on 10/04/17.
 */
public interface Consumer<T> {

    void consume(T t);

}
