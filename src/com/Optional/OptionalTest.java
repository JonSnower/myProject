package com.Optional;

import java.util.Optional;

public class OptionalTest {
	public static void main(String[] args) {

		// ����һ������Ϊnull�������������ֵ������isPresent()�����᷵��true������get()�����᷵�ظö���

		// Optional<User> emptyOpt = Optional.empty();
		// emptyOpt.get(); //���Է��� emptyOpt ������ֵ�ᵼ�� NoSuchElementException

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