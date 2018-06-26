package com.lambda;

public class PersonAdult {
	private String name;

	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public PersonAdult() {

	}
	
	public PersonAdult(Person persion) {
		this.name = persion.getName();
		this.age = persion.getAge();
	}

	public PersonAdult(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "name:" + this.name + ",age:" + this.age;
	}
	
}