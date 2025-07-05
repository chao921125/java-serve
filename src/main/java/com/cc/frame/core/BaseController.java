package com.cc.frame.core;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 通用 CRUD 基础控制器
 * 提供标准的增删改查操作
 * @param <T> 实体类型
 * @param <S> 服务类型
 */
public abstract class BaseController<T, S> {
	/**
	 * 获取服务实例
	 */
	protected abstract S getService();

	/**
	 * 根据ID获取实体
	 * @param id 实体ID
	 * @return 实体对象
	 */
	protected abstract T doGetById(Long id);

	/**
	 * 新增实体
	 * @param entity 实体对象
	 * @return 操作结果
	 */
	protected abstract boolean doAdd(T entity);

	/**
	 * 删除实体
	 * @param id 实体ID
	 * @return 操作结果
	 */
	protected abstract boolean doDelete(Long id);

	/**
	 * 更新实体
	 * @param entity 实体对象
	 * @return 操作结果
	 */
	protected abstract boolean doUpdate(T entity);

	/**
	 * 获取实体列表
	 * @return 实体列表
	 */
	protected abstract List<T> doList();

	/**
	 * 分页查询
	 * @param pageRequest 分页请求
	 * @return 分页结果
	 */
	protected abstract PageResult<T> doPage(PageRequest pageRequest);

	/**
	 * 通用获取接口
	 * @param id 实体ID
	 * @return 响应结果
	 */
	@Operation(summary = "根据ID获取详情")
	@GetMapping("/{id}")
	public ApiResponse<T> get(@Parameter(description = "实体ID") @PathVariable Long id) {
		T entity = doGetById(id);
		if (entity == null) {
			return ApiResponse.success("未查询到数据", null);
		}
		return ApiResponse.success(entity);
	}

	/**
	 * 通用新增接口
	 * @param entity 实体对象
	 * @return 响应结果
	 */
	@Operation(summary = "新增")
	@PostMapping("/add")
	public ApiResponse<String> add(@RequestBody T entity) {
		boolean result = doAdd(entity);
		return result ? ApiResponse.success("新增成功", null) : ApiResponse.error(500, "新增失败");
	}

	/**
	 * 通用删除接口
	 * @param id 实体ID
	 * @return 响应结果
	 */
	@Operation(summary = "删除")
	@PostMapping("/delete/{id}")
	public ApiResponse<String> delete(@Parameter(description = "实体ID") @PathVariable Long id) {
		boolean result = doDelete(id);
		return result ? ApiResponse.success("删除成功", null) : ApiResponse.error(500, "删除失败");
	}

	/**
	 * 通用更新接口
	 * @param entity 实体对象
	 * @return 响应结果
	 */
	@Operation(summary = "更新")
	@PostMapping("/update")
	public ApiResponse<String> update(@RequestBody T entity) {
		boolean result = doUpdate(entity);
		return result ? ApiResponse.success("修改成功", null) : ApiResponse.error(500, "修改失败");
	}

	/**
	 * 通用列表接口
	 * @return 响应结果
	 */
	@Operation(summary = "获取列表")
	@GetMapping("/list")
	public ApiResponse<List<T>> list() {
		List<T> list = doList();
		return ApiResponse.success(list);
	}

	/**
	 * 通用分页接口
	 * @param pageNum 页码
	 * @param pageSize 页大小
	 * @return 响应结果
	 */
	@Operation(summary = "分页查询")
	@GetMapping("/page")
	public ApiResponse<PageResult<T>> page(@RequestParam(defaultValue = "1") int pageNum,
										  @RequestParam(defaultValue = "10") int pageSize) {
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageNum(pageNum);
		pageRequest.setPageSize(pageSize);
		PageResult<T> result = doPage(pageRequest);
		return ApiResponse.success(result);
	}
}
