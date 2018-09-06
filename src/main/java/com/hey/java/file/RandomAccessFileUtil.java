package com.hey.java.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileUtil {
	
	/**
	 * 读取文件指定内容
	 * 
	 * @param path
	 * @param point
	 * @return
	 */
	public static String RandomAccessFileContent(String path, int point) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(path, "rw");
			System.out.println("RandomAccessFile文件指针的初始位置:" + raf.getFilePointer());
			raf.seek(point);// 移动文件指针位置
			byte[] buff = new byte[1024];
			// 用于保存实际读取的字节数
			int hasRead = 0;
			// 循环读取
			while ((hasRead = raf.read(buff)) > 0) {
				// 打印读取的内容,并将字节转为字符串输入
				System.out.println(new String(buff, 0, hasRead));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	public static String WriteContent() {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(new File("C:\\Users\\Administrator\\Desktop\\abc\\abc.txt"), "rw");
			raf.seek(0);
			raf.writeDouble(2.44);
			raf.writeDouble(2.45);
			raf.writeDouble(2.46);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static String ReadContent() {
		RandomAccessFile raf = null;
		try {
			File file = new File("C:\\Users\\Administrator\\Desktop\\abc\\abc.txt");
			raf = new RandomAccessFile(file,"rw");
			raf.seek(0);
			for (int i = 0; i < file.length(); i++) {
				System.out.println(raf.readDouble());
			}
			System.out.println(raf.readDouble());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
}
