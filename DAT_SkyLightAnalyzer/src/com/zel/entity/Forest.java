package com.zel.entity;

import java.io.Serializable;

import com.zel.interfaces.WoodInterface;
import com.zel.util.SystemParas;

/**
 * 用双数组形式来构造的trie树结构，理论上有最快的机械分词的查询方式
 * 
 * @author zel
 * 
 */
public class Forest implements WoodInterface,Serializable {
	/**
	 * 作为双数组中的base和check双数组 都在最大的基础之上*2,是省去给每个不同字符找index的时间了,便于方便的插入操作 即以空间换时间
	 */
	public Branch[] base_array = new Branch[SystemParas.max_branch_array_length];
	public CheckPojo[] check_array = new CheckPojo[SystemParas.max_branch_array_length];

	public void insertBranch(Branch branch) {
		int position = branch.getPosition();
		// 首先判断要插入的节点是否会产生冲突，如果产生冲突，则要首先调整this对象
		Branch myBranch = base_array[position];
		// 首先判断该节点是否已存在
		if (myBranch == null) {// 说明该节点是第一次插入
			base_array[position] = branch;// 给该空白位置赋值
			check_array[position] = new CheckPojo(
					branch.getFrontPosition());
		} else {// 不是第一次插入
			switch (myBranch.getStatus()) {// 先判断在root中的status
			case 0:
				if (branch.getStatus() == 1) {// 现在已经是个词了
					myBranch.setStatus(2);
					myBranch.setParas(branch.getParas());
				}
				break;
			case 1:
				if (branch.getStatus() == 0) {// 原先是个词，现在新加的有延长，故设置为2
					myBranch.setStatus(2);
				}
				break;
			}
		}
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Branch getBranch(int index) {
		if (index >= SystemParas.max_branch_array_length) {
			return null;
		}
		return base_array[index];
	}

	public boolean isNull(int baseValue, int c) {
		return base_array[baseValue + c] == null;
	}

	@Override
	public int getBase_value() {
		return 0;
	}

	@Override
	public int getPosition() {
		return 0;
	}
}
