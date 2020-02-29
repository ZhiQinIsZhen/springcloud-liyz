package com.liyz.cloud.service.es.service;

import com.liyz.cloud.common.model.bo.page.PageBaseBO;
import com.liyz.cloud.service.es.model.NearByDO;
import com.liyz.cloud.service.es.repository.NearByRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/2/29 16:00
 */
@Slf4j
@Service
public class NearByService {

    @Autowired
    NearByRepository nearByRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public Page<NearByDO> search(PageBaseBO pageBaseBO) {
        Pageable pageable = PageRequest.of(pageBaseBO.getPageNum(), pageBaseBO.getPageSize());
        return nearByRepository.findAll(pageable);
    }
}
