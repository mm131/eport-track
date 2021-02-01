package com.njeport.track.auth.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.njeport.track.auth.service.HierarchyCollectionService;
import com.njeport.track.common.constants.StringConstants;
import com.njeport.track.common.vo.HierarchyVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Service
public class HierarchyCollectionServiceImpl implements HierarchyCollectionService {

    /**
     * 将集合加工为带有层次关系的新集合
     *
     * @param sourceList 源集合
     * @return 新集合
     */
    @Override
    public <T extends HierarchyVO> List<T> buildHierarchyList(List<T> sourceList) {
        Map<String, List<T>> parentIdMap = Maps.newHashMap();

        // 构建 map
        sourceList.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getParentId())) {
                if (parentIdMap.containsKey(item.getParentId())) {
                    parentIdMap.get(item.getParentId()).add(item);
                } else {
                    parentIdMap.put(item.getParentId(), Lists.newArrayList(item));
                }
            } else {
                if (parentIdMap.get(StringConstants.ROOT) != null) {
                    parentIdMap.get(StringConstants.ROOT).add(item);
                } else {
                    parentIdMap.put(StringConstants.ROOT, Lists.newArrayList(item));
                }
            }
        });

        // 构建返回的机构列表
        List<T> retList = parentIdMap.get(StringConstants.ROOT);
        retList.sort(Comparator.comparing(T::getSortNo));
        for (T parent : retList) {
            wrapChildNodes(parent, parentIdMap);
        }
        return retList;
    }

    /**
     * 递归设置子节点
     *
     * @param parent 父节点
     */
    @SuppressWarnings("all")
    private <T extends HierarchyVO> void wrapChildNodes(T parent, Map<String, List<T>> parentIdMap) {
        if (parentIdMap.containsKey(parent.getId())) {
            // 将子节点根据 orderSeq 排序
            List<T> childList = parentIdMap.get(parent.getId());
            childList.sort(Comparator.comparing(T::getSortNo));
            // 设置当前节点的子节点
            parent.setChildren(childList);
            // 遍历设置子节点的子节点
            childList.forEach(child -> {
                wrapChildNodes(child, parentIdMap);
            });
        }
    }
}
