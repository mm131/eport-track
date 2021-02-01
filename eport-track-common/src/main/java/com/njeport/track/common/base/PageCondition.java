package com.njeport.track.common.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kongming
 * @date 2020/03/07
 */
@Getter
@Setter
public class PageCondition {
    private String column;
    private String order;
    private String field;
    private int pageNo;
    private int pageSize;
}
