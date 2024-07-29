package com.example.techgirls.Models;

public class NewsData {
    private String dataTitle;
    private String dataCaption;
    private String dataText;
    private String dataLink;
    private String dataTheme;
    private String dataImage;
    private String key;
    private String dataAuthor;
    public NewsData(){}

    public NewsData(String dataTitle, String dataCaption, String dataText, String dataLink, String dataTheme, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataCaption = dataCaption;
        this.dataText = dataText;
        this.dataLink = dataLink;
        this.dataTheme = dataTheme;
        this.dataImage=dataImage;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataCaption() {
        return dataCaption;
    }

    public void setDataCaption(String dataCaption) {
        this.dataCaption = dataCaption;
    }

    public String getDataText() {
        return dataText;
    }

    public void setDataText(String dataText) {
        this.dataText = dataText;
    }

    public String getDataLink() {
        return dataLink;
    }

    public void setDataLink(String dataLink) {
        this.dataLink = dataLink;
    }

    public String getDataTheme() {
        return dataTheme;
    }

    public void setDataTheme(String dataTheme) {
        this.dataTheme = dataTheme;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getDataAuthor() {
        return dataAuthor;
    }

    public void setDataAuthor(String dataAuthor) {
        this.dataAuthor = dataAuthor;
    }

}
