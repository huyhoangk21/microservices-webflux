package io.huyhoang.userservice.exception;

import java.util.List;

public class ResourceAlreadyExistsException extends RuntimeException {

    List<String> errors;

    public ResourceAlreadyExistsException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
