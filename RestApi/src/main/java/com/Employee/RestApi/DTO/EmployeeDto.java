package com.Employee.RestApi.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto {

    private Long id;
    @NotBlank(message = "Name Cant Be null")
    private String name;
    @Email(message = "Email have should @gmail.com")
    private String email;
    @Max(value = 60 ,message="Age Cant be above 60")
    @Min(value = 18 ,message="Age Cant be below 18")
    private Integer age;
    @PastOrPresent
    private LocalDate dateOfJoining;
    @AssertTrue
    private Boolean isActive;
    @Positive(message = "Salary cant be Negative or 0 ")
    @DecimalMin(value = "10" ,message = "Salary should be greater than this")
    private Long salary;
}
