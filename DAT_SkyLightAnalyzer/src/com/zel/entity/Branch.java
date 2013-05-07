package com.zel.entity;

import java.io.Serializable;
import java.util.Arrays;

import com.zel.interfaces.WoodInterface;
import com.zel.util.SystemParas;

/**
 * 节点类,trie树的基本组成单位
 * 
 * @author zel
 */
public class Branch implements WoodInterface,Serializable {
	public Branch(char charNode, int position, int frontPosition,
			int base_value, int status, String[] paras) {
		this.charNode = charNode;
		this.position = position;
		this.frontPosition = frontPosition;
		this.base_value = base_value;
		this.status = status;
		this.paras = paras;
//		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Branch [base_value=" + base_value + ", charNode=" + charNode
				+ ", frontPosition=" + frontPosition + ", paras="
				+ Arrays.toString(paras) + ", position=" + position
				+ ", status=" + status + "]";
	}

	// 每个char对象的 int值，来唯一标识一个词中的字的char值
	private char charNode;

	public char getCharNode() {
		return charNode;
	}

	public void setCharNode(char charNode) {
		this.charNode = charNode;
	}

	public int getBase_value() {
		return base_value;
	}

	public void setBase_value(int baseValue) {
		base_value = baseValue;
	}

	// status=0代表只是个字; status=1代表是个词,并没有后续字符;status=2代表不仅是个独立的词，且是某个其它词的中间状态
	private int status;
	private String[] paras;
	private int base_value;
	private int frontPosition;

	public int getFrontPosition() {
		return frontPosition;
	}

	public void setFrontPosition(int frontPosition) {
		this.frontPosition = frontPosition;
	}

	private int position;// 标志每个branch所在的位置的数量表示

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String[] getParas() {
		return paras;
	}

	public void setParas(String[] paras) {
		this.paras = paras;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public Branch getBranch(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBranch(Branch branch) {
		// TODO Auto-generated method stub
		
	}
}
