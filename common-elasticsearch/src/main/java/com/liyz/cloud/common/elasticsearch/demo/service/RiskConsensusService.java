package com.liyz.cloud.common.elasticsearch.demo.service;

import com.liyz.cloud.common.elasticsearch.demo.domain.EsRiskConsensusDO;
import com.liyz.cloud.common.elasticsearch.demo.repository.RiskConsensusRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/13 15:08
 */
@Slf4j
@Service
public class RiskConsensusService {

    @Autowired
    RiskConsensusRepository riskConsensusRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public int save(List<EsRiskConsensusDO> list) {
        Iterable<EsRiskConsensusDO> iterable = riskConsensusRepository.saveAll(list);

        return iterable.hashCode();
    }

    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsRiskConsensusDO> doList = new ArrayList<>(ids.size());
            for (Long id : ids) {
                EsRiskConsensusDO esRiskConsensusDO = new EsRiskConsensusDO();
                esRiskConsensusDO.setId(id);
                doList.add(esRiskConsensusDO);
            }
            riskConsensusRepository.deleteAll(doList);
        }
    }

    public Page<EsRiskConsensusDO> search(String keyword, Integer pageNum, Integer pageSize, String sentimentType, String sourceType) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //过滤
        if (StringUtils.isNotBlank(sentimentType)) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            String[] types = sentimentType.split(",");
            BoolQueryBuilder sourceTerm = QueryBuilders.boolQuery();
            for (String type : types) {
                sourceTerm.should(QueryBuilders.termQuery("sentimentType", type));
            }
            boolQueryBuilder.must(sourceTerm);
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }
        if (StringUtils.isNotBlank(sourceType)) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            String[] types = sourceType.split(",");
            BoolQueryBuilder sourceTerm = QueryBuilders.boolQuery();
            for (String type : types) {
                sourceTerm.should(QueryBuilders.termQuery("sourceType", type));
            }
            boolQueryBuilder.must(sourceTerm);
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }
        if (StringUtils.isNotBlank(keyword)) {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("title", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("context", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(2)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        //排序
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("publishTime").order(SortOrder.DESC));
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        log.info("searchQuery : {}", searchQuery.getQuery().toString());
        return riskConsensusRepository.search(searchQuery);
    }
}
