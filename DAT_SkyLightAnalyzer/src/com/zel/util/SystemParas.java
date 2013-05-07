package com.zel.util;

public class SystemParas {
	/**
	 * trie树中的最大节点数目,*2是要将前一半分给第一个char,后一半用做非第一个字的存储，
	 * 这样可以避免因为第一个char的问题去反复定位每个字的index, 现在直接将char的int值做为该char的数组中的index值
	 */
	public static int max_branch_array_length = Character.MAX_VALUE * 10;
	// 遇到冲突后要对base值进行加10操作,循环判断直到所有的keyword中的char都能唯一放下
	public static int conflict_add_number = 10;
	/**
	 * 刚开始时候第一个base的值,即base[index]=base_init_value
	 * 从max_branch_array_length开始时,是为了排除与首字符的冲突
	 */
	public static int base_init_value = Character.MAX_VALUE;
	// 刚开始时候第一个check的值,即check[index]=check_init_value
	public static int check_init_value = 0;

	/**
	 * 读取配置文件的参数
	 */
	public static String dic_path = ReadConfigUtil.getValue("dic.path");// 读词典文件所在文件路径
	public static String cache_trie_path = ReadConfigUtil
			.getValue("cache.trie.path");// 读词典文件所在文件路径
	/**
	 * 为了测试方便，配置下是否启用cache trie
	 */
	public static boolean cache_trie_enable = Boolean
			.parseBoolean(ReadConfigUtil.getValue("cache.trie.enable"));

}
