package com.axml2xml.chunk;

import com.axml2xml.chunk.base.BaseContentChunk;
import android.util.TypedValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sens on 2021/8/27.
 */
public class StartTagChunk extends BaseContentChunk {
    public final int namespaceUri;
    public final int name;

    public short attributeStart;
    public short attributeSize;
    public short attributeCount;
    public short idIndex;
    public short classIndex;
    public short styleIndex;

    public List<Attribute> attributes;

    public StartTagChunk(ByteBuffer byteBuffer, StringChunk stringChunk, List<NamespaceChunk> namespaceChunkList) {
        super(byteBuffer, stringChunk);
        namespaceUri = byteBuffer.getInt();
        name = byteBuffer.getInt();
        attributeStart = byteBuffer.getShort();
        attributeSize = byteBuffer.getShort();
        attributeCount = byteBuffer.getShort();
        idIndex = byteBuffer.getShort();
        classIndex = byteBuffer.getShort();
        styleIndex = byteBuffer.getShort();

        attributes = new ArrayList<>(attributeCount);
        for (int i = 0; i < attributeCount; i++)
            attributes.add(new Attribute(byteBuffer));

        this.namespaceChunkList = namespaceChunkList;
        byteBuffer.position(ChunkStartPosition + chunkSize);
    }

    public static class Attribute {
        public final int namespaceUri;
        public final int name;
        public final int value;
        public final short structureSize;
        public final int Res0;
        public final int type;
        public final int data;

        public Attribute(ByteBuffer byteBuffer) {
            namespaceUri = byteBuffer.getInt();
            name = byteBuffer.getInt();
            value = byteBuffer.getInt();
            structureSize = byteBuffer.getShort();
            Res0 = byteBuffer.get() & 0xFF;
            type = byteBuffer.get() & 0xFF;
            data = byteBuffer.getInt();
        }

        protected void toBytes(ByteArrayOutputStream stream) throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.allocate(5 * 4);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            byteBuffer.putInt(namespaceUri);
            byteBuffer.putInt(name);
            byteBuffer.putInt(value);
            byteBuffer.putShort(structureSize);
            byteBuffer.put((byte) Res0);
            byteBuffer.put((byte) type);
            byteBuffer.putInt(data);
            stream.write(byteBuffer.array());
        }
    }

    @Override
    protected void toBytes(ByteArrayOutputStream stream) throws IOException {
        super.toBytes(stream);
        this.attributeCount = (short) attributes.size();
        ByteBuffer byteBuffer = ByteBuffer.allocate(5 * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(namespaceUri);
        byteBuffer.putInt(name);
        byteBuffer.putShort(attributeStart);
        byteBuffer.putShort(attributeSize);
        byteBuffer.putShort(attributeCount);
        byteBuffer.putShort(idIndex);
        byteBuffer.putShort(classIndex);
        byteBuffer.putShort(styleIndex);
        stream.write(byteBuffer.array());
        for (Attribute attribute : attributes)
            attribute.toBytes(stream);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected final List<NamespaceChunk> namespaceChunkList;

    protected String getPrefix(int uri) {
        for (NamespaceChunk namespaceChunk : namespaceChunkList)
            if (namespaceChunk.uri == uri)
                return getString(namespaceChunk.prefix);
        return null;
    }

    protected String getNameSpace() {
        StringBuilder nsBuilder = new StringBuilder();
        if (namespaceChunkList != null)//add namespace
            for (NamespaceChunk namespaceChunk : namespaceChunkList)
                nsBuilder.append(" ").append(namespaceChunk.getXmlNameSpace());
        return nsBuilder.toString();
    }

    private boolean addxmlns = false;

    public void addXmlns() {
        addxmlns = true;
    }

    @Override
    public String toString() {
        StringBuilder tagBuilder = new StringBuilder();
        if (comment > -1) tagBuilder.append("<!--").append(getString(comment)).append("-->").append("\n");
        tagBuilder.append('<');

        String tagName = getString(name);
        tagBuilder.append(tagName).append('\n');

        if (addxmlns) {
            tagBuilder.append(getNameSpace()).append('\n');
        }

        for (Attribute attribute : attributes) {
            tagBuilder.append(" ");
            if (attribute.namespaceUri != -1) tagBuilder.append(getPrefix(attribute.namespaceUri)).append(":");
            String data = TypedValue.coerceToString(attribute.type, attribute.data);
            tagBuilder.append(getString(attribute.name))
                    .append("=")
                    .append('"')
                    .append((data==null||data.isEmpty()) ? getString(attribute.value) : data)
                    .append('"').append('\n');
        }
        if(tagBuilder.charAt(tagBuilder.length()-1)=='\n'){
            tagBuilder = tagBuilder.deleteCharAt(tagBuilder.length()-1);
        }
        return tagBuilder.append(">\n").toString();
    }
}
