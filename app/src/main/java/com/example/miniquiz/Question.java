package com.example.miniquiz;

public class Question {
    public String tekst;
    public String odpowiedzA;
    public String odpowiedzB;
    public String odpowiedzC;
    public String poprawna; // tekst poprawnej odpowiedzi (dokladnie jak w odpowiedzX)

    public Question(String tekst, String a, String b, String c, String poprawna) {
        this.tekst = tekst;
        this.odpowiedzA = a;
        this.odpowiedzB = b;
        this.odpowiedzC = c;
        this.poprawna = poprawna;
    }
}
