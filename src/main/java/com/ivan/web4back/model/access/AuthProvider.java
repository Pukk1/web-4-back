package com.ivan.web4back.model.access;

public enum AuthProvider {
    LOCAL("local"),
    GOOGLE("google"),
    VK("vk");

    private final String name;

    AuthProvider(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
