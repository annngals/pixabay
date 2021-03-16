package com.example.pixabay;

public class Response {
    int total;
    Hit[] hits;

    @Override
    public String toString() {
        return "totalHits = " + total;
    }
}
