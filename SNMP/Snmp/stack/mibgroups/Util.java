
import java.net.*;
import java.io.*;
import java.util.*;

public class Util 
{
    public final static String HOST = "host";
    public final static String PORT = "port";
    public final static String SOCKETTYPE = "sockettype";
    public final static String COMM = "comm";
    public final static String OID = "oid";
    public final static String INTERVAL = "interval";

    Properties prop;

public Util(String propertiesFilename, String classname)
{
    File propFile = null;
    prop = new Properties();

    if (propertiesFilename == null)
    {
        propertiesFilename = getDefaultPropertiesFilename(classname);
        propFile = new File(propertiesFilename);
    }
    else
    {
        propFile = new File(propertiesFilename);
        if (propFile.exists() == false)
        {
            System.out.println("Util(): Cannot find properties file '" 
                  + propFile + "'.");

            propertiesFilename = getDefaultPropertiesFilename(classname);
            propFile = new File(propertiesFilename);
        }
    }

    if (propFile.exists() == true)
    {
        try
        {
            FileInputStream in = new FileInputStream(propFile);
            prop.load(in);
            in.close();
            System.out.println("Util(): Using properties file '" + propFile
                  + "'.");
        }
        catch (FileNotFoundException exc)
        {
            System.out.println("Util(): FileNotFoundException " + exc.getMessage());
        }
        catch (IOException exc)
        {
            System.out.println("Util(): IOException " + exc.getMessage());
        }
    }
    else
    {
        System.out.println("Util(): Cannot find properties file '" + propFile
              + "'. Will use default properties.");
    }
}

public static String getDefaultPropertiesFilename(String classname)
{
    String propertiesFilename;
    if (classname == null)
    {
        propertiesFilename = "default.properties";
    }
    else
    {
        int index = classname.lastIndexOf('.');
        propertiesFilename = classname.substring(index+1) + ".properties";
    }
    return propertiesFilename;
}

/**
 * Returns the name of the localhost. If that cannot be found it will
 * return <code>localhost</code>.
 *
 * @return my host 
 */
public static String myHost()
{
    String str = null;
    try
    {
        InetAddress inetA = InetAddress.getLocalHost();
        str = inetA.getHostName();
    }
    catch (UnknownHostException exc) { }

    if (str == null)
    {
        str = "localhost";
    }
    return str;
}

/**
 * Returns the <code>host</code> property.
 *
 * @return The <code>host</code> property.
 * @see #HOST
 */
public String getHost()
{
    String host = prop.getProperty(HOST, myHost());
    return host;
}

/**
 * Returns the <code>port</code> property.
 *
 * @return The <code>port</code> property.
 * @see #PORT
 */
public String getPort()
{
    String port = prop.getProperty(PORT, "" + SnmpContextBasisFace.DEFAULT_PORT);
    return port;
}

/**
 * Returns the <code>port</code> property. 
 *
 * @param def The default value.
 * @return The <code>port</code> property.
 * @see #PORT
 */
public int getPort(int def)
{
    int port = getIntParameter(PORT, def);
    return port;
}

/**
 * Returns the <code>sockettype</code> property. The default value will
 * be the standard socket.
 *
 * @return The <code>sockettype</code> property.
 * @see #SOCKETTYPE
 * @see SnmpContextFace#STANDARD_SOCKET
 */
public String getSocketType()
{
    String socketType = SnmpContextFace.STANDARD_SOCKET;
    String param = prop.getProperty(SOCKETTYPE, SnmpContextFace.STANDARD_SOCKET);
    char f = param.charAt(0);
    if (f == 'N')
    {
        socketType = SnmpContextFace.NETSCAPE_SOCKET;
    }
    return socketType;
}

/**
 * Returns the <code>comm</code> property. The default value will
 * be the default community name.
 *
 * @return The <code>comm</code> property.
 * @see #COMM
 * @see SnmpContext#Default_Community
 */
public String getCommunity()
{
    String comm = prop.getProperty(COMM, SnmpContext.Default_Community);
    return comm;
}

/**
 * Returns the <code>oid</code> property. 
 *
 * @param def The default value.
 * @return The <code>oid</code> property.
 * @see #OID
 */
public String getOid(String def)
{
    String oid = prop.getProperty(OID, def);
    return oid;
}

public String getProperty(String key)
{
    return prop.getProperty(key);
}

public String getProperty(String key, String defaultValue)
{
    return prop.getProperty(key, defaultValue);
}

/**
 * Return the integer value of a property. If there is no property
 * <code>key</code>, or the value is not an integer, the default value
 * is returned.
 *
 * @param key The key
 * @param def The default value
 */
public int getIntParameter(String key, int def)
{
    int val = def;
    try
    {
        String v = prop.getProperty(key);
        if (v != null)
        {
            val = Integer.valueOf(v).intValue();
        }
    }
    catch (java.lang.NumberFormatException exc) { }
    catch (NullPointerException exc) { }
    return val;
}

public static boolean isNumber(String str)
{
    boolean res = false;
    try
    {
        int t = Integer.valueOf(str).intValue();
        res = true;
    }
    catch (NumberFormatException e) { }

    return res;
}

public static int getNumber(String str)
{
    int t=0;
    try
    {
        t = Integer.valueOf(str).intValue();
    }
    catch (NumberFormatException e) { }

    return t;
}

}
