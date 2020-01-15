package com.liyz.cloud.common.elasticsearch.demo.repository;

import com.liyz.cloud.common.elasticsearch.demo.domain.EsRiskConsensusDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/1/13 15:23
 */
public interface RiskConsensusRepository extends ElasticsearchRepository<EsRiskConsensusDO, Long> {


}
