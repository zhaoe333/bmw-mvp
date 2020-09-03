package com.cm.common.tree;

import com.cm.common.utils.LogicUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Title: Node
 * @Description: 树节点模型
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class Node {
	/**
	 * 节点编号
	 */
	private String id;
	/**
	 * 节点标题
	 */
	private String title;
	/**
	 * 上级节点
	 */
	private String parent;
	/**
	 * 子节点
	 */
	private List<Node> children=new ArrayList();
	/**
	 * 叶节点标识
	 */
	boolean leaf=true;
	/**
	 * 所在层级
	 */
	private int level;

	/**
	 * 顺序
	 */
	private Integer sort;

	public Node(){		
	}
	public Node(String id){
		this.id=id;
	}
	public Node(String id,String title){
		this.id=id;
		this.title=title;	
	}
	public Node(String id,String title,String parent){
		this.id=id;
		this.title=title;
		this.parent=parent;
	}
	public boolean hasChildren(){
		return getChildren().size()>0;
	}
	
	public boolean hasParent(){
		return LogicUtil.isNotNull(getParent());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.leaf = isLeaf;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
