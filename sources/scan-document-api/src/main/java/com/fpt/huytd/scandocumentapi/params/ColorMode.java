package com.fpt.huytd.scandocumentapi.params;

import java.util.Arrays;

public enum ColorMode {
    BW(0, "BW"), GRAY(1, "GRAY"), RBG(2, "RBG");

    private ColorMode(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ColorMode fromNameOrThrowException(String name) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("ColorMode %s not match {BW,GRAY,RBG}!", name)));
    }

    private String value;
    private int key;

    public String getValue() {
        return this.name();
    }

    public int getKey() {
        return key;
    }
}
