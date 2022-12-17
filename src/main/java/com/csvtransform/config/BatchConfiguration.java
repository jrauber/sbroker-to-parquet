package com.csvtransform.config;

import com.csvtransform.dkb.DKBBrokerTransferDto;
import com.csvtransform.dkb.DKBBrokerTransferToParquetProcessor;
import com.csvtransform.parquet.ParquetDto;
import com.csvtransform.sbroker.SBrokerDto;
import com.csvtransform.sbroker.SBrokerToParquetProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private Resource outputResource = new FileSystemResource("output/outputData.csv");

    private Resource sbrokerInputResource = new FileSystemResource("SBroker.csv");

    private Resource dkbInputResource = new FileSystemResource("DKB.csv");

    private Resource sbrokerManualCorrectionResource = new FileSystemResource("SBroker-manualCorrection.csv");

    @Bean
    public FlatFileItemReader<DKBBrokerTransferDto> dkbBrokerTransferReader() {
        return new FlatFileItemReaderBuilder<DKBBrokerTransferDto>()
                .name("dkbBrokerTransferReader")
                .resource(dkbInputResource)
                .linesToSkip(7)
                .encoding("windows-1252")
                .delimited().delimiter(";")
                .names(new String[]{"isin", "name", "stueck", "valuta"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(DKBBrokerTransferDto.class);
                    setDistanceLimit(1);
                }})
                .build();
    }

    @Bean
    public FlatFileItemReader<SBrokerDto> sbrokerDataReader() {
        return new FlatFileItemReaderBuilder<SBrokerDto>()
                .name("sbrokerDataReader")
                .resource(sbrokerInputResource)
                .linesToSkip(7)
                .encoding("windows-1252")
                .delimited().delimiter(";")
                .names(new String[]{"handelsdatum", "valutadatum", "name", "ISIN", "WKN", "transaktionsart",
                "handelsplatz", "nominalStueck", "umsatz", "waehrung", "ausfuehrungskurs", "kurswaehrung", "ausfuehrungsdatum",
                "ausmachenderBetrag", "handelswaehrung", "orderstatus", "ordernummer"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(SBrokerDto.class);
                    setDistanceLimit(1);
                }})
                .build();
    }

    @Bean
    public FlatFileItemReader<SBrokerDto> sbrokerManualCorrectionDataReader() {
        return new FlatFileItemReaderBuilder<SBrokerDto>()
                .name("sbrokerManualCorrectionDataReader")
                .resource(sbrokerManualCorrectionResource)
                .linesToSkip(1)
                .encoding("windows-1252")
                .delimited().delimiter(";")
                .names(new String[]{"handelsdatum", "valutadatum", "name", "ISIN", "WKN", "transaktionsart",
                        "handelsplatz", "nominalStueck", "umsatz", "waehrung", "ausfuehrungskurs", "kurswaehrung", "ausfuehrungsdatum",
                        "ausmachenderBetrag", "handelswaehrung", "orderstatus", "ordernummer"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(SBrokerDto.class);
                    setDistanceLimit(1);
                }})
                .build();
    }

    @Bean
    public FlatFileItemWriter<ParquetDto> writer()
    {
        FlatFileItemWriter<ParquetDto> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setHeaderCallback(writer1 -> writer1.write("date;currency;time;price;shares;tax;fee;type;broker;isin"));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(";");
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(new String[] { "date", "currency", "time", "price", "shares", "tax", "fee", "type", "broker", "isin" });
                    }
                });
            }
        });

        return writer;
    }

    @Bean
    public SBrokerToParquetProcessor sbrokerProcessor() {
        return new SBrokerToParquetProcessor();
    }

    @Bean
    public DKBBrokerTransferToParquetProcessor dkbProcessor() {
        return new DKBBrokerTransferToParquetProcessor();
    }

    @Bean
    public Job importTransactionsJob(JobCompletionNotificationListener listener, Step step1, Step step2, Step step3) {
        return jobBuilderFactory.get("importTransactionsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .next(step2)
                .next(step3)
                .end()
                .build();
    }

    @Bean
    public Step step1(FlatFileItemWriter<ParquetDto> writer) {
        return stepBuilderFactory.get("step1")
                .<SBrokerDto, ParquetDto> chunk(10)
                .reader(sbrokerDataReader())
                .processor(sbrokerProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public Step step2(FlatFileItemWriter<ParquetDto> writer) {
        return stepBuilderFactory.get("step2")
                .<SBrokerDto, ParquetDto> chunk(10)
                .reader(sbrokerManualCorrectionDataReader())
                .processor(sbrokerProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public Step step3(FlatFileItemWriter<ParquetDto> writer) {
        return stepBuilderFactory.get("step3")
                .<DKBBrokerTransferDto, ParquetDto> chunk(10)
                .reader(dkbBrokerTransferReader())
                .processor(dkbProcessor())
                .writer(writer)
                .build();
    }
}