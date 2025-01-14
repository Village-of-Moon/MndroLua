package com.axml2xml.chunk;

import com.axml2xml.chunk.base.BaseContentChunk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Sens on 2021/8/27.
 */
public class EndTagChunk extends BaseContentChunk {
    public final int namespaceUri;
    public final int name;

    public EndTagChunk(ByteBuffer byteBuffer, StringChunk stringChunk) {
        super(byteBuffer, stringChunk);
        namespaceUri = byteBuffer.getInt();
        name = byteBuffer.getInt();

        byteBuffer.position(ChunkStartPosition + chunkSize);
    }

    @Override
    protected void toBytes(ByteArrayOutputStream stream) throws IOException {
        super.toBytes(stream);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(this.namespaceUri);
        byteBuffer.putInt(this.name);
        stream.write(byteBuffer.array());
    }

    @Override
    public String toString() {
        return "</" + getString(name) + ">\n";
    }
}
