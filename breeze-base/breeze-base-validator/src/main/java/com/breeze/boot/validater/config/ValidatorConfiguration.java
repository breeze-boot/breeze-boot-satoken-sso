/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.validater.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 验证器配置
 *
 * @author breeze
 * @date 2022-10-19
 */
public class ValidatorConfiguration {

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败
                .failFast(true)
                .buildValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(@Autowired Validator validator) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator);
        return postProcessor;
    }

}
