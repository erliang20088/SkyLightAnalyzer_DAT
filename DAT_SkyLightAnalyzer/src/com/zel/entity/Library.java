package com.zel.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.zel.util.SystemParas;

public class Library {
	// 做日志用
	private static Logger logger = Logger.getLogger(Library.class);

	public static Forest forest = null;// 由于Obejct序列化必须是非静态字段，故将Forest中的base、check都改为非静态，特在此声明一下

	public static int index = 0;
	public static int word_length = 0;
	public static int word_index = 0;

	public static int temp_index = 0;
	public static int temp_frontPosition = 0;
	public static List<WordPojo> delList = new ArrayList<WordPojo>();// 暂存待删除集合

	// 真正开始构造trie树
	public static void makeTrie(Forest forest,
			Map<String, List<WordPojo>> wordGroupMap) {
		Set<String> keys = wordGroupMap.keySet();
		int count=keys.size();
		System.out.println("有key共"+count);
		count=1;
		for (String temp_key : keys) {// 每个key都是一组以该key为首字符的一个word集合
			List<WordPojo> word_list = wordGroupMap.get(temp_key);
			List<WordPojo> word_list_copy = new ArrayList<WordPojo>(word_list);
			insertWordGroup(forest, temp_key, word_list_copy, 0);// 以组为单位插入
			System.out.println("已构建完第---"+(count++));
		}
	}

	// 以组为单位构造trie
	// 此处的postion指定位到word的哪个第几个位置
	public static void insertWordGroup(Forest forest, String key,
			List<WordPojo> word_list_copy, int position) {
//		System.out.println("*******");
		Map<String, List<WordPojo>> subWordGroupMap = new HashMap<String, List<WordPojo>>();// 暂存每个小组集合的map对象
		int temp_first_position_value = 0;// 暂存key对应的位置上的value值
		// 将new_word_list拆分成以position不同，依次递近的小word_list数组
		String sub_key = null;
		for (WordPojo temp_pojo : word_list_copy) {
			if (temp_pojo.getWord().length() >= position + 1) {
				sub_key = temp_pojo.getWord().substring(0, position + 1);// 取到每个键
				// 将得到的每个wordPojo对象放到wordGroupMap中去
				if (subWordGroupMap.containsKey(sub_key)) {
					subWordGroupMap.get(sub_key).add(temp_pojo);
				} else {
					List<WordPojo> oneGroupList = new ArrayList<WordPojo>();
					oneGroupList.add(temp_pojo);
					subWordGroupMap.put(sub_key, oneGroupList);
				}
			}
		}
		// 将数组拆分结束
		// 遍历subWordGroupMap里边的每个List，依次构造进trie树中
		Set<String> sub_keys = subWordGroupMap.keySet();
		for (String temp_key : sub_keys) {// 每个key都是一组以该key为首字符的一个word集合
			List<WordPojo> sub_word_list = subWordGroupMap.get(temp_key);
			// 每个小组的处理开始
			boolean isFound_Base_Value = false;
			int temp_base_init_value = SystemParas.base_init_value;
			while (!isFound_Base_Value) {
				for (WordPojo pojo : sub_word_list) {
					if (pojo.getWord().length() >= position + 2) {
						if (!forest.isNull(temp_base_init_value, pojo.getWord()
								.charAt(position + 1))) {
							isFound_Base_Value = false;
							break;
						} else {
							isFound_Base_Value = true;
						}
					} else {
						isFound_Base_Value = true;
					}
				}
				if (isFound_Base_Value) {
					break;
				} else {
					temp_base_init_value = temp_base_init_value
							+ SystemParas.conflict_add_number;
				}
			}

			// 如果是首字符则直接插入根branch中即可
			if (position == 0) {
				for (WordPojo pojo : sub_word_list) {
					if (pojo.getWord().length() == position + 1) {
						forest.insertBranch(new Branch(key.charAt(position),
								key.charAt(position), position,
								temp_base_init_value, 1, pojo.getParas()));
						delList.add(pojo);// 如果等于1，即为一个字直接去掉，其余部分进入下一轮循环
					} else {
						forest.insertBranch(new Branch(key.charAt(position),
								key.charAt(position), position,
								temp_base_init_value, 0, null));
					}
				}
			} else {
				temp_first_position_value = key.charAt(0);
				// 此步最重要为如何得前一个位置frontPosition
				for (WordPojo pojo : sub_word_list) {
					for (temp_index = 0; temp_index < position; temp_index++) {
						if (position == 1) {
							temp_frontPosition = key.charAt(0);
							break;
						} else {
							if (temp_index >= 1) {
								temp_frontPosition = forest.base_array[temp_first_position_value]
										.getBase_value()
										+ pojo.getWord().charAt(temp_index);
								temp_first_position_value = temp_frontPosition;
							}
						}
					}
					if (pojo.getWord().length() == position + 1) {
						forest.insertBranch(new Branch(pojo.getWord().charAt(
								position),
								forest.base_array[temp_frontPosition]
										.getBase_value()
										+ pojo.getWord().charAt(position),
								temp_frontPosition, temp_base_init_value, 1,
								pojo.getParas()));
						delList.add(pojo);// 如果等于1，即为一个字直接去掉，其余部分进入下一轮循环
					} else {
						forest.insertBranch(new Branch(pojo.getWord().charAt(
								position),
								forest.base_array[temp_frontPosition]
										.getBase_value()
										+ pojo.getWord().charAt(position),
								temp_frontPosition, temp_base_init_value, 0,
								null));
					}
					// 清空临时变量
					temp_first_position_value = key.charAt(0);
					temp_frontPosition = 0;
				}
			}
			word_list_copy.removeAll(delList);
			sub_word_list.removeAll(delList);
			delList.clear();
			// 每个小组的处理完一遍

			// 在此做是否对每一个子集合做递归操作
			if(sub_word_list.size()>0){
				insertWordGroup(forest,key, sub_word_list,(position+1));	
			}
		}
		subWordGroupMap.clear();// 清空一下用来存子集合的map对象
		// 如果集合中依然有没有完成的词条，则继续进行
		if (word_list_copy.size() > 0) {
			insertWordGroup(forest, key, word_list_copy, ++position);
		}
	}

