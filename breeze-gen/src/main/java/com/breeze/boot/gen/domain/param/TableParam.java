package com.breeze.boot.gen.domain.param;

import com.breeze.boot.core.base.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class TableParam extends PageQuery {

    private String tableName;

}
