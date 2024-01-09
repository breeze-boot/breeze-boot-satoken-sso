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

package com.breeze.boot.sap.core;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.sap.conn.jco.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sap.conn.jco.JCoException.JCO_ERROR_FUNCTION_NOT_FOUND;

@Slf4j
public class SAPRFCInvoker {

    private static SAPRFCInvoker instance;
    private JCoDestination destination;
    private JCoFunction jCoFunction;

    private String clientCd;


    @SneakyThrows
    private SAPRFCInvoker() {
        // 创建SAP连接目标对象
        destination = JCoDestinationManager.getDestination(this.getDestinationName());
    }

    public SAPRFCInvoker(String clientCd) {
        this.clientCd = clientCd;
    }

    public static SAPRFCInvoker getInstance(String clientCd) {
        if (instance == null) {
            instance = new SAPRFCInvoker(clientCd);
        }
        return instance;
    }

    public String getDestinationName() {
        return StrUtil.upperFirstAndAddPre(clientCd, "ABAP_AS_");
    }


    /**
     * 取得RFC函数实例.
     *
     * @param functionId RFC函数ID
     * @return RFC函数实例
     * @throws JCoException JCoException
     */
    public JCoFunction getFunction(String functionId) throws JCoException {
        return destination.getRepository().getFunction(functionId);
    }

    /**
     * 执行RFC函数处理.
     *
     * @param functionName 方法名
     */
    public void execute(String functionName) {
        try {
            if (StrUtil.isBlank(functionName)) {
                jCoFunction.execute(destination);
            } else {
                jCoFunction.execute(destination, functionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SAP输入参数
     *
     * // @formatter:off
     * {
     *     key: value
     * }
     * // @formatter:on
     */
    protected void setParameter(String paramName, Object value) {
        // 单个参数传值
        jCoFunction.getImportParameterList().setValue(paramName, value);
    }

    /**
     * SAP输入参数
     * // @formatter:off
     * {
     *     key:  {
     *              key: value
     *           }
     * }
     * // @formatter:on
     */
    protected void setParameterStructure(String k, Map<String, Object> valueMap) {
        // 取得sap该结构类Key下的Structure
        JCoStructure jCoStructure = jCoFunction.getImportParameterList().getStructure(k);
        log.debug("参数 结构体KEY: {}", k);
        valueMap.forEach((sk, v) -> {
            // 遍历输入项目给Structure下的所有KEY赋值
            jCoStructure.setValue(sk, v);
            log.debug("参数KEY: {} 设定值: {}", sk, v);
        });
    }

    /**
     * SAP输入参数
     *
     * // @formatter:off
     * {
     *     key:  [
     *              {
     *                  key: value
     *              }
     *           ]
     * }
     * // @formatter:on
     */
    protected void setParameterTable(String k, List<Map<String, Object>> valueList) {
        int rowNumber = 0;
        JCoTable tDateRange = jCoFunction.getTableParameterList().getTable(k);
        tDateRange.appendRow();
        tDateRange.setRow(rowNumber);
        valueList.forEach(vMap -> {
            vMap.forEach((tk, v) -> {
                tDateRange.setValue(k, v);
                log.debug("参数KEY: {} 设定值: {}", k, v);
            });
        });
    }

    /**
     * SAP输入参数
     *
     * // @formatter:off
     * {
     *     key: value
     * }
     * // @formatter:on
     */
    protected void getParameter(String paramName, Object value) {
        // 单个参数传值
        jCoFunction.getImportParameterList().setValue(paramName, value);
    }

    /**
     * SAP输入参数
     * // @formatter:off
     * {
     *     key:  {
     *              key: value
     *           }
     * }
     * // @formatter:on
     */
    protected void getParameterStructure(String k, Map<String, Object> valueMap) {
        // 取得sap该结构类Key下的Structure
        JCoStructure jCoStructure = jCoFunction.getImportParameterList().getStructure(k);
        log.debug("参数 结构体KEY: {}", k);
        valueMap.forEach((sk, v) -> {
            // 遍历输入项目给Structure下的所有KEY赋值
            jCoStructure.setValue(sk, v);
            log.debug("参数KEY: {} 设定值: {}", sk, v);
        });
    }

    /**
     * SAP输入参数
     *
     * // @formatter:off
     * {
     *     key:  [
     *              {
     *                  key: value
     *              }
     *           ]
     * }
     * // @formatter:on
     */
    protected void getParameterTable(String k, List<Map<String, Object>> valueList) {
        int rowNumber = 0;
        JCoTable tDateRange = jCoFunction.getTableParameterList().getTable(k);
        tDateRange.appendRow();
        tDateRange.setRow(rowNumber);
        valueList.forEach(vMap -> {
            vMap.forEach((tk, v) -> {
                tDateRange.setValue(k, v);
                log.debug("参数KEY: {} 设定值: {}", k, v);
            });
        });
    }

    public Object callRFCFunction(String functionName, Map<String, Object> inputParameterMap) throws JCoException {
        // 获取RFC函数对象
        jCoFunction = getFunction(functionName);
        if (jCoFunction == null) {
            throw new JCoException(JCO_ERROR_FUNCTION_NOT_FOUND, "RFC function not found: " + functionName);
        }

        if (MapUtil.isNotEmpty(inputParameterMap)) {
            inputParameterMap.forEach((k, v) -> {
                if (v instanceof ArrayList) {
                    setParameterTable(k, (ArrayList) v);
                } else if (v instanceof Map) {
                    setParameterStructure(k, (Map) v);
                } else {
                    setParameter(k, v);
                }
            });
        }

        // 执行 调用RFC函数并传递参数
        execute(functionName);
        // 获取输出参数和返回值
        JCoParameterList exportParams = jCoFunction.getExportParameterList();
//        if (outputTable != null && exportParams != null) {
//            int rowCount = outputTable.getNumRows();
//            for (int i = 0; i < rowCount; i++) {
//                outputTable.setRow(i);
//                for (int j = 0; j < exportParams.getNumFields(); j++) {
//                    outputTable.setValue(j, exportParams.getString(j));
//                }
//            }
//        }
        return exportParams;
    }
}
