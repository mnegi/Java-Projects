import java.util.*;

public class InterfacePdu extends Pdu 
{
    final static String IFNUMBER      ="1.3.6.1.2.1.2.1.0";

    final static String SYS_UPTIME    ="1.3.6.1.2.1.1.3";

    final static String DESCR         ="1.3.6.1.2.1.2.2.1.2";
    final static String OPR_STATUS    ="1.3.6.1.2.1.2.2.1.8";

    final static String IN_OCTETS     ="1.3.6.1.2.1.2.2.1.10";

    final static String OUT_OCTETS    ="1.3.6.1.2.1.2.2.1.16";

    final public static String UP       = "up";
    final public static String DOWN     = "down";
    final public static String TESTING  = "testing";
    final public static String UNKNOWN  = "unknown";

    long sysUpTime;
    int operStatus;
    long inOctet;
    long outOctet;
    long speed;
    int index;
    String descr;
    boolean valid = false;
    boolean got;



InterfacePdu(SnmpContextBasisFace con)
{
    super(con);
}

public InterfacePdu(SnmpContextBasisFace con, Observer o, int interf) 
throws PduException, java.io.IOException
{
    super(con);

    addOids(interf);
    if (o!=null) 
    {
        addObserver(o);
    }
    index = interf;
    send();
}

public int getIndex()
{
    return index;
}

public long getSysUpTime()
{
    return sysUpTime;
}

public String getDescription()
{
    return descr;
}

public int getOperStatus()
{
    return operStatus;
}

public String getOperStatusString()
{
    return getOperStatusString(operStatus);
}

public String getOperStatusString(int status)
{
    String str = null;
    switch (status) 
    {
        case 1: 
            str = UP;
            break;
        case 2: 
            str = DOWN;
            break;
        case 3: 
            str = TESTING;
            break;
        default: 
            str = UNKNOWN;
    }
    return str;
}

public long getInOctet()
{
    return inOctet;
}
 
public long getOutOctet()
{
    return outOctet;
}

public long getSpeed(InterfacePdu old)
{
    long speed = -1;
    if ((this.operStatus <1) || (old.operStatus <1) 
              || 
        !this.valid || !old.valid)
    {
        return -1;
    }
    long tdif = (this.sysUpTime - old.sysUpTime);
    if (tdif != 0)
    {
        speed = 100 *((this.inOctet - old.inOctet) 
              + (this.outOctet - old.outOctet))/ tdif;
    }
    return speed;
}
    
void addOids(int interf)
{
    addOid(SYS_UPTIME+".0");
    addOid(DESCR+"."+interf);
    addOid(OPR_STATUS+"."+interf);
    addOid(IN_OCTETS+"."+interf);
    addOid(OUT_OCTETS+"."+interf);
}

protected void new_value(int n, varbind res)  
{
    AsnObject obj = res.getValue();
    if (getErrorStatus() == AsnObject.SNMP_ERR_NOERROR)
    {
        try
        {
            switch (n) 
            {
                case 0:
                    sysUpTime = ((AsnUnsInteger) obj).getValue();
                    break;
                case 1:
                    descr = ((AsnOctets) obj).getValue();
                    break;
                case 2:
                    operStatus = ((AsnInteger) obj).getValue();
                    break;
                case 3:
                    inOctet = ((AsnUnsInteger) obj).getValue();
                    break;
                case 4:
                    outOctet = ((AsnUnsInteger) obj).getValue();
                    valid = true;
                    break;
                default:
                    valid = false;
            }
        }
        catch(ClassCastException exc)
        {
            sysUpTime = 0;
            descr = null;
            operStatus = 0;
            inOctet = 0;
            outOctet = 0;
            valid = false;
        }
    }
    else
    {
        valid = false;
    }
}

protected void tell_them()  
{
    notifyObservers(this);
}

public static int getNumIfs(SnmpContextBasisFace con)
throws PduException, java.io.IOException
{
    int ifCount =0;

    if (con != null)
    {
        OneIntPdu numIfs = new OneIntPdu(con, IFNUMBER);
        if (numIfs.waitForSelf())
        {
            ifCount = numIfs.getValue().intValue();
        }
    }
    return ifCount;
}


}

