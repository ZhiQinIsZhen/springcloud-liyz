package com.liyz.cloud.service.es.repository;

import com.liyz.cloud.service.es.model.NearByDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/2/29 15:56
 */
public interface NearByRepository extends ElasticsearchRepository<NearByDO, String> {
}
