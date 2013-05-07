package com.test;

import java.util.List;

import junit.framework.TestCase;

import com.zel.core.SkyLightAnalyzer;
import com.zel.manager.LibraryManager;

public class MainTest extends TestCase {
	// 得到分出来的词，相当于词抽取
	public void testgetSplitWordsBySingleDoc() {
		long begin = System.currentTimeMillis();
		LibraryManager.makeTrie();
		long end = System.currentTimeMillis();
		System.out.println("加载词典用时---" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		String content = "我认为中文分词是自然语言处的最关键一步，不知道各位同学怎么着看？";

		begin = System.currentTimeMillis();
		SkyLightAnalyzer analyzer = new SkyLightAnalyzer(LibraryManager.forest,
				content);
		List<String> list = analyzer.getSplitWords();
		end = System.currentTimeMillis();
		System.out.println("共用时" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		System.out.println("content length----" + content.length());
		for (String temp : list) {
			System.out.println(temp);
		}
	}

	public void testgetSplitWordsByBigDoc() {
		long begin = System.currentTimeMillis();
		LibraryManager.makeTrie();
		long end = System.currentTimeMillis();
		System.out.println("加载词典用时---" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		String temp_content = "香港中国企业协会，不足为外人道木盂子镇，伏尔塔瓦河，日本电话电报公司,也许你是一个特别好的人，但是天和地也会生气的，你知道吗?这是一个ictclas的java实现.基本上重写了所有的数据结构和算法.词典是用的开源版的ictclas所提供的.并且进行了部分的人工优化"
				+ "内存中中文分词每北大国际关系学院秒钟大约100万字(速度上已经超越ictclas)"
				+ "文件读取分词每秒钟大约30万字"
				+ "准确率能达到96%以上"
				+ "目前实现了.中文分词. 老弱病残者中文姓名识别 . 用户自定义词典"
				+ "可以应用到自然语言香港友联银行处理等方面,适用于对分词效果要求搞的各种项目.不足为外人道";
		begin = System.currentTimeMillis();
		String content = temp_content;
		for (int i = 0; i < 10000; i++) {
			content += temp_content;
		}
		end = System.currentTimeMillis();

		System.out.println("组合成大文本共用时" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");

		int repeat = 10;
		begin = System.currentTimeMillis();
		for (int i = 0; i < repeat; i++) {
			SkyLightAnalyzer analyzer = new SkyLightAnalyzer(
					LibraryManager.forest, content);
			List<String> list = analyzer.getSplitWords();
		}
		end = System.currentTimeMillis();
		System.out.println("分词共用时" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		System.out.println("content length----" + content.length() * repeat);
	}

	// 得到该句话所有分出来的元素
	public void testgetSplitResultBySingleDoc() {
		long begin = System.currentTimeMillis();
		LibraryManager.makeTrie();
		long end = System.currentTimeMillis();
		System.out.println("加载词典用时---" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		String content = "我认为中文分词是自然语言处的最关键一步，不知道各位同学怎么着看？";

		begin = System.currentTimeMillis();
		SkyLightAnalyzer analyzer = new SkyLightAnalyzer(LibraryManager.forest,
				content);
		List<String> list = analyzer.getSplitResult();
		end = System.currentTimeMillis();
		System.out.println("共用时" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		System.out.println("content length----" + content.length());
		for (String temp : list) {
			System.out.print(temp + " /");
		}
		System.out.println();
	}

	public void testgetSplitResultByBigDoc() {
		long begin = System.currentTimeMillis();
		LibraryManager.makeTrie();
		long end = System.currentTimeMillis();
		System.out.println("加载词典用时---" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		String temp_content = "香港中国企业协会，不足为外人道木盂子镇，伏尔塔瓦河，日本电话电报公司,也许你是一个特别好的人，但是天和地也会生气的，你知道吗?这是一个ictclas的java实现.基本上重写了所有的数据结构和算法.词典是用的开源版的ictclas所提供的.并且进行了部分的人工优化"
				+ "内存中中文分词每北大国际关系学院秒钟大约100万字(速度上已经超越ictclas)"
				+ "文件读取分词每秒钟大约30万字"
				+ "准确率能达到96%以上"
				+ "目前实现了.中文分词. 老弱病残者中文姓名识别 . 用户自定义词典"
				+ "可以应用到自然语言香港友联银行处理等方面,适用于对分词效果要求搞的各种项目.不足为外人道";
		begin = System.currentTimeMillis();
		String content = temp_content;
		for (int i = 0; i < 10000; i++) {
			content += temp_content;
		}
		end = System.currentTimeMillis();

		System.out.println("组合成大文本共用时" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");

		int repeat = 10;
		begin = System.currentTimeMillis();
		for (int i = 0; i < repeat; i++) {
			SkyLightAnalyzer analyzer = new SkyLightAnalyzer(
					LibraryManager.forest, content);
			List<String> list = analyzer.getSplitResult();
		}
		end = System.currentTimeMillis();
		System.out.println("分词共用时" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		System.out.println("content length----" + content.length() * repeat);
	}

	public static void main(String[] args) throws Exception {
		long begin = System.currentTimeMillis();
		LibraryManager.makeTrie();
		long end = System.currentTimeMillis();
		System.out.println("加载词典用时---" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		String content = "也许中国是个有悠久历史的国家，但是很多现在的新技术并没有西方世界那么发达，尤其是IT业尤为明显。"+
		"IT之软件是个开源的趋势，谁也阴挡不了，但是国内仍处于缓慢发展期，闭门造车毕竟太受限，共同加入共享开源的大潮吧!";
		begin = System.currentTimeMillis();
		SkyLightAnalyzer analyzer = new SkyLightAnalyzer(LibraryManager.forest,
				content);
		List<String> list = analyzer.getSplitWords();
		end = System.currentTimeMillis();
		System.out.println("共用时" + ((end - begin) / 1000) + "."
				+ ((end - begin) % 1000) + "s");
		System.out.println("content length----" + content.length());
		for (String temp : list) {
			System.out.print(temp + "    ");
		}
		// List<String> list = analyzer.getSplitResult();
		// for (String temp : list) {
		// System.out.print(temp+" ");
		// }
		// System.out.println();
		// System.out.println("分词完成!");
		// double d=28502850/1.673;
		// System.out.println(d);
	}
}
