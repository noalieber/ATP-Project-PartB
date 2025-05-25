package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * SimpleDecompressorInputStream decompresses maze data using the basic [value, count] RLE format.
 *
 * It assumes the first 12 bytes represent the maze metadata and are copied directly.
 */
public class SimpleDecompressorInputStream extends InputStream {

    private InputStream in;

    /**
     * Constructs a decompressor that wraps an existing InputStream.
     * @param in the input stream containing compressed data
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads a single byte from the input stream.
     * @return the byte read (0–255), or -1 if end of stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads compressed maze data, decompresses it, and stores the result in the provided array.
     *
     * The first 12 bytes are read directly and copied into the output.
     * The remaining bytes are interpreted as [value, count] pairs.
     *
     * @param b the destination array to fill with decompressed data
     * @return the number of bytes written into the array
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        int totalRead = 0;

        // read the first 12 bytes
        for (int i = 0; i < 12; i++) {
            int value = read();
            if (value == -1) throw new IOException("Unexpected end of stream while reading header");
            b[i] = (byte) value;
            totalRead++;
        }

        int index = 12;

        // read and decode [value, count] pairs
        while (index < b.length) {
            int value = read();
            int count = read();

            if (value == -1 || count == -1)
                throw new IOException("Unexpected end of stream during decompression");

            for (int i = 0; i < count && index < b.length; i++) {
                b[index++] = (byte) value;
            }

            totalRead += count;
        }

        return totalRead;
    }
}
