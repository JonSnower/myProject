package com.hey.java.optional;

import java.util.Optional;

public class OptionalTest {
	public static void main(String[] args) {

		// 这是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。

		// Optional<User> emptyOpt = Optional.empty();
		// emptyOpt.get(); //尝试访问 emptyOpt 变量的值会导致 NoSuchElementException

		User user = null;

		// Optional<User> opt = Optional.of(user);
		// opt.get(); //NullPointerException

		// Optional<User> opt = Optional.ofNullable(user);
		// opt.get(); // NoSuchElementException

		Book bag = null;
		// Book bag = new Book();
		// bag.setName("english");

		Optional<Book> bagOpt = Optional.ofNullable(bag);

		Student stu = new Student();
		stu.setBook(bagOpt);

		Optional<Student> stuOpt = Optional.ofNullable(stu);

		String bookName = stuOpt.flatMap(Student::getBook).map(Book::getName).orElse("Unknown");
		System.out.println(bookName);

	}
}

class User {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}