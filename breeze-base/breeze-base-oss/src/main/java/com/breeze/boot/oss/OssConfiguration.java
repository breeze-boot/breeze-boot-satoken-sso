/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.oss;

import com.breeze.boot.local.operation.LocalStorageTemplate;
import com.breeze.boot.oss.operation.MinioOssTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Oss 配置
 *
 * @author gaoweixuan
 * @since 2023-04-18
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Import({MinioOssTemplate.class, LocalStorageTemplate.class})
public class OssConfiguration {

}
