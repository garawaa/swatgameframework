package org.swat.data.tests;


import java.io.IOException;


import org.swat.server.controller.*;


public class UserAuthenticationTest {
	
	public UserAuthenticationTest() {
		UserAuthentication ua = new UserAuthentication();
		try {
			testInitialization1(ua);
			testInitialization2(ua);
			testInitialization3(ua);
			testInitialization4(ua);
			testuserauthenticate(ua);
			testuseradd(ua);
		} catch(IOException ex) {
			System.out.println("test fail");
		}
	}
	
	private static void assertTrue(boolean f) throws IOException {
		if (f = false) {
			throw (new IOException());
		}
	}
	
	private static void assertFalse(boolean f) throws IOException {
		if (f = true) {
			throw (new IOException());
		}
	}
	
	
	public void testInitialization1(UserAuthentication ua) throws IOException
	{
		assertTrue(ua.userauthenticate("weiyu", "1111"));
	}
	
	public void testInitialization2(UserAuthentication ua) throws IOException
	{
		assertTrue(ua.userauthenticate("steve", "1111"));
	}
	
	public void testInitialization3(UserAuthentication ua) throws IOException
	{
		assertTrue(ua.userauthenticate("abhi", "1111"));
	}
	
	public void testInitialization4(UserAuthentication ua) throws IOException
	{
		assertTrue(ua.userauthenticate("tom", "1111"));
	}
	
	public void testuserauthenticate(UserAuthentication ua) throws IOException
	{
		assertTrue(ua.userauthenticate("weiyu", "1111"));
		assertFalse(ua.userauthenticate("weiyu", "1112"));
		assertFalse(ua.userauthenticate("wei", "1111"));
		assertFalse(ua.userauthenticate("wei", "1112"));
	}
	
	public void testuseradd(UserAuthentication ua) throws IOException
	{
		assertFalse(ua.adduser("weiyu", "1111"));
		assertFalse(ua.adduser("weiyu", "1112"));
		assertTrue(ua.adduser("john", "1111"));
		assertTrue(ua.userauthenticate("join", "1111"));
	}

}
