package com.liyz.cloud.common.elasticsearch.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

/**
 * 注释:es工具类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 11:40
 */
@Component
public class ElasticSearchUtil {

    private static Log logger;
    private static RestHighLevelClient client;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    public void init() {
        client = restHighLevelClient;
        logger = LogFactory.getLog(getClass());
    }

    /**
     * 保存/更新数据
     *
     * @param esBulkData
     * @param index
     */
    public static void saveEsDataWithBulk(Map<String, Map<String, Object>> esBulkData, String index) {
        BulkRequest bulkRequest = new BulkRequest();
        for(Map.Entry<String, Map<String, Object>> oneEsData : esBulkData.entrySet()){
            String id = oneEsData.getKey();
            Map<String, Object> esData = oneEsData.getValue();
            UpdateRequest request = new UpdateRequest(index, "_doc", id);
            request.doc(esData);
            request.docAsUpsert(true);
            bulkRequest.add(request);
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.info("save esData error:", e);
        }
    }

    /**
     * 查询数据
     *
     * @param source
     * @return
     */
    public static SearchResponse getEsData(SearchRequest source) {
        SearchResponse search = null;
        if (Objects.nonNull(source)) {
            try {
                search = client.search(source, RequestOptions.DEFAULT);
            } catch (Exception e) {
                logger.error("search es data error", e);
            }
        }
        return search;
    }

    /**
     * 删除数据
     *
     * @param deleteRequest
     * @return
     */
    public static DeleteResponse deleteEsData(DeleteRequest deleteRequest) {
        DeleteResponse response = null;
        try {
            response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("delete es data error", e);
        }
        return response;
    }

    /**
     * 删除数据
     *
     * @param deleteRequest
     * @return
     */
    public static BulkByScrollResponse deleteEsData(DeleteByQueryRequest deleteRequest) {
        BulkByScrollResponse response = null;
        try {
            response = client.deleteByQuery(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("delete es data error", e);
        }
        return response;
    }
}
