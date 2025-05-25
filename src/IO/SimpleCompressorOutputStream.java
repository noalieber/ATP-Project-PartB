package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * SimpleCompressorOutputStream compresses maze data using basic run-length encoding,
 * where each run is stored as a pair: [value, count].
 *
 * The first 12 bytes of the array (maze metadata) are written as-is. The rest is compressed.
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Constructs a compressor that wraps an existing OutputStream.
     * @param out the OutputStream to write compressed data into
     */
    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes a single byte to the stream.
     * @param b the byte to write
     * @throws IOException if writing fails
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses and writes the byte array using [value, count] RLE.
     * First 12 bytes (metadata) are written directly.
     *
     * @param b the uncompressed byte array
     * @throws IOException if writing fails
     */
    @Override
    public void write(byte[] b) throws IOException {
        // write the first 12 bytes
        for (int i = 0; i < 12; i++) {
            write(b[i]);
        }

        // compress the rest of the array
        int index = 12;
        byte current = b[index++];
        int count = 1;

        while (index < b.length) {
            if (b[index] == current && count < 255) {
                count++;
            } else {
                write(current);
                write(count);
                current = b[index];
                count = 1;
            }
            index++;
        }

        // write the final pair
        write(current);
        write(count);
    }
}
