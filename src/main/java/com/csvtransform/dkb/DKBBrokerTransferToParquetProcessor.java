package com.csvtransform.dkb;

import com.csvtransform.parquet.ParquetDto;
import org.springframework.batch.item.ItemProcessor;
import java.math.BigDecimal;

/**
 * Transforms the dkb input into the csv format
 * that parquet can import
 *
 * Import format description: https://www.parqet.com/blog/csv
 */
public class DKBBrokerTransferToParquetProcessor implements ItemProcessor<DKBBrokerTransferDto, ParquetDto> {


    @Override
    public ParquetDto process(final DKBBrokerTransferDto sb) throws Exception {

        ParquetDto t = new ParquetDto();

        t.setDate(sb.getValuta());
        t.setIsin(sb.getIsin());
        t.setPrice("1,0");

        t.setShares(sb.getStueck());
        t.setType("TransferIn");
        t.setBroker("dkb");
        t.setTime("00:00:00");
        t.setCurrency("EUR");

        return t;
    }
}
