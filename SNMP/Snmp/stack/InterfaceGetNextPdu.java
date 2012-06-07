 
import java.util.*;
public class InterfaceGetNextPdu extends GetNextPdu_vec
{
    public final static String IFNUMBER      ="1.3.6.1.2.1.2.1.0";

    public final static String SYS_UPTIME    ="1.3.6.1.2.1.1.3";

    public final static String INDEX         ="1.3.6.1.2.1.2.2.1.1";

    public final static String DESCR         ="1.3.6.1.2.1.2.2.1.2";

    public final static String OPR_STATUS    ="1.3.6.1.2.1.2.2.1.8";
    public final static String IN_OCTETS     ="1.3.6.1.2.1.2.2.1.10";

    public final static String OUT_OCTETS    ="1.3.6.1.2.1.2.2.1.16";

    final public static String UP       = "up";
    final public static String DOWN     = "down";
    final public static String TESTING  = "testing";
    final public static String UNKNOWN  = "unknown";

    final static int    NR_OID     = 6;
    final static String inf_oids[] =
    {
        SYS_UPTIME,
        INDEX,
        DESCR,
        OPR_STATUS,
        IN_OCTETS,
        OUT_OCTETS
    };

    private varbind[] inf_var;

    int index;
    long sysUpTime;
    int operStatus;
    long inOctets;
    long outOctets;
    long speed;
    String descr;
    boolean valid = false;


public InterfaceGetNextPdu(SnmpContextBasisFace con)
{
    super(con, NR_OID);
    inf_var = new varbind[NR_OID];
    for (int i=0; i<NR_OID; i++)
    {
        inf_var[i]=null;
    }
    speed = 0;
    valid = false;
}

public int getIndex()
{
    return index;
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
            break;
    }
    return str;
}


public long getSpeed()
{
    return speed;
}

public long getSpeed(InterfaceGetNextPdu old)
{
    speed = -1;
    if ((this.operStatus <1) 
            || 
        (old.operStatus <1) 
            || 
        !this.valid 
            || 
        !old.valid)
    {
        return -1;
    }
    long tdif = (this.sysUpTime - old.sysUpTime);
    if (tdif != 0)
    {
        long inO = this.inOctets - old.inOctets;
        long outO = this.outOctets - old.outOctets;

        speed = 100 * (inO + outO) / tdif;
    }
    return speed;
}


public boolean addOids()
{
    for (int i=0; i<NR_OID; i++)
    {
        inf_var[i] = new varbind(inf_oids[i]);
        addOid(inf_var[i]);
    }
    return true;
}

public void addOids(InterfaceGetNextPdu old)
{
    inf_var[0] = new varbind(SYS_UPTIME);

    for (int i=1; i<NR_OID; i++)
    {
        inf_var[i] = old.inf_var[i];
    }

    for (int i=0; i<NR_OID; i++)
    {
        addOid(inf_var[i]);
    }
}

protected void new_value(int n, varbind res)
{
    inf_var[n] = res;

    if (getErrorStatus() == AsnObject.SNMP_ERR_NOERROR)
    {
        if (inf_var[n].getOid().toString().startsWith(inf_oids[n]))
        {
            try
            {
                switch (n)
                {
                    case 0:
                        sysUpTime = ((AsnUnsInteger) 
                                      inf_var[n].getValue()).getValue();
                        break;
                    case 1:
                        index = ((AsnInteger) inf_var[n].getValue()).getValue();
                        break;
                    case 2:
                        descr = ((AsnOctets) inf_var[n].getValue()).getValue();
                        break;
                    case 3:
                        operStatus = ((AsnInteger)
                                      inf_var[n].getValue()).getValue();
                        break;
                    case 4:
                        inOctets  = ((AsnUnsInteger)
                                    inf_var[n].getValue()).getValue();
                        break;
                    case 5:
                        outOctets = ((AsnUnsInteger)
                                    inf_var[n].getValue()).getValue();
                        valid = true;
                        break;
                    default:
                        valid = false;
                }
            }
            catch(ClassCastException exc)
            {
                descr = null;
                index = 0;
                sysUpTime = 0;
                operStatus = 0;
                inOctets = 0;
                outOctets = 0;
                valid = false;
            }
        }
        else
        {
            setErrorStatus(AsnObject.SNMP_ERR_NOSUCHNAME);
            setErrorIndex(n);
        }
    }
}

public String toString()
{
    String str = new String();

    for (int i=0; i<NR_OID; i++)
    {
        str = str + inf_var[i] + "\n";
    }
    return str;
}


public static int getNumIfs(SnmpContextBasisFace con)
throws PduException, java.io.IOException
{
    int ifNumber=0;

    if (con != null)
    {
        OneIntPdu numIfs = new OneIntPdu(con, IFNUMBER);
        if (numIfs.waitForSelf())
        {
            ifNumber = numIfs.getValue().intValue();
        }
    }
    return ifNumber;
}

}

