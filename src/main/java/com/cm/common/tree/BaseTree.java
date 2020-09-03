package com.cm.common.tree;

import com.cm.common.utils.LogicUtil;
import com.cm.common.utils.StringUtil;

import java.util.*;

/**
 * @version V1.0
 * @Title: BaseTree
 * @Description: 树形结构基本模型
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class BaseTree implements Tree {
	/**
	 * 节点集合
	 */
	private Map<String, Node> nodes = new HashMap();
	/**
	 * 根节点
	 */
	protected Root root = new Root();
	/**
	 * 总层级数
	 */
	protected int levelNum;
	/**
	 * 总叶节点数
	 */
	protected int leafNum;
	
	public void addNode(Node node) {
		if (node != null && LogicUtil.isNotNull(node.getId())) {
			nodes.put(node.getId(), node);
			Node parent=getNodeById(node.getParent());
			if (parent==null) {
				root.getChildren().add(node);
				node.setLevel(1);
			} else {
				parent.setLeaf(false);
				parent.getChildren().add(node);
				node.setLevel(parent.getLevel()+1);
			}
		}
	}

	public void addNodes(List<Node> nList) {
		if (nList == null)
			return;
		for (int i = 0; i < nList.size(); i++) {
			Node n = nList.get(i);
			if (n != null && LogicUtil.isNotNull(n.getId())) {
				nodes.put(n.getId(),n);
			}
		}
		for(int i=0;i<nList.size();i++){
			Node n = nList.get(i);
			if (n != null && LogicUtil.isNotNull(n.getId())) {
				Node parent=getNodeById(n.getParent());
				if(parent==null){
					root.getChildren().add(n);
				}else{
					parent.setLeaf(false);
					parent.getChildren().add(n);
				}
			}
		}
	}
	public void setLevel(){
		setLevel(root);
	}
	
	public void setLevel(Node node){
		List children=node.getChildren();
		for(int i=0;i<children.size();i++){
			Node n=(Node)children.get(i);
			n.setLevel(node.getLevel()+1);
			if(n.hasChildren()) setLevel(n);
		}
	}
	public void removeNode(String id) {
		Node n=getNodeById(id);
		if(n!=null){
			List children=getAllChildren(id);
			for(int i=0;i<children.size();i++){
				Node sub=(Node)children.get(i);
				nodes.remove(sub.getId());
			}
			nodes.remove(n.getId());
			Node p=getNodeById(n.getParent());
			if(p!=null){
				List subs=p.getChildren();
				subs.remove(n);
			}else{
				getRoot().getChildren().remove(n);
			}
		}
	}
	public int computeLevel() {
		int level = 0;
		Iterator it = nodes.keySet().iterator();
		while (it.hasNext()) {
			Node n = nodes.get((String) it.next());
			int l = computeNodeLevel(n, 1);
			if (l > level)
				level = l;
		}
		this.levelNum = level;
		return level;
	}

	public int computeNodeLevel(Node node, int level) {
		if (node.hasParent()) {
			level += 1;
			return computeNodeLevel(getNodeById(node.getParent()), level);
		} else {
			return level;
		}
	}

	public int computeNodeLevel(Node node) {
		return computeNodeLevel(node, 1);
	}
	public int computeLeafs() {
		int count = 0;
		Iterator it = nodes.keySet().iterator();
		while (it.hasNext()) {
			Node n = nodes.get((String) it.next());
			if (n.isLeaf()) {
				count++;
			}
		}
		return count;
	}

	public int computeLeafs(Node node) {
		int count = 0;
		if (node.isLeaf())
			return 0;
		List<Node> children = node.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Node n = children.get(i);
			if (n.isLeaf()) {
				count += 1;
			} else {
				count += computeLeafs(n);
			}
		}
		return count;
	}

	public List getNodesByLevel(int level) {
		List list = new ArrayList();
		List<Node> level1 = getRoot().getChildren();
		if (level == 1) {
			return level1;
		} else {
			for (int i = 0; i < level1.size(); i++) {
				Node node = level1.get(i);
				getNodesByLevel(node, list, level);
			}
		}
		return list;
	}

	public void getNodesByLevel(Node node, List list, int level) {
		int l = computeNodeLevel(node);
		if (l == level) {
			list.add(node);
		} else if (l < level) {
			List<Node> c = node.getChildren();
			for (int i = 0; i < c.size(); i++) {
				getNodesByLevel(c.get(i), list, level);
			}
		}
	}
	
	public List getLeafs() {
		List leafs = new ArrayList();
		List<Node> level1 = getRoot().getChildren();
		for (int i = 0; i < level1.size(); i++) {
			Node node = level1.get(i);
			getLeafs(node, leafs);
			// getNodesByLevel(node,list,level);
		}
		return leafs;
	}

	public void getLeafs(Node node, List leafs) {
		if (node.isLeaf()) {
			leafs.add(node);
		} else {
			List list = node.getChildren();
			for (int i = 0; i < list.size(); i++) {
				Node n = (Node) list.get(i);
				getLeafs(n, leafs);
			}
		}
	}
	public List getAllChildren(String id){
		List list=new ArrayList();
		Node node=getNodeById(id);
		list.add(node);		
		getAllChildren(list,node);
		return list;
	}
	
	public void getAllChildren(List list, Node node){
		if(node.hasChildren()){
			List subs=node.getChildren();
			for(int i=0;i<subs.size();i++){
				Node n=(Node)subs.get(i);
				list.add(n);
				getAllChildren(list,n);
			}
		}
	}
	public int getIndexOfSibling(Node node) {
		Node parent = null;
		if (LogicUtil.isNotNull(node.getParent())) {
			parent = getNodeById(node.getParent());
		} else {
			parent = getRoot();
		}
		List<Node> children = parent.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Node n = children.get(i);
			if (StringUtil.isEqual(n.getId(), node.getId())) {
				return i + 1;
			}
		}
		return 1;
	}
	public List getAllParent(Node node){
		List list=new ArrayList();
		getAllParent(list,node);
		return list;
	}
	
	public List getAllParent(String id){
		Node node=getNodeById(id);
		if(node!=null){
			return getAllParent(node);
		}else{
			return new ArrayList();
		}
	}
	
	private void getAllParent(List list, Node node){
		Node parent=getNodeById(node.getParent());
		if(parent!=null){
			list.add(0,parent);
			getAllParent(list,parent);
		}else{
			list.add(0,getRoot());
		}
	}
	
	public boolean isLeaf(Node node) {
		int currentLevel = computeLeafs(node);
		if(currentLevel == 0) return true;
		return false;
	}

	public int getIndexOfLevel(Node node) {
		return 0;
	}

	public Node getNodeById(String id) {
		return nodes.get(id);
	}

	public Map<String, Node> getNodes() {
		return nodes;
	}

	public void setNodes(Map<String, Node> nodes) {
		if(null != nodes && nodes.size() > 0){
			for(String key : nodes.keySet()){
				Node node = nodes.get(key);
				if (node != null && LogicUtil.isNotNull(node.getId())) {
					Node parent=getNodeById(node.getParent());
					if (parent==null) {
						root.getChildren().add(node);
						node.setLevel(1);
					} else {
						parent.setLeaf(false);
						parent.getChildren().add(node);
						node.setLevel(parent.getLevel()+1);
					}
				}
			}
		}
		this.nodes = nodes;
	}

	public Root getRoot() {
		return root;
	}

	public void setRoot(Root root) {
		this.root = root;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	public int getLeafNum() {
		return leafNum;
	}

	public void setLeafNum(int leafNum) {
		this.leafNum = leafNum;
	}
	
}
