package com.hey.java.file;

import java.io.File;

public class FileUtils {

	public static void main(String[] args) {
		File f1 = new File("E:\\a");
		deleatFile(f1);
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param file
	 */
	public static void deleatFile(File file) {
		if (file == null) {
			return;
		} else if (file.exists()) {// 如果此抽象指定的对象存在并且不为空。
			if (file.isFile()) {
				file.delete();// 如果此抽象路径代表的是文件，直接删除。
			} else if (file.isDirectory()) {// 如果此抽象路径指代的是目录
				String[] str = file.list();// 得到目录下的所有路径
				if (str == null) {
					file.delete();// 如果这个目录下面是空，则直接删除
				} else {// 如果目录不为空，则遍历名字，得到此抽象路径的字符串形式。
					for (String st : str) {
						deleatFile(new File(file, st));
					} // 遍历清楚所有当前文件夹里面的所有文件。
					file.delete();// 清楚文件夹里面的东西后再来清楚这个空文件夹
				}
			}
		}
	}
}