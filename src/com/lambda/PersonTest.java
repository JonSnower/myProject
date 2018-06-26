package com.lambda;

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

		// ����ǰ
		list.forEach(item -> System.out.println(((Person) item).getName()));

		// ����
		//Collections.sort(list, byName);
		// ��
		list.sort(byName);
		
		// �����
		list.forEach(item -> System.out.println(((Person) item).getName()));
		
		/**
		��һ����ȥ�������������
		Collections.sort(peoples,(Person x, Person y) -> x.getLastName().compareTo(y.getLastName()));

		�ڶ�����ʹ��Comparator���comparing����
		Collections.sort(peoples, Comparator.comparing((Person p) -> p.getLastName()));

		�������������Ƶ��;�̬����
		Collections.sort(peoples, comparing(p -> p.getLastName()));

		���Ĳ����������� , ˫ð���������Java�е�[��������],����û�����š�()��
		Collections.sort(peoples, comparing(Person::getLastName));

		���岽��ʹ��List�����sort����
		peoples.sort(comparing(Person::getLastName));;
		*/
	}
}
