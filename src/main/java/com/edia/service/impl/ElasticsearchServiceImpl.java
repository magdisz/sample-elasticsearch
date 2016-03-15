package com.edia.service.impl;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import com.edia.service.ElasticsearchService;

/**
 * Service to connect and provide Elasticsearch capabilities
 * 
 * @author magdisz
 *
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService{

	private Client client;

	public ElasticsearchServiceImpl() {
		Node node = nodeBuilder().node();
		client = node.client();
	}

	/**
	 * Create index with specified documentId and document data
	 * 
	 * @param index
	 * @param type
	 * @param documentId
	 * @param jsonDocumentData
	 */
	@Override
	public void addDocument(String index, String type, Long documentId, String jsonDocumentData) {
		try {
			if (documentId != null) {
				client.prepareIndex(index, type, documentId + "").setSource(jsonDocumentData).setRefresh(true).execute()
						.actionGet();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Multimatch search
	 * 
	 * @param searchWord
	 * @param sortByField
	 * @param sortOrder
	 * @param indexes
	 * @param types
	 * @return
	 */
	@Override
	public List<String> multiMatchSearch(String searchWord, String sortByField, SortOrder sortOrder, String indexes,
			String types) {
		SearchResponse response = client.prepareSearch(indexes).setTypes(types)
				.setQuery(QueryBuilders.multiMatchQuery(searchWord, types)).addSort(sortByField, sortOrder).execute()
				.actionGet();
		List<String> searchResults = new ArrayList<String>();
		for (SearchHit searchHit : response.getHits()) {
			String searchResult = searchHit.getSourceAsString();
			searchResults.add(searchResult);
		}
		return searchResults;
	}

}
