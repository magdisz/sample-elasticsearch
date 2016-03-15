package com.edia.service;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;

/**
 * Elasticsearch service interface
 * 
 * @author magdisz
 *
 */
public interface ElasticsearchService {

	/**
	 * Add document with specified index, type and id
	 * @param index
	 * @param type
	 * @param documentId
	 * @param jsonDocumentData
	 */
	void addDocument(String index, String type, Long documentId, String jsonDocumentData);

	/**
	 * Multimatch search by field containing the searchWord
	 * @param searchWord
	 * @param sortByField
	 * @param sortOrder
	 * @param indexes
	 * @param types
	 * @return
	 */
	List<String> multiMatchSearch(String searchWord, String sortByField, SortOrder sortOrder, String indexes,
			String types);

}
