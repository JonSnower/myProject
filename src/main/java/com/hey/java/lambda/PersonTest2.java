package com.hey.java.lambda;

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

		// 过滤年龄大于10的对象,Filter过滤
		System.out.println("过滤");
		Stream stream = list.stream().filter(p -> p.getAge() > 10);
		stream.forEach(stu -> System.out.println(stu));

		// 把person转成Adult,map对元素进行操作
		System.out.println("person转成Adult");
		stream = list.stream().filter(p -> p.getAge() > 10).map(p -> new PersonAdult((Person) p));
		stream.forEach(stu -> System.out.println(stu));

		// 获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素，limit截断
		System.out.println("lilit截断");
		stream = list.stream().limit(2);
		stream.forEach(stu -> System.out.println(stu));

		// 去重，distinct
		System.out.println("distinct去重");

		// 如果用stream的distinct需要对对象重写equals和hashcode方法
		list.stream().distinct().forEach(System.out::println);
		// 也可以用下面的方法不用distinct（无侵入）
		// list.stream().filter(distinctByKey(Person::getName)).forEach(System.out::println);

		// 引申出几个经常在面试中问到的问题：
		// 1、两个对象，如果a.equals(b)==true，那么a和b是否相等？
		// 相等，但地址不一定相等。
		// 2、两个对象，如果hashcode一样，那么两个对象是否相等？
		// 不一定相等，判断两个对象是否相等，需要判断equals是否为true。

		// count统计
		System.out.println("count统计：");
		long num = list.stream().filter(p -> p.getAge() > 10).count();
		System.out.println(num);

		// collect
		// 将stram转成list
		List<Person> pList = list.stream().filter(p -> p.getAge() > 10).collect(Collectors.toList());
		pList.forEach(System.out::println);

		// 每个Stream都有两种模式：顺序执行和并行执行。

		// 顺序流：
		List<Person> pe1 = list.stream().collect(Collectors.toList());

		// 并行流：
		List<Person> pe2 = list.stream().parallel().collect(Collectors.toList());

		// 可以看出，要使用并行流，只需要.parallel()即可
		// 顾名思义，当使用顺序方式去遍历时，每个item读完后再读下一个item。
		// 而使用并行去遍历时，数组会被分成多个段，其中每一个都在不同的线程中处理，然后将结果一起输出。

		System.out.println("给年龄加10:");
		// 该接口表示接受单个输入参数并且没有返回值的操作
		Consumer<Person> giveRaise = e -> e.setAge(e.getAge() + 10);
		list.forEach(giveRaise);

		list.forEach(System.out::println);

		System.out.println("多filter");
		// Predicate 方法 表示 判断 输入的对象是否 符合某个条件。
		Predicate<Person> ageFilter = p -> p.getAge() > 20;
		Predicate<Person> salaryFilter = p -> p.getSalary() > 1000;
		list.stream().filter(ageFilter).filter(salaryFilter).forEach(System.out::println);

		System.out.println("排序操作：");
		// list.stream().sorted((p1,p2) -> p1.getSalary() -
		// p2.getSalary()).forEach(System.out::println);
		list.stream().sorted(Comparator.comparing(Person::getSalary)).forEach(System.out::println);
		System.out.println("从大到小：");
		list.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).forEach(System.out::println);

		System.out.println("最大最小值：");
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
