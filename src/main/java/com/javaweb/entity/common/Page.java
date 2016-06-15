package com.javaweb.entity.common;

import java.io.Serializable;

/** hibernate写法
@Entity@Table(name="user")//单元测试用
@Entity(name="user")//项目部署用
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="id",nullable=false)
private int id;
*/
public class Page implements Serializable {
	
	private static final long serialVersionUID = -4011563775309984811L;

	private long currentPage;//当前页码
	
	private long totalCount;//总记录数
	
	private long totalPage;//总页码数
	
	private long pageSize;//每页显示的记录数
	
	private long currentCount;//当前记录数

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(long currentCount) {
		this.currentCount = currentCount;
	}
	
}
