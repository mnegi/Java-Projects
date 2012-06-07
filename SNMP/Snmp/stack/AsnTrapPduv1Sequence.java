
import java.io.*;
import java.util.*;
class AsnTrapPduv1Sequence extends AsnSequence 
{
AsnTrapPduv1Sequence(InputStream in, int len, int pos) throws IOException 
{
    super(in,len,pos);
}

String getEnterprise() throws DecodingException
{
    String ent = "";
    AsnObject obj = getObj(0);
    if (obj instanceof AsnObjectId)
    {
        AsnObjectId rid = (AsnObjectId) obj;
        ent = rid.getValue();
    }
    else
    {
        String msg = "TrapPduv1.Enterprise should be AsnObjectId"
            + " instead of " + obj.getRespTypeString();
        throw new DecodingException(msg);
    }
    return ent;
}


byte [] getIPAddress() throws DecodingException
{
    byte [] ip = null;
    AsnObject obj = getObj(1);
    if (obj instanceof AsnOctets)
    {
        AsnOctets estat = (AsnOctets) obj;
        ip = estat.getBytes();
    }
    else
    {
        String msg = "TrapPduv1.IPAddress should be of type AsnOctets"
            + " instead of " + obj.getRespTypeString();
        throw new DecodingException(msg);
    }
    return ip;
}

int getGenericTrap() throws DecodingException
{
    int genTrap = -1;
    AsnObject obj = getObj(2);
    if (obj instanceof AsnInteger)
    {
        AsnInteger estat = (AsnInteger) obj;
        genTrap = estat.getValue();
    }
    else
    {
        String msg = "TrapPduv1.GenericTrap should be of type AsnInteger"
            + " instead of " + obj.getRespTypeString();
        throw new DecodingException(msg);
    }
    return genTrap;
}


int getSpecificTrap() throws DecodingException
{
    int specTrap = -1;
    AsnObject obj = getObj(3);
    if (obj instanceof AsnInteger)
    {
        AsnInteger estat = (AsnInteger) obj;
        specTrap = estat.getValue();
    }
    else
    {
        String msg = "TrapPduv1.SpecificTrap should be of type AsnInteger"
            + " instead of " + obj.getRespTypeString();
        throw new DecodingException(msg);
    }
    return specTrap;

}

long getTimeTicks() throws DecodingException
{
    long ticks = -1;
    AsnObject obj = getObj(4);
    if (obj instanceof AsnUnsInteger)
    {
        AsnUnsInteger estat = (AsnUnsInteger) obj;
        ticks = estat.getValue();
    }
    else
    {
        String msg = "TrapPduv1.TimeTicks should be of type AsnUnsInteger"
            + " instead of " + obj.getRespTypeString();
        throw new DecodingException(msg);
    }
    return ticks;
}

AsnSequence getVarBind() throws DecodingException
{
    AsnSequence varb = null;
    AsnObject obj = getObj(5);
    if (obj instanceof AsnSequence)
    {
        varb = (AsnSequence) obj;
    }
    else
    {
        String msg = "TrapPduv1.VarBind should be of type AsnSequence"
            + " instead of " + obj.getRespTypeString();
        throw new DecodingException(msg);
    }
    return varb;
}

/** 
 * recursively look for a trapPduv1Sequence object
 * - got one :-)
 */
AsnObject findTrapPduv1() 
{
    return this;  
}

}
