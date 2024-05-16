package cn.wangjiahang.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

// 处理规则降级的统一异常类
@Slf4j
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        R r = null;
        if(e instanceof FlowException){
            r = R.error(100001,"接口已被限流");
        }
        if(e instanceof DegradeException){
            r = R.error(100002,"服务已被降级");
        }
        if(e instanceof ParamFlowException){
            r = R.error(100003,"热点参数被限流");
        }
        if(e instanceof SystemBlockException){
            r = R.error(100004,"触发系统保护规则");
        }
        if(e instanceof AuthorityException){
            r = R.error(100004,"未被授权,请稍后再试");
        }
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), r);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class R {
        private int code;
        private String msg;

        public static R error(int code, String msg) {
            return new R(code, msg);
        }
    }
}

