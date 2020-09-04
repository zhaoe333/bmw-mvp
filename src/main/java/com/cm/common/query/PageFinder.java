package com.cm.common.query;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageFinder<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("每页的记录数")
	private int pageSize = 10;

	@ApiModelProperty("当前页中存放的数据")
	private List<T> data;

	@ApiModelProperty("总记录数")
	private int rowCount;

	@ApiModelProperty("页数")
	private int pageCount;

	@ApiModelProperty("当前页")
	private int page;

	@ApiModelProperty("是否有上一页")
	private boolean hasPrevious = false;

	@ApiModelProperty("是否有下一页")
	private boolean hasNext = false;

	/**
	 * 只有使用PageFinder(Query query, List<T> data)构造方法的时候有效
	 */
	@ApiModelProperty("总条数增量")
	private Integer totalIncrement;

	public PageFinder(int page, int rowCount) {
		this.page = page;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		refresh();
	}

	/**
	 * 构造方法
	 */
	public PageFinder(int page, int pageSize, int rowCount) {
		this.page = page;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		refresh();
	}

	/**
	 * 
	 */
	public PageFinder(int page, int pageSize, int rowCount, List<T> data) {
		this.page = page;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		this.data = data;
		refresh();
	}
	
	public PageFinder(Query query, List<T> data) {
		this.page = query.getPageNo();
		this.pageSize = query.getPageSize();
		this.rowCount = query.getTotal();
		this.pageCount = getTotalPageCount();
		this.data = data;
		if (query.getLastTotal() != null) {
			this.totalIncrement = query.getTotal() - query.getLastTotal();
		}
		refresh();
	}

	public PageFinder(Page<T> listPage) {
		this.page = listPage.getPageNum();
		this.pageSize = listPage.getPageSize();
		this.rowCount = (int)listPage.getTotal();
		this.pageCount = listPage.getPages();
		this.data = listPage.getResult();
		refresh();
	}
	
	/**
	 * 取总页数
	 */
	private int getTotalPageCount() {
		if (rowCount % pageSize == 0)
			return rowCount / pageSize;
		else
			return rowCount / pageSize + 1;
	}

	/**
	 * 刷新当前分页对象数据
	 */
	private void refresh() {
		if (pageCount <= 1) {
			hasPrevious = false;
			hasNext = false;
		} else if (page == 1) {
			hasPrevious = false;
			hasNext = true;
		} else if (page == pageCount) {
			hasPrevious = true;
			hasNext = false;
		} else {
			hasPrevious = true;
			hasNext = true;
		}
	}
	
	public static <K> PageFinder<K> bulid(int page, int pageSize, List<K> data)throws Exception{
		List<K> list = new ArrayList<K>();
		for(int i=0;i<pageSize&&i<data.size();i++){
			list.add(data.get((page-1)*pageSize+i));
		}
		return new PageFinder<K>(page,pageSize,data.size(),list);
	}
}
