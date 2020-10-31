package com.sumanth.cb;


public class SomeServiceImpl implements SomeService {

	@Override
	public String getData(String somevalue) {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("Got call");
			int i =1/0;
			
		}catch(Exception ex) {
			throw new RuntimeException();
		}
		return "result from service";
		
		
	}

}
