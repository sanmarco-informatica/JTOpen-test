///////////////////////////////////////////////////////////////////////////////
//
// JTOpen (IBM Toolbox for Java - OSS version)
//
// Filename:  JDCPDSGetConnection.java
//
// The source code contained herein is licensed under the IBM Public License
// Version 1.0, which has been approved by the Open Source Initiative.
// Copyright (C) 1997-2023 International Business Machines Corporation and
// others.  All rights reserved.
//
///////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////
//
//
//
//
//
////////////////////////////////////////////////////////////////////////
//
// File Name:    JDCPDSGetConnection.java
//
// Classes:      JDCPDSGetConnection
//
////////////////////////////////////////////////////////////////////////
//
//
// Release     Date        Userid    Comments
//
//
////////////////////////////////////////////////////////////////////////

package test;


import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

import javax.sql.DataSource;

import com.ibm.as400.access.AS400;



/**
Testcase JDCPDSGetConnection.  This tests the following methods
of the JDBC Driver class:

<ul>
<li>getConnection() 

</ul>
**/
public class JDCPDSGetConnection
extends JDTestcase {



    // Private data.
    private DataSource dataSource_;
    private Object pc;

    // Protected data.
    //protected String system_;
    //protected String userId_;
    //protected String password_;

/**
Constructor.
**/
    public JDCPDSGetConnection (AS400 systemObject,
                                Hashtable namesAndVars,
                                int runMode,
                                FileOutputStream fileOutputStream,
                                
                                String password)
    {
        super (systemObject, "JDCPDSGetConnection",
               namesAndVars, runMode, fileOutputStream,
               password);
        //system_ = systemObject.getSystemName();
        //userId_ = systemObject.getUserId();
        //password_ = password;
    }



/**
Performs setup needed before running variations.

@exception Exception If an exception occurs.
**/
    protected void setup ()
    throws Exception
    {
        if (isJdbc20StdExt()) {
            dataSource_ = (DataSource) JDReflectionUtil.createObject("com.ibm.db2.jdbc.app.DB2ConnectionPoolDataSource");
        }
    }


    // TO Test:
    // getConnection()
    // getConnection(user, password)



/**
getConnection() - Should work when the databasename is not set.  The 
default database name of *LOCAL will get picked up and the connection 
will get created.
**/
   public void Var001 ()
   {
      if (checkJdbc20StdExt ()) {
         try {
            pc = JDReflectionUtil.callMethod_O(dataSource_,"getPooledConnection");
            Connection conn_ = (Connection)JDReflectionUtil.callMethod_O(pc,"getConnection");

            // Run a query.
            Statement s = conn_.createStatement ();
            ResultSet rs = s.executeQuery ("SELECT * FROM QIWS.QCUSTCDT");
            boolean found = rs.next ();

            // Close the connection.
            conn_.close ();

            assertCondition (found);
         } catch (Exception e) {
            failed (e, "Unexpected Exception");
         }
      }
   }

/**
getConnection() - Should throw an exception when the databasename is set but not valid.
**/
    public void Var002 ()
    {
        if (checkJdbc20StdExt()) {
            try {
                JDReflectionUtil.callMethod_V(dataSource_,"setDatabaseName","invaliddatabasename");
                pc = JDReflectionUtil.callMethod_O(dataSource_,"getPooledConnection");
                Connection conn_ = (Connection)(Connection)JDReflectionUtil.callMethod_O(pc,"getConnection");
                failed("Did not throw exception but got "+conn_);
            }
            catch (Exception e) {
                assertExceptionIsInstanceOf (e, "java.sql.SQLException");
            }
        }
    }

/**
getConnection() - Should work when the valid databasename is set.
**/
    public void Var003 ()
    {
        if (checkJdbc20StdExt()) {
            try {
                JDReflectionUtil.callMethod_V(dataSource_,"setDatabaseName",system_);
		JDReflectionUtil.callMethod_V(dataSource_,"setUser",userId_);
		JDReflectionUtil.callMethod_V(dataSource_,"setPassword",passwordFile_);

                pc = JDReflectionUtil.callMethod_O(dataSource_,"getPooledConnection");
                Connection conn_ = (Connection)JDReflectionUtil.callMethod_O(pc,"getConnection");

                // Run a query.
                Statement s = conn_.createStatement ();
                ResultSet rs = s.executeQuery ("SELECT * FROM QIWS.QCUSTCDT");
                boolean found = rs.next ();

                // Close the connection.
                conn_.close ();

                assertCondition (found);
            }
            catch (Exception e) {
                failed (e, "Unexpected Exception");
            }
        }
    }

/**
getConnection(userid,pwd) - Should throw an exception when the userid is not valid.
**/
    public void Var004 ()
    {
        if (checkJdbc20StdExt()) {
            try {
                JDReflectionUtil.callMethod_V(dataSource_,"setDatabaseName",system_);
                JDReflectionUtil.callMethod_V(dataSource_,"setUser","invalidusername");
                JDReflectionUtil.callMethod_V(dataSource_,"setPassword",passwordFile_);
                pc = JDReflectionUtil.callMethod_O(dataSource_,"getPooledConnection");
                JDReflectionUtil.callMethod_O(pc,"getConnection");
                failed("Did not throw exception");
            }
            catch (Exception e) {
                assertExceptionIsInstanceOf (e, "java.sql.SQLException");
            }
        }
    }

/**
getConnection(userid,pwd) - Should throw an exception when the password is not valid.
**/
    public void Var005 ()
    {
        if (checkJdbc20StdExt()) {
            try {
                JDReflectionUtil.callMethod_V(dataSource_,"setDatabaseName",system_);
                JDReflectionUtil.callMethod_V(dataSource_,"setUser",userId_);
                JDReflectionUtil.callMethod_V(dataSource_,"setPassword","invalidpassword");
                pc = JDReflectionUtil.callMethod_O(dataSource_,"getPooledConnection");
                JDReflectionUtil.callMethod_O(pc,"getConnection");
                failed("Did not throw exception");
            }
            catch (Exception e) {
                assertExceptionIsInstanceOf (e, "java.sql.SQLException");
            }
        }
    }

/**
getConnection(userid,pwd) - Should work with valid userid,pwd.
**/
    public void Var006 ()
    {
        if (checkJdbc20StdExt()) {
            try {
                JDReflectionUtil.callMethod_V(dataSource_,"setDatabaseName",system_);
                JDReflectionUtil.callMethod_V(dataSource_,"setUser",userId_);
                JDReflectionUtil.callMethod_V(dataSource_,"setPassword",passwordFile_);
                pc = JDReflectionUtil.callMethod_O(dataSource_,"getPooledConnection");
                Connection conn_ = (Connection)JDReflectionUtil.callMethod_O(pc,"getConnection");

                // Run a query.
                Statement s = conn_.createStatement ();
                ResultSet rs = s.executeQuery ("SELECT * FROM QIWS.QCUSTCDT");
                boolean found = rs.next ();

                // Close the connection.
                conn_.close ();

                assertCondition (found);
            }
            catch (Exception e) {
                failed (e, "Unexpected Exception");
            }
        }
    }



}



