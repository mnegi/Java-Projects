import java.util.*;
public class OneSetPdu extends SetPdu 
{
    varbind var;
    public OneSetPdu(SnmpContextBasisFace con)
    {
        super(con);
    }
    public OneSetPdu(SnmpContextBasisFace con, String oid, AsnObject val) 
    throws PduException, java.io.IOException
    {
        this(con, oid, val, null);
    }

    public OneSetPdu(SnmpContextBasisFace con, String oid, AsnObject val, Observer o) 
    throws PduException, java.io.IOException
    {
        super(con);
        if (o != null) 
        {
            addObserver(o);
        }
        addOid(oid, val);
        send();
    }

    protected void new_value(int n, varbind a_var) 
    {
        if (n == 0) 
        {
            var = a_var;
        }
    }

    protected void tell_them()  
    {
        notifyObservers(var);
    }

}
