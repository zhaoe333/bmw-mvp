package com.cm.common.tree;

import java.util.List;


/**
 * @version V1.0
 * @Title: Tree
 * @Description: 树形结构模型接口
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface Tree {
	/**
	 * 增加节点
	 * @param node
	 */
	void addNode(Node node);
	/**
	 * 增加多个节点
	 * @param nList
	 */
	void addNodes(List<Node> nList);
	/**
	 * 计算层级数
	 * @return
	 */
	int computeLevel();
	/**
	 * 计算叶节点数
	 * @return
	 */
	int computeLeafs();
	/**
	 * 计算某个节点下的叶节点数
	 * @param node
	 * @return
	 */
	int computeLeafs(Node node);
	/**
	 * 计算节点层级
	 * @param node
	 * @return
	 */
	int computeNodeLevel(Node node);
	/**
	 * 获取某个层级的所有节点
	 * @param level
	 * @return
	 */
	List getNodesByLevel(int level);
	/**
	 * 节点所在层的相对位置
	 * @param node
	 * @return
	 */
	int getIndexOfSibling(Node node);
	/**
	 * 获取所有叶节点
	 * @return
	 */
	List getLeafs();
	/**
	 * 根据编号获取节点
	 * @param id
	 * @return
	 */
	Node getNodeById(String id);
	/**
	 * 是否为叶节点
	 * @param node
	 * @return
	 */
	boolean isLeaf(Node node);
	/**
	 * 获取根节点
	 * @return
	 */
	Root getRoot();
	/**
	 * 设置根节点
	 * @param root
	 */
	void setRoot(Root root);
	/**
	 * 根据编号获取某节点下所有子节点
	 * @param id
	 * @return
	 */
	List getAllChildren(String id);
	
}
