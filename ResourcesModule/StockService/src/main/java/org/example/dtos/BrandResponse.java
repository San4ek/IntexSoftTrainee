package org.example.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {

    @NotNull(message = "Name can't be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String brandName;
}
