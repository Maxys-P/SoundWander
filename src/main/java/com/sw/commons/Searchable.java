package com.sw.commons;

import java.util.List;

public interface Searchable {
    List<Object> search(SearchCriteria criteria);
}
