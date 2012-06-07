

public abstract class SetPdu extends Pdu 
{
    public SetPdu(SnmpContextBasisFace con) 
    {
        super(con);
        setMsgType(AsnObject.SET_REQ_MSG);
    }


}
