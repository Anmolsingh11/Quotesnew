package com.kavara.myapplication;

public class QuotesModel {
    private String Quotes;
    private String Author;

    public QuotesModel() {
    }

    public QuotesModel(String quotes, String author) {
        Quotes = quotes;
        Author = author;
    }


    public String getQuotes() {
        return Quotes;
    }

    public void setQuotes(String quotes) {
        Quotes = quotes;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}
