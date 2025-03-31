package com.didasepidos.didasepidoswebapp.dao;

public enum OrderByField {
	NAME_ASC(" ORDER BY name ASC"), NAME_DESC(" ORDER BY name DESC"),
	DATETIME_ASC(" ORDER BY datetime ASC"), DATETIME_DESC(" ORDER BY datetime DESC"),
	LIKES_ASC(" ORDER BY likes ASC"), LIKES_DESC(" ORDER BY likes DESC"),
	DISLIKES_ASC(" ORDER BY dislikes ASC"), DISLIKES_DESC(" ORDER BY dislikes DESC"),
	LIKE_DISLIKE_RATIO_ASC(" ORDER BY (likes/dislikes) ASC"), LIKE_DISLIKE_RATIO_DESC(" ORDER BY (likes/dislikes) DESC");
	
	public final String sqlString;
	
	private OrderByField(String sqlString) {
		this.sqlString = sqlString;
	}
}
