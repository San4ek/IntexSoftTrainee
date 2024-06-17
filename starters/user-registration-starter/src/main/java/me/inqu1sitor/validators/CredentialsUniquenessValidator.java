package me.inqu1sitor.validators;

import me.inqu1sitor.annotations.UniqueCredentials;
import me.inqu1sitor.dtos.CredentialsRequestDto;
import me.inqu1sitor.interfaces.CheckUniqueness;

public class CredentialsUniquenessValidator extends UniquenessValidator<UniqueCredentials, CredentialsRequestDto> {
    public CredentialsUniquenessValidator(CheckUniqueness<CredentialsRequestDto> checkUniqueness) {
        super(checkUniqueness);
    }
}
