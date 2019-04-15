package com.charlezz.selectionsample;

public class Photo {
    private String name;
    private String path;
    public Photo(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
