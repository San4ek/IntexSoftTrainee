package me.inquis1tor.userservice.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParameteredRuntimeException extends RuntimeException {

    private String parameter;

    public ParameteredRuntimeException(String message) {
        super(message);
    }

    public ParameteredRuntimeException(String parameter, String message) {
        super(message);
        this.parameter=parameter;
    }
}
