package com.breeze.boot.security.service;

import java.util.List;
import java.util.Map;

public interface ITenantService {

    List<Map<String, Object>> selectTenant();

}
