
import java.io.*;
import java.util.*;

public class AsnInteger extends AsnObject
{
    protected int value;

    /** 
     * Constructor.
     *
     * @param v The value of the AsnInteger
     */
    public AsnInteger(int v) 
    { 
        value = v; 
    }

    /** 
     * Constructor.
     *
     * @param in The input stream from which the value should be read
     * @param len The length of the AsnInteger
     */
    public AsnInteger(InputStream in, int len) throws IOException
    {
        byte data[] = new byte[len];
        if (len != in.read(data,0,len))
        {
            throw new IOException("AsnInteger(): Not enough data");
        }
        int val = bytesToInteger(data);
        value = val;
    }

    /** 
     * Returns the value.
     *
     * @return The value of the AsnInteger
     */
    public int getValue()
    {
        return value;
    }

    /** 
     * Returns the string representation of the AsnInteger.
     *
     * @return The string of the AsnInteger
     */
    public String toString()
    {
        return (String.valueOf(value));
    }

    /**
     * Get number of bytes the integer occupies.
     */
    int size()
    {
        int  count, empty = 0x00, sign = 0x00;

        if (value < 0)
        {
            empty = 0xFF;
            sign  = 0x80;
        }

        // 32-bit integer.. change to 56 to write 64-bit long
        // loop through bytes in value while it is 'empty'
        for(count=24; count>0; count-=8)
        {
            if ( ((value >> count) & 0xFF) != empty) break;
        }
        // Check sign bit.. make sure negative's MSB bit is 1,
        // positives is 0
        // (0x00000080 = 0x00 0x80) 0xFFFFFF01 => 0xFF 0x01
        // (0x0000007F = 0x7F)      0xFFFFFF80 => 0x80
        if (((value >> count) & 0x80) != sign) count += 8;
        return (count>>3)+1;
    }


    /** 
     * Output integer.
     */
    void write(OutputStream out, int pos) throws IOException
    {
        int  count, empty = 0x00, sign = 0x00;

        if (value < 0)
        {
            empty = 0xFF;
            sign  = 0x80;
        }

        // Get count
        for(count=24; count>0; count-=8)
        {
            if ( ((value >> count) & 0xFF) != empty) break;
        }
        if (((value >> count) & 0x80) != sign) count += 8;

        // Build header and write value
        AsnBuildHeader(out, ASN_INTEGER, (count>>3)+1);

        if (debug > 10)
        {
            System.out.println("\tAsnInteger(): value = " + value
                + ", pos = " + pos);
        }

        for(; count>=0; count-=8)
        {
            out.write((byte)((value >> count) & 0xFF));
        }
    }

    /**
     * Changes an array of bytes into an int.
     * Thanks to Julien Conan (jconan@protego.net) for improving 
     * this method.
     *
     * @param data the array of bytes
     * @return the int represenation of the array
     */
    protected int bytesToInteger(byte[] data) throws IOException
    {
        DataInputStream dis = new DataInputStream(
              new ByteArrayInputStream(data));

        int val = 0;
        int size = data.length;

        /*
         * First byte contains the sign if the number is negative. Do this
         * only for AsnInteger
         */
        val = dis.readByte();

        for (int n=1; n<size; n++)
        {
            val = (val << 8) + dis.readUnsignedByte();
        }

        return val;
    }


    /**
     * Compares this object to the specified object.  The result is
     * <code>true</code> if and only if the argument is not
     * <code>null</code> and is an <code>AsnInteger</code> object that
     * contains the same <code>int</code> value as this object.
     *
     * @param   obj   the object to compare with.
     * @return  <code>true</code> if the objects are the same;
     *          <code>false</code> otherwise.
     */
    public boolean equals(Object obj) 
    {
        if (obj instanceof AsnInteger) 
        {
            return value == ((AsnInteger)obj).value;
        }
        return false;
    }


    /**
     * Returns a hash code for this <code>AsnInteger</code>.
     *
     * @return  a hash code value for this object, equal to the 
     *          primitive <code>int</code> value represented by this 
     *          <code>AsnInteger</code> object. 
     */
    public int hashCode() 
    {
        return value;
    }
}
