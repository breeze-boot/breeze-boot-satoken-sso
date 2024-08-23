package com.breeze.boot.security.service.impl;

import com.breeze.boot.security.service.ITenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
@Slf4j
@RequiredArgsConstructor
public class TenantServiceImpl implements ITenantService {

    private final Supplier<ITenantService> tenantServiceSupplier;

    @Override
    public List<Map<String, Object>> selectTenant() {
        return tenantServiceSupplier.get().selectTenant();
    }

}
