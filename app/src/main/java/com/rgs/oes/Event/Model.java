package com.rgs.oes.Event;

public class Model {
    String qustion;
    String A, B, C, D, ans;

    public Model(String qustion, String a, String b, String c, String d, String ans) {
        this.qustion = qustion;
        A = a;
        B = b;
        C = c;
        D = d;
        this.ans = ans;
    }

    public String getQustion() {
        return qustion;
    }

    public void setQustion(String qustion) {
        this.qustion = qustion;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