	// 专门用来执行完成一个分成的小组
	public static void insertWordPojoCompletely(Forest forest, String key,
			WordPojo wordPojo, int position) {
		// 将一个wordPojo完全加入到trie树中
		boolean isFound_Base_Value = false;
		int temp_base_init_value = SystemParas.base_init_value;
		int temp_first_position_value = 0;// 暂存key对应的位置上的value值
		while (!isFound_Base_Value) {
			if (wordPojo.getWord().length() >= position + 2) {
				if (!forest.isNull(temp_base_init_value, wordPojo.getWord()
						.charAt(position + 1))) {
					isFound_Base_Value = false;
					temp_base_init_value = temp_base_init_value
							+ SystemParas.conflict_add_number;
					continue;
				} else {
					isFound_Base_Value = true;
				}
			} else {
				isFound_Base_Value = true;
			}
		}
		// 如果是首字符则直接插入根branch中即可
		if (position == 0) {
			if (wordPojo.getWord().length() == position + 1) {
				forest.insertBranch(new Branch(key.charAt(position), key
						.charAt(position), position, temp_base_init_value, 1,
						wordPojo.getParas()));
			} else {
				forest.insertBranch(new Branch(key.charAt(position), key
						.charAt(position), position, temp_base_init_value, 0,
						null));
			}
		} else {
			temp_first_position_value = key.charAt(0);
			// 此步最重要为如何得前一个位置frontPosition
			for (temp_index = 0; temp_index < position; temp_index++) {
				if (position == 1) {
					temp_frontPosition = key.charAt(0);
					break;
				} else {
					if (temp_index >= 1) {
						temp_frontPosition = forest.base_array[temp_first_position_value]
								.getBase_value()
								+ wordPojo.getWord().charAt(temp_index);
						temp_first_position_value = temp_frontPosition;
					}
				}
			}
			if (wordPojo.getWord().length() == position + 1) {
				forest.insertBranch(new Branch(wordPojo.getWord().charAt(
						position), forest.base_array[temp_frontPosition]
						.getBase_value()
						+ wordPojo.getWord().charAt(position),
						temp_frontPosition, temp_base_init_value, 1, wordPojo
								.getParas()));
			} else {
				forest.insertBranch(new Branch(wordPojo.getWord().charAt(
						position), forest.base_array[temp_frontPosition]
						.getBase_value()
						+ wordPojo.getWord().charAt(position),
						temp_frontPosition, temp_base_init_value, 0, null));
			}
			// 清空临时变量
			temp_frontPosition = 0;
		}
		if (wordPojo.getWord().length() != position + 1) {
			insertWordPojoCompletely(forest, key, wordPojo, ++position);
		}
	}

	public static void main(String[] args) {
	}
}
