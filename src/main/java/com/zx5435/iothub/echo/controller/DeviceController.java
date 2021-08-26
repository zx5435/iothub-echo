package com.zx5435.iothub.echo.controller;

import com.zx5435.iothub.echo.model.dao.DeviceDAO;
import com.zx5435.iothub.echo.model.db.DeviceDO;
import com.zx5435.iothub.echo.model.vo.DeviceVO;
import com.zx5435.iothub.echo.util.IdWorker;
import com.zx5435.jii.web.ApiData;
import com.zx5435.jii.web.ApiException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zx5435
 */
@Controller
public class DeviceController {

    @Resource
    DeviceDAO deviceDAO;

    @GetMapping("/device/{id}")
    public String info(Model model, @PathVariable long id) {
        DeviceDO deviceDO = deviceDAO.findById(id).orElseThrow(ApiException::a404);
        DeviceVO deviceVO = new DeviceVO();
        BeanUtils.copyProperties(deviceDO, deviceVO);

        model.addAttribute("device", deviceVO);
        return "device_info";
    }

    @PostMapping("/api/device")
    @ResponseBody
    public ApiData create(@RequestBody DeviceDO device) {
        device.setId(IdWorker.nextId());
        device.setType(1);
        device = deviceDAO.save(device);
        return ApiData.success(device);
    }

    @DeleteMapping("/api/device/{id}")
    @ResponseBody
    public ApiData delete(@PathVariable long id) {
        deviceDAO.deleteById(id);
        return ApiData.success("Deleted");
    }

}
