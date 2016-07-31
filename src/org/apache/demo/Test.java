package org.apache.demo;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		User user1 = User.getInstance();
		user1.setName("1");
		User user2 = User.getInstance();
		user1.setName("2");
		
		System.out.println(user1.getName() + "--" + user2.getName());
		
	}

}
