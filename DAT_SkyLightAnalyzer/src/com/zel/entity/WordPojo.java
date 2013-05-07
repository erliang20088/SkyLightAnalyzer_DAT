package com.zel.entity;

import java.io.Serializable;

public class WordPojo implements Comparable<WordPojo>,Serializable{
	public WordPojo(String word, String[] paras) {
		this.word = word;
		this.paras = paras;
	}

	private String word;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String[] getParas() {
		return paras;
	}

	public void setParas(String[] paras) {
		this.paras = paras;
	}

	private String[] paras;

	// 若有比较，则升序排列
	@Override
	public int compareTo(WordPojo o) {
		return this.getWord().compareTo(o.getWord());
	}
}
