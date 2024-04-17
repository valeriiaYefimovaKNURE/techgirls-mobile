package com.example.techgirls.Models;

public class hotlinesModel {
    String orgName;
    String Phones;
    String Auditoria;
    String SiteLink;
    String SocialLink;
    String Telegram;

    public hotlinesModel(String nameOrgTitle, String Phones, String forWhoText, String siteLink, String socialLink,
                         String telegramLink) {
        this.orgName = nameOrgTitle;
        this.Phones = Phones;
        this.Auditoria = forWhoText;
        this.SiteLink = siteLink;
        this.SocialLink = socialLink;
        this.Telegram = telegramLink;
    }
    public hotlinesModel(){

    }

    public String getOrgName() {
        return orgName;
    }

    public String getPhones() {
        return Phones;
    }

    public String getAuditoria() {
        return Auditoria;
    }

    public String getSiteLink() {
        return SiteLink;
    }

    public String getSocialLink() {
        return SocialLink;
    }

    public String getTelegram() {
        return Telegram;
    }
}
