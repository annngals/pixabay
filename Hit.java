package com.example.pixabay;

// Класс для описания результатов поиска
public class Hit {
    int id;
    String previewURL;
    String type;

    @Override
    public String toString() {
        return "Hit{" +
                "id=" + id +
                ", previewURL='" + previewURL + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}