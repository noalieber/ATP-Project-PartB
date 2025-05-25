package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * MyCompressorOutputStream compresses maze data using a simple run-length encoding approach.
 * The format assumes that the first 12 bytes of the input represent the maze metadata
 * (rows, columns, start/goal positions). Then one byte representing the first maze value (0 or 1),
 * followed by a sequence of counts of repeating values alternating between 0 and 1.
 */
public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Constructs a MyCompressorOutputStream that wraps an existing OutputStream.
     *
     * @param out the OutputStream to wrap
     */
    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes a single byte to the wrapped OutputStream.
     *
     * @param b the byte to write
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Writes a byte array to the stream, compressing the maze data using RLE.
     * The first 12 bytes (maze metadata) are written directly,
     * then one byte for the first maze value, followed by alternating run counts.
     *
     * @param b the byte array representing the maze
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(byte[] b) throws IOException {
        // write metadata directly (rows, cols, start/goal positions)
        for (int i = 0; i < 12; i++) {
            write(b[i]);
        }

        // write the first value of the maze data explicitly
        write(b[12]); // 0 or 1 — lets decompressor know where to start

        // compress the maze data and write it
        byte[] compressed = compress(b, 12);
        for (byte value : compressed) {
            write(value);
        }
    }

    /**
     * Compresses the maze grid using RLE.
     * Each run of identical bytes (0 or 1) is replaced with a single byte count.
     *
     * @param b     the full byte array representing the maze
     * @param start the index from which the maze grid starts
     * @return a compressed byte array (counts only, no values)
     */
    private byte[] compress(byte[] b, int start) {
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();

        byte current = b[start++];
        int count = 1;

        while (start < b.length) {
            if (b[start] == current && count < 255) {
                count++;
            } else {
                compressed.write(count);
                current = b[start];
                count = 1;
            }
            start++;
        }

        // write the final run
        compressed.write(count);

        return compressed.toByteArray();
    }

    /**
     * A simple expandable byte array accumulator, used to construct
     * the compressed output without knowing the final size in advance.
     */
    private static class ByteArrayOutputStream {
        private byte[] buffer = new byte[256];
        private int size = 0;

        /**
         * Appends a byte value to the buffer.
         *
         * @param value the value to add
         */
        public void write(int value) {
            if (size >= buffer.length) {
                expand();
            }
            buffer[size++] = (byte) value;
        }

        /**
         * Doubles the size of the internal buffer.
         */
        private void expand() {
            byte[] newBuffer = new byte[buffer.length * 2];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }

        /**
         * Converts the accumulated data to a new trimmed byte array.
         *
         * @return the byte array containing the written values
         */
        public byte[] toByteArray() {
            byte[] result = new byte[size];
            System.arraycopy(buffer, 0, result, 0, size);
            return result;
        }
    }
}
