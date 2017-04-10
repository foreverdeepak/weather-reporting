package com.mckinsey.wr.platform;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Created by deepakc on 10/04/17.
 */
public class KryoSerializer<T> implements Serializer<T> {

    private Kryo kryo;
    private Class<T> type;

    public KryoSerializer(Class<T> type) {
        kryo = new Kryo();
        this.type = type;
        kryo.register(type);
    }

    @Override
    public byte[] serialize(T o) {
        Output output = new Output();
        kryo.writeClassAndObject(output, o);
        return output.toBytes();
    }

    @Override
    public T deserialize(byte[] bytes) {
        return kryo.readObject(new Input(bytes), type);
    }
}
