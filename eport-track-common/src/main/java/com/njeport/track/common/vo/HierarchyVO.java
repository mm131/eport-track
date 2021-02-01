package com.njeport.track.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Getter
@Setter
public abstract class HierarchyVO<T> {
    protected String id;
    private String parentId;
    private Double sortNo;
    private List<T> children;
    public abstract String getId();
}
