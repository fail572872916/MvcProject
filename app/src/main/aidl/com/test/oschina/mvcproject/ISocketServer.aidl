// ISocketServer.aidl
package com.test.oschina.mvcproject;

// Declare any non-default types here with import statements

interface ISocketServer {

    boolean send (String msg);

    void  clsoeSocketConn(String  obj);
}
