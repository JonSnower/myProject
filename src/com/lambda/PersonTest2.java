package com.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonTest2 {
	public static void main(String[] args) {

		List<Person> list = new ArrayList<Person>();
		list.add(new Person("tom", 18, 1000));
		list.add(new Person("cat", 20, 2000));
		list.add(new Person("java", 30, 1000));
		list.add(new Person("script", 10, 4000));
		list.add(new Person("script", 10, 5000));

		// �����������10�Ķ���,Filter����
		System.out.println("����");
		Stream stream = list.stream().filter(p -> p.getAge() > 10);
		stream.forEach(stu -> System.out.println(stu));

		// ��personת��Adult,map��Ԫ�ؽ��в���
		System.out.println("personת��Adult");
		stream = list.stream().filter(p -> p.getAge() > 10).map(p -> new PersonAdult((Person) p));
		stream.forEach(stu -> System.out.println(stu));

		// ��ȡ��ǰN��Ԫ�أ����ԭStream�а�����Ԫ�ظ���С��N���Ǿͻ�ȡ�����е�Ԫ�أ�limit�ض�
		System.out.println("lilit�ض�");
		stream = list.stream().limit(2);
		stream.forEach(stu -> System.out.println(stu));

		// ȥ�أ�distinct
		System.out.println("distinctȥ��");

		// �����stream��distinct��Ҫ�Զ�����дequals��hashcode����
		list.stream().distinct().forEach(System.out::println);
		// Ҳ����������ķ�������distinct�������룩
		// list.stream().filter(distinctByKey(Person::getName)).forEach(System.out::println);

		// ����������������������ʵ������⣺
		// 1�������������a.equals(b)==true����ôa��b�Ƿ���ȣ�
		// ��ȣ�����ַ��һ����ȡ�
		// 2�������������hashcodeһ������ô���������Ƿ���ȣ�
		// ��һ����ȣ��ж����������Ƿ���ȣ���Ҫ�ж�equals�Ƿ�Ϊtrue��

		// countͳ��
		System.out.println("countͳ�ƣ�");
		long num = list.stream().filter(p -> p.getAge() > 10).count();
		System.out.println(num);

		// collect
		// ��stramת��list
		List<Person> pList = list.stream().filter(p -> p.getAge() > 10).collect(Collectors.toList());
		pList.forEach(System.out::println);

		// ÿ��Stream��������ģʽ��˳��ִ�кͲ���ִ�С�

		// ˳������
		List<Person> pe1 = list.stream().collect(Collectors.toList());

		// ��������
		List<Person> pe2 = list.stream().parallel().collect(Collectors.toList());

		// ���Կ�����Ҫʹ�ò�������ֻ��Ҫ.parallel()����
		// ����˼�壬��ʹ��˳��ʽȥ����ʱ��ÿ��item������ٶ���һ��item��
		// ��ʹ�ò���ȥ����ʱ������ᱻ�ֳɶ���Σ�����ÿһ�����ڲ�ͬ���߳��д���Ȼ�󽫽��һ�������

		System.out.println("�������10:");
		// �ýӿڱ�ʾ���ܵ��������������û�з���ֵ�Ĳ���
		Consumer<Person> giveRaise = e -> e.setAge(e.getAge() + 10);
		list.forEach(giveRaise);

		list.forEach(System.out::println);

		System.out.println("��filter");
		// Predicate ���� ��ʾ �ж� ����Ķ����Ƿ� ����ĳ��������
		Predicate<Person> ageFilter = p -> p.getAge() > 20;
		Predicate<Person> salaryFilter = p -> p.getSalary() > 1000;
		list.stream().filter(ageFilter).filter(salaryFilter).forEach(System.out::println);

		System.out.println("���������");
		// list.stream().sorted((p1,p2) -> p1.getSalary() -
		// p2.getSalary()).forEach(System.out::println);
		list.stream().sorted(Comparator.comparing(Person::getSalary)).forEach(System.out::println);
		System.out.println("�Ӵ�С��");
		list.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).forEach(System.out::println);

		System.out.println("�����Сֵ��");
		Person p  = list.stream().max(Comparator.comparing(Person::getSalary)).get();
		System.out.println(p);
		
		System.out.println("-------------");
		list.stream().filter(distinctByKey(Person::getName)).forEach(System.out::println);
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return f -> seen.add(keyExtractor.apply(f));
	}

	Map map = new HashMap();
}
