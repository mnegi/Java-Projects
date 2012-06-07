


public class TrapEvent extends java.util.EventObject  
{
    protected boolean consumed = false;

    private int version;
    private String hostAddress;
    private byte [] message;
    private Pdu trapPdu;
    private boolean isDecoded;

public TrapEvent(Object source, Pdu pdu) 
{
    super(source);
    trapPdu = pdu;
    isDecoded = true;
}

public TrapEvent(Object source, int v, String hn, byte [] mess) 
{
    super(source);
    version = v;
    hostAddress = hn;
    message = mess;
    isDecoded = false;
}

public boolean isDecoded()
{
    return isDecoded;
}


public int getVersion()
{
    return version;
}

public String getHostAddress()
{
    return hostAddress;
}

public byte [] getMessage()
{
    return message;
}

public Pdu getPdu()
{
    return trapPdu;
}

public void consume()
{
    consumed = true;
}

public boolean isConsumed()
{
    return consumed;
}

}
