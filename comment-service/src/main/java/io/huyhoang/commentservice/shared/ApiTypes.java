package io.huyhoang.commentservice.shared;

public enum ApiTypes {

    SUCCESS("success"),
    FAILURE("failure");

    private final String name;

    ApiTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
