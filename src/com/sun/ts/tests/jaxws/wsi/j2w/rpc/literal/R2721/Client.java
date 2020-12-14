/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package com.sun.ts.tests.jaxws.wsi.j2w.rpc.literal.R2721;

import java.util.Properties;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sun.javatest.Status;
import com.sun.ts.lib.harness.ServiceEETest;
import com.sun.ts.tests.jaxws.sharedclients.ClientFactory;
import com.sun.ts.tests.jaxws.sharedclients.SOAPClient;
import com.sun.ts.tests.jaxws.sharedclients.faultclient.FaultTestClient;
import com.sun.ts.tests.jaxws.wsi.constants.DescriptionConstants;
import com.sun.ts.tests.jaxws.wsi.constants.SOAPConstants;
import com.sun.ts.tests.jaxws.wsi.utils.DescriptionUtils;

public class Client extends ServiceEETest
    implements DescriptionConstants, SOAPConstants {
  /**
   * The client.
   */
  private SOAPClient client;

  static FaultTest service = null;

  private boolean foundOne = false;

  /**
   * Test entry point.
   * 
   * @param args
   *          the command-line arguments.
   */
  public static void main(String[] args) {
    Client test = new Client();
    Status status = test.run(args, System.out, System.err);
    status.exit();
  }

  /**
   * @class.testArgs: -ap jaxws-url-props.dat
   * @class.setup_props: webServerHost; webServerPort; platform.mode;
   *
   * @param args
   * @param properties
   *
   * @throws Fault
   */
  public void setup(String[] args, Properties properties) throws Fault {
    client = ClientFactory.getClient(FaultTestClient.class, properties, this,
        service);
    logMsg("setup ok");
  }

  public void cleanup() {
    logMsg("cleanup");
  }

  /**
   * @testName: testNameAttributes
   *
   * @assertion_ids: WSI:SPEC:R2721
   *
   * @test_Strategy: Retrieve the WSDL, generated by the Java-to-WSDL tool, and
   *                 examine the wsdl:binding elements, ensuring that the
   *                 contained soap:fault elements have the name attribute
   *                 specified.
   *
   * @throws Fault
   */
  public void testNameAttributes() throws Fault {
    Document document = client.getDocument();
    Element[] bindings = DescriptionUtils.getBindings(document);
    for (int i = 0; i < bindings.length; i++) {
      verifyBinding(bindings[i]);
    }
    if (!foundOne) {
      throw new Fault("No soap:fault elements found during test");
    }

  }

  protected void verifyBinding(Element binding) throws Fault {
    Element[] operations = DescriptionUtils.getChildElements(binding,
        WSDL_NAMESPACE_URI, WSDL_OPERATION_LOCAL_NAME);
    for (int i = 0; i < operations.length; i++) {
      verifyOperation(operations[i]);
    }
  }

  protected void verifyOperation(Element operation) throws Fault {
    NodeList list = operation.getElementsByTagNameNS(SOAP_NAMESPACE_URI,
        SOAP_FAULT_LOCAL_NAME);
    for (int i = 0; i < list.getLength(); i++) {
      verifySOAPFault((Element) list.item(i));
    }
  }

  protected void verifySOAPFault(Element fault) throws Fault {
    Attr attr = fault.getAttributeNode(SOAP_NAME_ATTR);
    if (attr == null) {
      throw new Fault(
          "Required 'name' attribute not present on soap:fault element (BP-R2721)");
    } else {
      foundOne = true;
    }
  }
}
