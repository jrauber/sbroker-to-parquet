package com.csvtransform.sbroker;

import com.csvtransform.parquet.ParquetDto;
import org.springframework.batch.item.ItemProcessor;
import java.math.BigDecimal;

/**
 * Transforms the sbroker input into the csv format
 * that parquet can import
 *
 * Import format description: https://www.parqet.com/blog/csv
 */
public class SBrokerToParquetProcessor implements ItemProcessor<SBrokerDto, ParquetDto> {

    public static final String AUSSCHUETTUNG = "Ausschüttung";
    public static final String VERKAUF = "Verkauf";
    public static final String AUSBUCHUNG = "Ausbuchung";
    public static final String SPARPLAN = "Sparplan";
    public static final String KAUF = "Kauf";
    public static final String EINBUCHUNG = "Einbuchung";

    @Override
    public ParquetDto process(final SBrokerDto sb) throws Exception {

        ParquetDto t = new ParquetDto();

        t.setDate(sb.getAusfuehrungsdatum().split(" ")[0]);
        t.setIsin(replaceUnknownISIN(sb.getISIN()));

        if (sb.getTransaktionsart().equals(AUSBUCHUNG)) {
            t.setPrice("1,0");
        } else {

            if (sb.getAusfuehrungskurs() != null && sb.getAusfuehrungskurs().length() > 0) {
                t.setPrice(sb.getAusfuehrungskurs());
            } else {
                t.setPrice("");
            }
        }

        t.setShares(sb.getNominalStueck());
        t.setType(mapType(sb.getTransaktionsart()));
        t.setBroker("sbroker");
        t.setTime(sb.getAusfuehrungsdatum().split(" ")[1] + ":00");
        t.setCurrency(sb.getWaehrung());

        if (sb.getTransaktionsart().equals(AUSSCHUETTUNG) || sb.getTransaktionsart().equals(VERKAUF)) {

            BigDecimal umsatzBrutto = new BigDecimal(formatToCultureInvariant(sb.getUmsatz()));
            BigDecimal umsatzNetto = new BigDecimal(formatToCultureInvariant(sb.getAusmachenderBetrag()));
            BigDecimal tax = umsatzBrutto.subtract(umsatzNetto);

            t.setTax(formatToGermanFormat(tax));
            t.setFee("0,0");

        } else if(sb.getTransaktionsart().equals(EINBUCHUNG)) {

            t.setPrice("1,0");
            t.setFee("");
            t.setTax("");

        } else {

            BigDecimal umsatzBrutto = new BigDecimal(formatToCultureInvariant(sb.getUmsatz()));
            BigDecimal umsatzNetto = new BigDecimal(formatToCultureInvariant(sb.getAusmachenderBetrag()));
            BigDecimal tax = umsatzBrutto.subtract(umsatzNetto).multiply(new BigDecimal(-1));

            t.setFee(formatToGermanFormat(tax));
            t.setTax("0,0");

        }

        return t;
    }

    private String replaceUnknownISIN(String isin) {

        if (isin.equals("IE0000597124")) {
            return "LU1992126489";
        }

        return isin;
    }

    /**
     * @param type One of the following: Buy, Sell, Dividend, TransferIn or TransferOut
     */
    private String mapType(String type) {

        if (type.equals(AUSSCHUETTUNG)) {
            return "Dividend";
        } else if (type.equals(AUSBUCHUNG)) {
            return "TransferOut";
        } else if (type.equals(SPARPLAN)) {
            return "Buy";
        } else if (type.equals(KAUF)) {
            return "Buy";
        } else if (type.equals(VERKAUF)) {
            return "Sell";
        } else if (type.equals(EINBUCHUNG)) {
            return "TransferIn";
        }

        throw new IllegalArgumentException("Type " + type + " unknown");
    }

    private String formatToCultureInvariant(String formattedGermanDecimal) {
        return formattedGermanDecimal.replace(".","")
                                     .replace(",",".");
    }

    private String formatToGermanFormat(BigDecimal decimal) {
        return decimal.toPlainString().replace(".",",");
    }

}
