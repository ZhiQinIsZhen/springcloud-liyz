package com.liyz.cloud.common.elasticsearch.util;

import com.liyz.cloud.common.elasticsearch.config.ElasticSearchConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/11/29 13:45
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticSearchUtil {

    private static RestHighLevelClient getClient() {
        return ElasticSearchConfig.client;
    }

    public static void saveEsDataWithBulk(Map<String,Map<String, Object>> esBulkData, String index) {
        BulkRequest bulkRequest = new BulkRequest();
        for(Map.Entry<String, Map<String, Object>> oneEsData : esBulkData.entrySet()){
            String id = oneEsData.getKey();
            Map<String, Object> esData = oneEsData.getValue();
            UpdateRequest request = new UpdateRequest(index, id);
            request.doc(esData);
            request.docAsUpsert(true);
            bulkRequest.add(request);
        }
        try {
            getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("save esData error:", e);
        }
    }

    public static SearchResponse getEsData(SearchRequest source) {
        SearchResponse search = null;
        try {
            search = getClient().search(source, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("search es data error", e);
        }
        return search;
    }
}
