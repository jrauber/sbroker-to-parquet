package com.csvtransform.sbroker;

import java.io.Serializable;

public class SBrokerDto implements Serializable {

    private String handelsdatum;
    private String valutadatum;
    private String name;
    private String ISIN;
    private String WKN;
    private String transaktionsart;
    private String handelsplatz;
    private String nominalStueck;
    private String umsatz;
    private String waehrung;
    private String ausfuehrungskurs;
    private String kurswaehrung;
    private String ausfuehrungsdatum;
    private String ausmachenderBetrag;
    private String handelswaehrung;
    private String orderstatus;
    private String ordernummer;

    public SBrokerDto() {
    }

    public SBrokerDto(String handelsdatum,
                      String valutadatum,
                      String name,
                      String ISIN,
                      String WKN,
                      String transaktionsart,
                      String handelsplatz,
                      String nominalStueck,
                      String umsatz,
                      String waehrung,
                      String ausfuehrungskurs,
                      String kurswaehrung,
                      String ausfuehrungsdatum,
                      String ausmachenderBetrag,
                      String handelswaehrung,
                      String orderstatus,
                      String ordernummer) {

        this.handelsdatum = handelsdatum;
        this.valutadatum = valutadatum;
        this.name = name;
        this.ISIN = ISIN;
        this.WKN = WKN;
        this.transaktionsart = transaktionsart;
        this.handelsplatz = handelsplatz;
        this.nominalStueck = nominalStueck;
        this.umsatz = umsatz;
        this.waehrung = waehrung;
        this.ausfuehrungskurs = ausfuehrungskurs;
        this.kurswaehrung = kurswaehrung;
        this.ausfuehrungsdatum = ausfuehrungsdatum;
        this.ausmachenderBetrag = ausmachenderBetrag;
        this.handelswaehrung = handelswaehrung;
        this.orderstatus = orderstatus;
        this.ordernummer = ordernummer;
    }

    public String getHandelsdatum() {
        return handelsdatum;
    }

    public void setHandelsdatum(String handelsdatum) {
        this.handelsdatum = handelsdatum;
    }

    public String getValutadatum() {
        return valutadatum;
    }

    public void setValutadatum(String valutadatum) {
        this.valutadatum = valutadatum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISIN() {
        return ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    public String getWKN() {
        return WKN;
    }

    public void setWKN(String WKN) {
        this.WKN = WKN;
    }

    public String getTransaktionsart() {
        return transaktionsart;
    }

    public void setTransaktionsart(String transaktionsart) {
        this.transaktionsart = transaktionsart;
    }

    public String getNominalStueck() {
        return nominalStueck;
    }

    public void setNominalStueck(String nominalStueck) {
        this.nominalStueck = nominalStueck;
    }

    public String getUmsatz() {
        return umsatz;
    }

    public void setUmsatz(String umsatz) {
        this.umsatz = umsatz;
    }

    public String getWaehrung() {
        return waehrung;
    }

    public void setWaehrung(String waehrung) {
        this.waehrung = waehrung;
    }

    public String getAusfuehrungskurs() {
        return ausfuehrungskurs;
    }

    public void setAusfuehrungskurs(String ausfuehrungskurs) {
        this.ausfuehrungskurs = ausfuehrungskurs;
    }

    public String getKurswaehrung() {
        return kurswaehrung;
    }

    public void setKurswaehrung(String kurswaehrung) {
        this.kurswaehrung = kurswaehrung;
    }

    public String getAusfuehrungsdatum() {
        return ausfuehrungsdatum;
    }

    public void setAusfuehrungsdatum(String ausfuehrungsdatum) {
        this.ausfuehrungsdatum = ausfuehrungsdatum;
    }

    public String getAusmachenderBetrag() {
        return ausmachenderBetrag;
    }

    public void setAusmachenderBetrag(String ausmachenderBetrag) {
        this.ausmachenderBetrag = ausmachenderBetrag;
    }

    public String getHandelswaehrung() {
        return handelswaehrung;
    }

    public void setHandelswaehrung(String handelswaehrung) {
        this.handelswaehrung = handelswaehrung;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getOrdernummer() {
        return ordernummer;
    }

    public void setOrdernummer(String ordernummer) {
        this.ordernummer = ordernummer;
    }

    public String getHandelsplatz() {
        return handelsplatz;
    }

    public void setHandelsplatz(String handelsplatz) {
        this.handelsplatz = handelsplatz;
    }
}
