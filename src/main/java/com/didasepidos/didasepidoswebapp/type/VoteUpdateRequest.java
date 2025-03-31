package com.didasepidos.didasepidoswebapp.type;

public class VoteUpdateRequest {
	
	private String checkboxName;
	private boolean likeChecked;
	private boolean dislikeChecked;
	
	public VoteUpdateRequest() {}

	public VoteUpdateRequest(String checkboxName, boolean likeChecked, boolean dislikeChecked) {
		super();
		this.checkboxName = checkboxName;
		this.likeChecked = likeChecked;
		this.dislikeChecked = dislikeChecked;
	}

	public String getCheckboxName() {
		return checkboxName;
	}

	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}

	public boolean isLikeChecked() {
		return likeChecked;
	}

	public void setLikeChecked(boolean likeChecked) {
		this.likeChecked = likeChecked;
	}

	public boolean isDislikeChecked() {
		return dislikeChecked;
	}

	public void setDislikeChecked(boolean dislikeChecked) {
		this.dislikeChecked = dislikeChecked;
	}
	
	public int getReviewId() {
		return Integer.parseInt(this.checkboxName.replaceAll("\\D+",""));
	}
}
