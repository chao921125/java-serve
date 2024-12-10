package com.cc.common.core;

import com.cc.common.constants.HttpStatus;
import com.cc.common.utils.DateUtil;
import com.cc.common.utils.PageUtil;
import com.cc.common.utils.SqlUtil;
import com.cc.common.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void convertDate(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtil.startPage();
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy() {
        PageEntity pageEntity = PageSupport.buildPageRequest();
        if (StringUtil.isNotEmpty(pageEntity.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(pageEntity.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 清理分页的线程变量
     */
    protected void clearPage() {
        PageUtil.clearPage();
    }


    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected ResultPageEntity getDataTable(List<?> list) {
        ResultPageEntity rspData = new ResultPageEntity();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }


    /**
     * 返回成功
     */
    public ResultEntity success() {
        return ResultEntity.success();
    }

    /**
     * 返回成功消息
     */
    public ResultEntity success(String message) {
        return ResultEntity.success(message);
    }

    /**
     * 返回成功消息
     */
    public ResultEntity success(Object data) {
        return ResultEntity.success(data);
    }

    /**
     * 返回失败消息
     */
    public ResultEntity error() {
        return ResultEntity.error();
    }

    /**
     * 返回失败消息
     */
    public ResultEntity error(String message) {
        return ResultEntity.error(message);
    }

    /**
     * 返回警告消息
     */
    public ResultEntity warn(String message) {
        return ResultEntity.warn(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected ResultEntity toAjax(int rows) {
        return rows > 0 ? ResultEntity.success() : ResultEntity.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected ResultEntity toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtil.format("redirect:{}", url);
    }

    /**
     * 获取用户缓存信息
     */
//    public LoginUser getLoginUser() {
//        return SecurityUtils.getLoginUser();
//    }

    /**
     * 获取登录用户id
     */
//    public Long getUserId() {
//        return getLoginUser().getUserId();
//    }
//    public Long[] getRoleIds() {
//        return getLoginUser().getUser().getRoleIds();
//    }
//    public String getRoleIdStr() {
//        Long[] ids = getRoleIds();
//        StringJoiner sj = new StringJoiner(",");
//        for(Long id:ids) {
//            sj.add(id.toString());
//        }
//        return sj.toString();
//    }

    /**
     * 获取登录部门id
     */
//    public Long getDeptId() {
//        return getLoginUser().getDeptId();
//    }

    /**
     * 获取登录用户名
     */
//    public String getUsername() {
//        return getLoginUser().getUsername();
//    }
}
