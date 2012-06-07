
public abstract class GetNextPdu extends Pdu 
{
    private static final String     version_id =
        "@(#)$Id: GetNextPdu.java,v 3.6 2002/10/10 15:13:57 birgit Exp $ Copyright Westhawk Ltd";

    /** 
     * Constructor.
     *
     * @param con The context of the Pdu
     */
    public GetNextPdu(SnmpContextBasisFace con) 
    {
        super(con);
        setMsgType(AsnObject.GETNEXT_REQ_MSG);
    }

}
