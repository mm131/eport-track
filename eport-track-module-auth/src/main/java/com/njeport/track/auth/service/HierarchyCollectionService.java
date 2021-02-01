package com.njeport.track.auth.service;

import com.njeport.track.common.vo.HierarchyVO;

import java.util.List;

/**
 * @author kongming
 * @date 2019/12/29
 */
public interface HierarchyCollectionService {

    /**
     * 将集合加工为带有层次关系的新集合
     *
     * @param sourceList 源集合
     * @param <T>        对象类型
     * @return 新集合
     */
    <T extends HierarchyVO> List<T> buildHierarchyList(List<T> sourceList);
}
