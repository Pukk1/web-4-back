package com.ivan.web4back.model.access;

public enum AuthProvider {
    LOCAL("LOCAL"),
    GOOGLE("GOOGLE"),
    VK("VK");

    private final String name;

    AuthProvider(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
