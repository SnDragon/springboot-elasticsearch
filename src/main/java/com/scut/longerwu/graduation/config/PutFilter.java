package com.scut.longerwu.graduation.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.HttpPutFormContentFilter;

/**
 * 允许控制器接受到PUT方法的参数
 */
@Component
public class PutFilter extends HttpPutFormContentFilter {
}
