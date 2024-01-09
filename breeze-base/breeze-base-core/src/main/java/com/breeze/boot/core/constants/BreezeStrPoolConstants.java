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

package com.breeze.boot.core.constants;

import cn.hutool.core.text.StrPool;

/**
 * str池
 *
 * @author gaoweixuan
 * @since 2022-10-22
 */
public interface BreezeStrPoolConstants extends StrPool {

    char M_DELIM_START = '(';

    char M_DELIM_END = ')';

    String BASE_SQL = "<script>insert into %s %s values %s</script>";

    String FOREACH_START = "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">";

    String ITEM_START = "#{item.";

    String FOREACH_END = "</foreach>";
}
