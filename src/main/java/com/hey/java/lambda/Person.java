package com.hey.java.lambda;

public class Person {

	private String name;

	private int age;

	private int salary;

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

	public Person() {

	}

	public Person(String name, int age, int salary) {
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "name:" + this.name + ",age:" + this.age + ",salary:" + this.salary;
	}

	// 名字一样，即两个对象一样，在存入散表时先通过hashcode确定位置再通过equals判断是否一致（去重原理）
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Person) {
			return name.equals(((Person) obj).getName());
		}
		return super.equals(obj);
	}

	// 如果名字一样，返回的hashcode是一样的,用于确定元素所在散列表位置
	@Override
	public int hashCode() {
		if (this.name != null) {
			return name.hashCode();
		}
		return super.hashCode();
	}

	// 在HashSet中，基本的操作都是有HashMap底层实现的，因为HashSet底层是用HashMap存储数据的。
	// 当向HashSet中添加元素的时候，首先计算元素的hashcode值，
	// 然后用这个（元素的hashcode）%（HashMap集合的大小）+1计算出这个元素的存储位置，
	// 如果这个位置位空，就将元素添加进去；
	// 如果不为空，则用equals方法比较元素是否相等，相等就不添加，否则找一个空位添加。
}
