package com.sumanth.cb;

public class ServiceWithOutCercuitBraker {

	
	public static void main(String args[]) {
		SomeService ss = new SomeServiceImpl();
		try {
			ss.getData("");
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
}
