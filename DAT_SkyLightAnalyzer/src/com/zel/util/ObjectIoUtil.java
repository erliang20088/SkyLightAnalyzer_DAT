package com.zel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectIoUtil {
	public static Object readObject(String file_path) {
		FileInputStream input = null;
		ObjectInputStream objectInput = null;
		try {
			input = new FileInputStream(file_path);
			objectInput = new ObjectInputStream(input);
			Object bloomFilter = objectInput.readObject();
			objectInput.close();
			input.close();
			return bloomFilter;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean writeObject(String file_path, Object bloom) {
		FileOutputStream output = null;
		ObjectOutputStream objectOutput = null;
		// 判断父路径是否存在，若存在则不做创建操作，若不存在，首先创建父路径
		File f = new File(file_path);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		try {
			output = new FileOutputStream(file_path);
			objectOutput = new ObjectOutputStream(output);
			objectOutput.writeObject(bloom);
			objectOutput.close();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static byte[] b = new byte[1024 * 5];

	public static boolean fileBak(String sourceFile, String destinationFile) {
		try {
			FileInputStream input = new FileInputStream(sourceFile);
			FileOutputStream output = new FileOutputStream(destinationFile);
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
