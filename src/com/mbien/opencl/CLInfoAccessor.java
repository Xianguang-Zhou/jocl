package com.mbien.opencl;

import com.mbien.opencl.util.CLUtil;
import com.sun.gluegen.runtime.PointerBuffer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.mbien.opencl.CLException.*;

/**
 * Internal utility for common OpenCL clGetFooInfo calls.
 * Threadsafe.
 * @author Michael Bien
 */
abstract class CLInfoAccessor {

    protected final static ThreadLocal<ByteBuffer> localBB = new ThreadLocal<ByteBuffer>() {

        @Override
        protected ByteBuffer initialValue() {
            return ByteBuffer.allocateDirect(512).order(ByteOrder.nativeOrder());
        }

    };
    protected final static ThreadLocal<PointerBuffer> localPB = new ThreadLocal<PointerBuffer>() {

        @Override
        protected PointerBuffer initialValue() {
            return PointerBuffer.allocateDirect(1);
        }

    };

    public final long getLong(int key) {

        ByteBuffer buffer = localBB.get();
        int ret = getInfo(key, 8, buffer, null);
        checkForError(ret, "error while asking for info value");

        return buffer.getLong(0);
    }

    public final String getString(int key) {
        
        ByteBuffer buffer = localBB.get();
        PointerBuffer pbuffer = localPB.get();
        int ret = getInfo(key, buffer.capacity(), buffer, pbuffer);
        checkForError(ret, "error while asking for info string");

        int clSize = (int)pbuffer.get(0);
        byte[] array = new byte[clSize-1]; // last char is always null
        buffer.get(array).rewind();

        return CLUtil.clString2JavaString(array, clSize);

    }

    protected abstract int getInfo(int name, long valueSize, Buffer value, PointerBuffer valueSizeRet);


}
