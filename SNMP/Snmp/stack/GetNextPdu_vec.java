// NAME
//      $RCSfile: GetNextPdu_vec.java,v $
// DESCRIPTION

import java.lang.*;
public class GetNextPdu_vec extends GetNextPdu 
{
    varbind[] value;

    /**
     * Constructor.
     *
     * @param con The context of the request
     * @param count The number of OIDs to be get
     */
    public GetNextPdu_vec(SnmpContextBasisFace con, int count) 
    {
        super(con);
        value = new varbind[count];
    }

    /**
     * The value of the request is set. This will be called by
     * Pdu.fillin().
     *
     * @param n the index of the value
     * @param a_var the value
     * @see Pdu#new_value 
     */
    protected void new_value(int n, varbind var) 
    {
        if (n <value.length) 
        {
            value[n] = var;
        }
    }

    /**
     * The methods notifies all observers. 
     * This will be called by Pdu.fillin().
     * 
     * <p>
     * If no exception occurred whilst receiving the response, the Object to the 
     * update() method of the Observer will be an array of
     * varbinds, so they may contains any AsnObject type.
     * If an exception occurred, that exception will be passed as the Object
     * to the update() method.
     * </p>
     */
    protected void tell_them()  
    {
        notifyObservers(value);
    }
}
