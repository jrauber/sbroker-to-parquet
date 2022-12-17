package com.csvtransform.dkb;

public class DKBBrokerTransferDto {

    private String stueck;
    private String isin;
    private String name;
    private String valuta;

    public DKBBrokerTransferDto() {
    }

    public DKBBrokerTransferDto(String isin, String name, String stueck, String valuta) {
        this.name = name;
        this.stueck = stueck;
        this.isin = isin;
        this.valuta = valuta;
    }

    public String getStueck() {
        return stueck;
    }

    public void setStueck(String stueck) {
        this.stueck = stueck;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
