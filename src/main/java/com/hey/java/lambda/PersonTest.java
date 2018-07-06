package com.hey.java.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class PersonTest {
	public static void main(String[] args) {

		Comparator<Person> byName = Comparator.comparing(p -> p.getName());

		Person p1 = new Person();
		p1.setName("tom");

		Person p2 = new Person();
		p2.setName("cat");

		List list = new ArrayList();
		list.add(p1);
		list.add(p2);

		// 排序前
		list.forEach(item -> System.out.println(((Person) item).getName()));

		// 排序
		//Collections.sort(list, byName);
		// 或
		list.sort(byName);
		
		// 排序后
		list.forEach(item -> System.out.println(((Person) item).getName()));
		
		/**
		第一步：去掉冗余的匿名类
		Collections.sort(peoples,(Person x, Person y) -> x.getLastName().compareTo(y.getLastName()));

		第二步：使用Comparator里的comparing方法
		Collections.sort(peoples, Comparator.comparing((Person p) -> p.getLastName()));

		第三步：类型推导和静态导入
		Collections.sort(peoples, comparing(p -> p.getLastName()));

		第四步：方法引用 , 双冒号运算就是Java中的[方法引用],后面没有括号“()”
		Collections.sort(peoples, comparing(Person::getLastName));

		第五步：使用List本身的sort更优
		peoples.sort(comparing(Person::getLastName));;
		*/
	}
}
