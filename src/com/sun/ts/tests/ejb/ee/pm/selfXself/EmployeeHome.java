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

package com.sun.ts.tests.ejb.ee.pm.selfXself;

import java.rmi.RemoteException;
import java.util.Collection;

import jakarta.ejb.CreateException;
import jakarta.ejb.EJBHome;
import jakarta.ejb.FinderException;

public interface EmployeeHome extends EJBHome {
  public Employee create(Integer id, String firstName, String lastName,
      java.util.Date hireDate, float salary)
      throws RemoteException, CreateException;

  public Employee findByPrimaryKey(Integer key)
      throws RemoteException, FinderException;

  public Collection findAllEmployees() throws RemoteException, FinderException;

  public Employee findEmployeeByQuery1(String s)
      throws RemoteException, FinderException;

  public Employee findEmployeeByQuery2(String s)
      throws RemoteException, FinderException;
}
