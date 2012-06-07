// NAME
//      $RCSfile: OneTrapPduv1.java,v $
// DESCRIPTION
//      [given below in javadoc format]
// DELTA
//      $Revision: 3.3 $
// CREATED
//      $Date: 2002/10/10 15:13:57 $
// COPYRIGHT
//      Westhawk Ltd
// TO DO
//

/*
 * Copyright (C) 2001, 2002 by Westhawk Ltd
 * <a href="www.westhawk.co.uk">www.westhawk.co.uk</a>
 *
 * Permission to use, copy, modify, and distribute this software
 * for any purpose and without fee is hereby granted, provided
 * that the above copyright notices appear in all copies and that
 * both the copyright notice and this permission notice appear in
 * supporting documentation.
 * This software is provided "as is" without express or implied
 * warranty.
 * author <a href="mailto:snmp@westhawk.co.uk">Tim Panton</a>
 */


import java.util.*;
import java.io.*;


/**
 * This class represents the ASN SNMPv1 Trap Pdu object. 
 * See <a href="http://ietf.org/rfc/rfc1157.txt">RFC 1157</a>.
 *
 * @author <a href="mailto:snmp@westhawk.co.uk">Birgit Arkesteijn</a>
 * @version $Revision: 3.3 $ $Date: 2002/10/10 15:13:57 $
 */
public class OneTrapPduv1 extends TrapPduv1 
{
    private static final String     version_id =
        "@(#)$Id: OneTrapPduv1.java,v 3.3 2002/10/10 15:13:57 birgit Exp $ Copyright Westhawk Ltd";


/** 
 * Constructor.
 *
 * @param con The context v1 of the OneTrapPduv1
 * @see SnmpContext
 */
public OneTrapPduv1(SnmpContext con) 
{
    super(con);
}


}
