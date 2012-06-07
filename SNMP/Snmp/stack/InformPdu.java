


public abstract class InformPdu extends Pdu 
{
public InformPdu(SnmpContextBasisFace con) 
{
    super(con);
    setMsgType(AsnObject.INFORM_REQ_MSG);
}

}
