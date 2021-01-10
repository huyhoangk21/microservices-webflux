package io.huyhoang.userservice.dto;

import java.io.Serializable;
import java.util.List;

public class UserError implements Serializable {

    private List<String> errors;

    public UserError() {
    }

    public UserError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
