package com.njeport.track.auth.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DuplicateCheckVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tableName;
	private String fieldName;
	private String fieldVal;
	private String dataId;
}