package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Api(tags = "套餐相关接口")
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @ApiOperation("新增套餐")
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result newSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐: {}",setmealDTO);
        setmealService.save(setmealDTO);


        return Result.success();
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> getSetmealById(@PathVariable Long id){
        log.info("根据id: {} 查询套餐",id);

        SetmealVO setmealVO = setmealService.getById(id);


        return Result.success(setmealVO);
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询: {}",setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 套餐起售、停售
     * @param status
     * @return
     */
    @ApiOperation("套餐起售、停售")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    @PostMapping("/status/{status}")
    public Result startOrForbidden(@PathVariable Integer status,Long id){
        log.info("套餐起售: {} ,{}", id, status == 1 ? "起售" : "停售");
        setmealService.startOrForbidden(id,status);

        return Result.success();
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @ApiOperation("修改套餐")
    @PutMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐:{}",setmealDTO);
        setmealService.update(setmealDTO);

        return Result.success();
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteBatch(@RequestParam List<Long> ids){
        log.info("批量删除套餐 :{}",ids);
        setmealService.deleteBatch(ids);

        return Result.success();

    }
}
