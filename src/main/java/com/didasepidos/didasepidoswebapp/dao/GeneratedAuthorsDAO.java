package com.didasepidos.didasepidoswebapp.dao;

import com.didasepidos.didasepidoswebapp.entity.AuthorIdGenerationRecord;

public interface GeneratedAuthorsDAO {
	public int getGeneratedAuthorIdCountFromOrigin(String origin);
	public int getGeneratedAuthorIdCountFromOriginFromLastXDays(String origin, int x);
	public void insertAuthorIdGenerationRecord(AuthorIdGenerationRecord record);
}
