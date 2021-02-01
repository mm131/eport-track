package com.njeport.track.auth.controller.common;

import com.njeport.track.auth.vo.DuplicateCheckVo;
import com.njeport.track.common.vo.CommonReturnVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kongming
 * @date 2020/03/08
 */
@RestController
@RequestMapping("/sys/duplicate")
public class DuplicateCheckController {

    /**
     * 校验数据是否在系统中是否存在
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public CommonReturnVO doDuplicateCheck(DuplicateCheckVo duplicateCheckVo, HttpServletRequest request) {
        return CommonReturnVO.succ();
    }
}
