package com.zel.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zel.entity.Forest;
import com.zel.entity.Library;
import com.zel.entity.WordPojo;
import com.zel.util.IOUtil;
import com.zel.util.ObjectIoUtil;
import com.zel.util.SystemParas;

public class LibraryManager {
	// 做日志用
	private static Logger logger = Logger.getLogger(LibraryManager.class);
	public static Forest forest = new Forest();

	public static List<WordPojo> wordList = new ArrayList<WordPojo>();// 存放所有的word及其后的参数
	// 用来存放将词典中的所有word分组后的结果
	public static Map<String, List<WordPojo>> wordGroupMap = new HashMap<String, List<WordPojo>>();

	/**
	 * 将词典中的每行转换成对应的WordPojo,并加入到WordList集合中
	 * 
	 * @param dic_path
	 * @param encoding
	 * @return
	 */
	public static List<WordPojo> getWordList4Dic(String dic_path,
			String encoding) {
		String dic_source = IOUtil.readFile(dic_path, encoding);
		BufferedReader br = new BufferedReader(new StringReader(dic_source));// 通过StringReader来读取词典
		String line = null;
		String items[] = null;// 存储每条dic item的param
		String[] parasArray = null;// 暂存每个词条后边储存的参数列表，包括权重、词性等.
		try {
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					line = line.trim();// 先去下空白字符,以免影响参数的构成
					items = line.split("\t");// 用制表符/t去分开每个词条的参数列表

					parasArray = new String[items.length - 1];
					for (int i = 1; i < items.length; i++) {
						parasArray[i - 1] = items[i];
					}
					wordList.add(new WordPojo(items[0], parasArray));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("得到wordList时出现错误!");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.info("关闭wordList的流时出现错误!");
			}
		}
		return wordList;
	}

	// 此时的dic_path将多个词典文件用空格格开就可以
	public static void loadLibrary(String dic_path, String encoding) {
		String[] dic_path_array = dic_path.split(" ");
		for (String temp_dic_file : dic_path_array) {
			getWordList4Dic(temp_dic_file, encoding);
		}
		// 加载完成后在此方法中对wordList集合进行升序排列
		// 由于首字符相同时，对集合是否排序对构造trie无影响，故暂去掉
		Collections.sort(wordList);
	}

	// 对已排序好的进行分组操作，用map来存储，每个key存放的是该词的首个char
	public static void getGroupMap() {
		WordPojo temp_pojo = null;
		String key = null;
		while (wordList.size() > 0) {
			temp_pojo = wordList.get(0);
			key = "" + temp_pojo.getWord().charAt(0);
			// 将得到的每个wordPojo对象放到wordGroupMap中去
			if (wordGroupMap.containsKey(key)) {
				wordGroupMap.get(key).add(temp_pojo);
			} else {
				List<WordPojo> oneGroupList = new ArrayList<WordPojo>();
				oneGroupList.add(temp_pojo);
				wordGroupMap.put(key, oneGroupList);
			}
			wordList.remove(0);
		}
	}

	public static void makeTrie() {
		if (SystemParas.cache_trie_enable
				&& new File(SystemParas.cache_trie_path).exists()) {
			LibraryManager.forest = (Forest) ObjectIoUtil
					.readObject(SystemParas.cache_trie_path);
		} else {
			loadLibrary(SystemParas.dic_path, "UTF-8");// 加载词典文件组,用空格隔开的多个文件即可
			getGroupMap();// 对所有的wordPojo按首字符分组
			Library.forest = LibraryManager.forest;
			Library.makeTrie(forest, wordGroupMap);// 构造trie树
			/**
			 * 构造完成后先缓存一份
			 */
			if (SystemParas.cache_trie_enable) {
				ObjectIoUtil.writeObject(SystemParas.cache_trie_path, forest);
			}
		}
	}

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		makeTrie();
		long end = System.currentTimeMillis();
		System.out.println((end - begin) / 1000);
		// System.out.println("wordList size()----" + wordList.size());
		// Set<String> keys = wordGroupMap.keySet();
		// for (String temp : keys) {
		// System.out.println(temp + "----" + wordGroupMap.get(temp).size());
		// for (WordPojo word : wordGroupMap.get(temp)) {
		// System.out.println("    " + word.getWord());
		// }
		// }
	}
}
