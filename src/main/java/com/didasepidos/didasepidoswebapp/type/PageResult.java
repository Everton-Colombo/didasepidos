package com.didasepidos.didasepidoswebapp.type;

import java.util.List;

public class PageResult<T> {
	
	private List<T> pageResultList;
	private int totalResultCount;
	private int currentPage;
	private int pageCount;
	
	public PageResult(List<T> pageResultList, int totalResultCount, int currentPage, int pageCount) {
		super();
		this.pageResultList = pageResultList;
		this.totalResultCount = totalResultCount;
		this.currentPage = currentPage;
		this.pageCount = pageCount;
	}
	
	public PageResult() {}

	public List<T> getPageResultList() {
		return pageResultList;
	}

	public void setPageResultList(List<T> pageResultList) {
		this.pageResultList = pageResultList;
	}

	public int getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + pageCount;
		result = prime * result + ((pageResultList == null) ? 0 : pageResultList.hashCode());
		result = prime * result + totalResultCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		PageResult other = (PageResult) obj;
		if (currentPage != other.currentPage)
			return false;
		if (pageCount != other.pageCount)
			return false;
		if (pageResultList == null) {
			if (other.pageResultList != null)
				return false;
		} else if (!pageResultList.equals(other.pageResultList))
			return false;
		if (totalResultCount != other.totalResultCount)
			return false;
		return true;
	}
}
