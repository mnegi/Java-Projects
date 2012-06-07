import java.util.*;
public class OneGetNextPdu extends GetNextPdu 
{
    varbind var;

    public OneGetNextPdu(SnmpContextBasisFace con)
    {
        super(con);
    }

    public OneGetNextPdu(SnmpContextBasisFace con, String oid) 
    throws PduException, java.io.IOException
    {
        this(con, oid, null);
    }

    public OneGetNextPdu(SnmpContextBasisFace con, String oid, Observer o) 
    throws PduException, java.io.IOException
    {
        super(con);
        if (o != null) 
        {
            addObserver(o);
        }
        addOid(oid);
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
