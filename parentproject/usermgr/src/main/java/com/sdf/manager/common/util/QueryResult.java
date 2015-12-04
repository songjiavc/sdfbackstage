package com.sdf.manager.common.util;

import java.util.List;

public class QueryResult<T> {
	/**
	 * 查询结果集
	 */
	private List<T> resultList;
	
	/**
	 * 总记录数
	 */
	private Long totalRecord;
	
	private int totalCount;
	
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public Long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
