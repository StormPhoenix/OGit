package com.stormphoenix.httpknife.http;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Arrays;

public class Base64 {

    /** The equals sign (=) as a byte. */
    private final static byte EQUALS_SIGN = (byte) '=';

    /** Preferred encoding. */
    private final static String PREFERRED_ENCODING = "US-ASCII";

    private static final byte[] ENC;
    private static final byte[] DEC;

    static {
        try {
            ENC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes("UTF-8");
        } catch (UnsupportedEncodingException var1) {
            throw new RuntimeException(var1.getMessage(), var1);
        }

        DEC = new byte[128];
        Arrays.fill(DEC, (byte) -3);

        for(int i = 0; i < 64; ++i) {
            DEC[ENC[i]] = (byte)i;
        }

        DEC[61] = -1;
        DEC[9] = -2;
        DEC[10] = -2;
        DEC[13] = -2;
        DEC[32] = -2;
    }


    /** Defeats instantiation. */
    private Base64() {
    }

    /**
     * <p>
     * Encodes up to three bytes of the array <var>source</var> and writes the
     * resulting four Base64 bytes to <var>destination</var>. The source and
     * destination arrays can be manipulated anywhere along their length by
     * specifying <var>srcOffset</var> and <var>destOffset</var>. This method
     * does not check to make sure your arrays are large enough to accomodate
     * <var>srcOffset</var> + 3 for the <var>source</var> array or
     * <var>destOffset</var> + 4 for the <var>destination</var> array. The
     * actual number of significant bytes in your array is given by
     * <var>numSigBytes</var>.
     * </p>
     * <p>
     * This is the lowest level of the encoding methods with all possible
     * parameters.
     * </p>
     *
     * @param source
     *          the array to convert
     * @param srcOffset
     *          the index where conversion begins
     * @param numSigBytes
     *          the number of significant bytes in your array
     * @param destination
     *          the array to hold the conversion
     * @param destOffset
     *          the index where output will be put
     * @return the <var>destination</var> array
     * @since 1.3
     */
    private static void encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset) {
        int inBuff = 0;
        switch(numSigBytes) {
            case 3:
                inBuff |= source[srcOffset + 2] << 24 >>> 24;
            case 2:
                inBuff |= source[srcOffset + 1] << 24 >>> 16;
            case 1:
                inBuff |= source[srcOffset] << 24 >>> 8;
            default:
                switch(numSigBytes) {
                    case 1:
                        destination[destOffset] = ENC[inBuff >>> 18];
                        destination[destOffset + 1] = ENC[inBuff >>> 12 & 63];
                        destination[destOffset + 2] = 61;
                        destination[destOffset + 3] = 61;
                        break;
                    case 2:
                        destination[destOffset] = ENC[inBuff >>> 18];
                        destination[destOffset + 1] = ENC[inBuff >>> 12 & 63];
                        destination[destOffset + 2] = ENC[inBuff >>> 6 & 63];
                        destination[destOffset + 3] = 61;
                        break;
                    case 3:
                        destination[destOffset] = ENC[inBuff >>> 18];
                        destination[destOffset + 1] = ENC[inBuff >>> 12 & 63];
                        destination[destOffset + 2] = ENC[inBuff >>> 6 & 63];
                        destination[destOffset + 3] = ENC[inBuff & 63];
                }

        }
    }

    /**
     * Encode string as a byte array in Base64 annotation.
     *
     * @param string
     * @return The Base64-encoded data as a string
     */
    public static String encode(String string) {
      byte[] bytes;
      try {
        bytes = string.getBytes(PREFERRED_ENCODING);
      } catch (UnsupportedEncodingException e) {
        bytes = string.getBytes();
      }
      return encodeBytes(bytes);
    }

    /**
     * Encodes a byte array into Base64 notation.
     *
     * @param source
     *          The data to convert
     * @return The Base64-encoded data as a String
     * @throws NullPointerException
     *           if source array is null
     * @throws IllegalArgumentException
     *           if source array, offset, or length are invalid
     * @since 2.0
     */
    public static String encodeBytes(byte[] source) {
      return encodeBytes(source, 0, source.length);
    }

    /**
     * Encodes a byte array into Base64 notation.
     *
     * @param source
     *          The data to convert
     * @param off
     *          Offset in array where conversion should begin
     * @param len
     *          Length of data to convert
     * @return The Base64-encoded data as a String
     * @throws NullPointerException
     *           if source array is null
     * @throws IllegalArgumentException
     *           if source array, offset, or length are invalid
     * @since 2.0
     */
    public static String encodeBytes(byte[] source, int off, int len) {
      byte[] encoded = encodeBytesToBytes(source, off, len);
      try {
        return new String(encoded, PREFERRED_ENCODING);
      } catch (UnsupportedEncodingException uue) {
        return new String(encoded);
      }
    }

    /**
     * Similar to {@link #encodeBytes(byte[], int, int)} but returns a byte
     * array instead of instantiating a String. This is more efficient if you're
     * working with I/O streams and have large data sets to encode.
     *
     *
     * @param source
     *          The data to convert
     * @param off
     *          Offset in array where conversion should begin
     * @param len
     *          Length of data to convert
     * @return The Base64-encoded data as a String if there is an error
     * @throws NullPointerException
     *           if source array is null
     * @throws IllegalArgumentException
     *           if source array, offset, or length are invalid
     * @since 2.3.1
     */
    public static byte[] encodeBytesToBytes(byte[] source, int off, int len) {

      if (source == null)
        throw new NullPointerException("Cannot serialize a null array.");

      if (off < 0)
        throw new IllegalArgumentException("Cannot have negative offset: "
            + off);

      if (len < 0)
        throw new IllegalArgumentException("Cannot have length offset: " + len);

      if (off + len > source.length)
        throw new IllegalArgumentException(
            String
                .format(
                    "Cannot have offset of %d and length of %d with array of length %d",
                    off, len, source.length));

      // Bytes needed for actual encoding
      int encLen = (len / 3) * 4 + (len % 3 > 0 ? 4 : 0);

      byte[] outBuff = new byte[encLen];

      int d = 0;
      int e = 0;
      int len2 = len - 2;
      for (; d < len2; d += 3, e += 4)
        encode3to4(source, d + off, 3, outBuff, e);

      if (d < len) {
        encode3to4(source, d + off, len - d, outBuff, e);
        e += 4;
      }

      if (e <= outBuff.length - 1) {
        byte[] finalOut = new byte[e];
        System.arraycopy(outBuff, 0, finalOut, 0, e);
        return finalOut;
      } else
        return outBuff;
    }

    public static byte[] decode(String s) {
        byte[] bytes;
        try {
            bytes = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var2) {
            bytes = s.getBytes();
        }

        return decode(bytes, 0, bytes.length);
    }

    public static byte[] decode(byte[] source, int off, int len) {
        byte[] outBuff = new byte[len * 3 / 4];
        int outBuffPosn = 0;
        byte[] b4 = new byte[4];
        int b4Posn = 0;

        for(int out = off; out < off + len; ++out) {
            byte sbiCrop = (byte)(source[out] & 127);
            byte sbiDecode = DEC[sbiCrop];
            if(-1 <= sbiDecode) {
                b4[b4Posn++] = sbiCrop;
                if(b4Posn > 3) {
                    outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
                    b4Posn = 0;
                    if(sbiCrop == 61) {
                        break;
                    }
                }
            } else if(sbiDecode != -2) {
                throw new IllegalArgumentException(MessageFormat.format("Bad Base64 input character at {0} : {1} (decimal)", new Object[]{Integer.valueOf(out), Integer.valueOf(source[out] & 255)}));
            }
        }

        if(outBuff.length == outBuffPosn) {
            return outBuff;
        } else {
            byte[] var10 = new byte[outBuffPosn];
            System.arraycopy(outBuff, 0, var10, 0, outBuffPosn);
            return var10;
        }
    }

    private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset) {
        int outBuff;
        if(source[srcOffset + 2] == 61) {
            outBuff = (DEC[source[srcOffset]] & 255) << 18 | (DEC[source[srcOffset + 1]] & 255) << 12;
            destination[destOffset] = (byte)(outBuff >>> 16);
            return 1;
        } else if(source[srcOffset + 3] == 61) {
            outBuff = (DEC[source[srcOffset]] & 255) << 18 | (DEC[source[srcOffset + 1]] & 255) << 12 | (DEC[source[srcOffset + 2]] & 255) << 6;
            destination[destOffset] = (byte)(outBuff >>> 16);
            destination[destOffset + 1] = (byte)(outBuff >>> 8);
            return 2;
        } else {
            outBuff = (DEC[source[srcOffset]] & 255) << 18 | (DEC[source[srcOffset + 1]] & 255) << 12 | (DEC[source[srcOffset + 2]] & 255) << 6 | DEC[source[srcOffset + 3]] & 255;
            destination[destOffset] = (byte)(outBuff >> 16);
            destination[destOffset + 1] = (byte)(outBuff >> 8);
            destination[destOffset + 2] = (byte)outBuff;
            return 3;
        }
    }
}

