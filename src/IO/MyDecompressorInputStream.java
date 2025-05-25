package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyDecompressorInputStream reads a compressed maze from an InputStream
 * and decompresses it using run-length encoding (RLE).
 *
 * Assumes the format produced by MyCompressorOutputStream:
 * - First 12 bytes: maze metadata (rows, cols, positions)
 * - Next 1 byte: the starting value of the grid (0 or 1)
 * - Following bytes: RLE counts alternating between 0 and 1
 */
public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    /**
     * Constructs a decompressor that wraps an existing InputStream.
     *
     * @param in the input stream containing compressed data
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads a single byte from the input stream.
     *
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
     * The first 12 bytes are read directly into the array.
     * The next byte indicates the starting value of the grid (0 or 1),
     * and the rest are RLE counts alternating between values.
     *
     * @param b the destination array to fill with decompressed data
     * @return the number of bytes written into the array
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        int totalRead = 0;

        // read the 12-byte metadata
        for (int i = 0; i < 12; i++) {
            int value = read();
            if (value == -1) throw new IOException("Unexpected end of stream while reading metadata");
            b[i] = (byte) value;
            totalRead++;
        }

        // read the first grid value (0 or 1)
        int firstValue = read();
        if (firstValue == -1) throw new IOException("Unexpected end of stream while reading first value");
        byte currentValue = (byte) firstValue;

        // decompress the RLE counts
        int index = 12;
        while (index < b.length) {
            int count = read();
            if (count == -1) throw new IOException("Unexpected end of stream during decompression");

            for (int i = 0; i < count && index < b.length; i++) {
                b[index++] = currentValue;
            }

            // alternate value (0 -> 1 or 1 -> 0)
            currentValue = (byte) (1 - currentValue);
            totalRead += count;
        }

        return totalRead;
    }
}
