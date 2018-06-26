package com.lambda;

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

	// ����һ��������������һ�����ڴ���ɢ��ʱ��ͨ��hashcodeȷ��λ����ͨ��equals�ж��Ƿ�һ�£�ȥ��ԭ��
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Person) {
			return name.equals(((Person) obj).getName());
		}
		return super.equals(obj);
	}

	// �������һ�������ص�hashcode��һ����,����ȷ��Ԫ������ɢ�б�λ��
	@Override
	public int hashCode() {
		if (this.name != null) {
			return name.hashCode();
		}
		return super.hashCode();
	}

	// ��HashSet�У������Ĳ���������HashMap�ײ�ʵ�ֵģ���ΪHashSet�ײ�����HashMap�洢���ݵġ�
	// ����HashSet�����Ԫ�ص�ʱ�����ȼ���Ԫ�ص�hashcodeֵ��
	// Ȼ���������Ԫ�ص�hashcode��%��HashMap���ϵĴ�С��+1��������Ԫ�صĴ洢λ�ã�
	// ������λ��λ�գ��ͽ�Ԫ����ӽ�ȥ��
	// �����Ϊ�գ�����equals�����Ƚ�Ԫ���Ƿ���ȣ���ȾͲ���ӣ�������һ����λ��ӡ�
}
