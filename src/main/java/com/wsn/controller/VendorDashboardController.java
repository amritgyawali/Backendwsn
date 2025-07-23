package com.wsn.controller;

import com.wsn.security.UserDetailsImpl;
import com.wsn.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/vendor/dashboard")
public class VendorDashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public Map<String,Object> getDashboard(){
        UserDetailsImpl vendor = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dashboardService.getVendorDashboardMetrics(vendor.getId());
    }
}
