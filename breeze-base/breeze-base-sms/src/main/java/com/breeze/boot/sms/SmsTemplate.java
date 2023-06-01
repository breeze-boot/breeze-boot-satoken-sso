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

package com.breeze.boot.sms;

import com.aliyun.core.http.HttpHeaders;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.*;
import com.breeze.boot.sms.config.SmsConfiguration;
import com.breeze.boot.sms.config.SmsProperties;
import com.google.gson.Gson;
import darabonba.core.RequestConfiguration;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 短信模板
 *
 * @author gaoweixuan
 * @date 2023/06/01
 */
@Slf4j
@RequiredArgsConstructor
public class SmsTemplate {

    private final SmsConfiguration smsConfiguration;

    public AsyncClient asyncClient(SmsProperties smsProperties) {
        return AsyncClient.builder()
                .region(smsProperties.getRegionId()) // 区域 region ID
                .credentialsProvider(smsConfiguration.credentialsProvider())
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                                .setConnectTimeout(Duration.ofSeconds(30))).build();
    }

    public SendSmsResponseBody sendSms(String phoneNumber) {
        SmsProperties smsProperties = smsConfiguration.getSmsProperties();
        AsyncClient asyncClient = this.asyncClient(smsProperties);
        try {
            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                    .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .signName(smsProperties.getSignName())
                    .templateCode(smsProperties.getTemplateCode())
                    .phoneNumbers(phoneNumber)
                    .templateParam(smsProperties.getTemplateParam())
                    .build();

            CompletableFuture<SendSmsResponse> response = asyncClient.sendSms(sendSmsRequest);
            SendSmsResponse smsResponse = response.get();
            log.info(new Gson().toJson(smsResponse));
            return smsResponse.getBody();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public SendBatchSmsResponseBody sendBatchSms(List<String> phoneNumber) {
        SmsProperties smsProperties = smsConfiguration.getSmsProperties();
        AsyncClient asyncClient = this.asyncClient(smsProperties);
        try {
            SendBatchSmsRequest sendBatchSmsRequest = SendBatchSmsRequest.builder()
                    .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .templateParamJson(smsProperties.getTemplateParam())
                    .signNameJson(smsProperties.getSignName())
                    .phoneNumberJson(Arrays.toString(phoneNumber.toArray()))
                    .templateCode(smsProperties.getTemplateCode())
                    .build();
            CompletableFuture<SendBatchSmsResponse> response = asyncClient.sendBatchSms(sendBatchSmsRequest);
            SendBatchSmsResponse smsResponse = response.get();
            log.info(new Gson().toJson(smsResponse));
            return smsResponse.getBody();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
