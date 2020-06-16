package com.liyz.cloud.service.es.repository;

import com.liyz.cloud.service.es.model.JudgementResultDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/6/15 16:19
 */
public interface JudgementResultRepository extends ElasticsearchRepository<JudgementResultDO, String> {
}
