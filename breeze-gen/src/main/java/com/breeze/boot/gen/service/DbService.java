package com.breeze.boot.gen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.gen.domain.Column;
import com.breeze.boot.gen.domain.Table;
import com.breeze.boot.gen.domain.param.TableParam;
import com.breeze.boot.gen.mapper.DbMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DbService {

    private final DbMapper dbMapper;

    public Page<Table> listPage(TableParam tableParam) {
        return this.dbMapper.listPage(new Page<>(tableParam.getCurrent(), tableParam.getSize()), tableParam);
    }

    public byte[] generatorCode(List<String> tables) {
        for (String tableName : tables) {
            Table table = this.dbMapper.getTableInfo(tableName);
            List<Column> columnList = this.dbMapper.listTableColumn(tableName);
            this.genCodeZip(table,columnList);
        }
        return new byte[0];
    }

    private void genCodeZip(Table table, List<Column> columnList) {


    }
}
