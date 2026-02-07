package com.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class StudentRequestDTO {
    @NotBlank
    @Pattern(regexp="^[A-Z][A-Za-z ]{2,19}$",message="Student must be 3-20 characters long")
    private String studentName;
    @Email(message="Provide valid email id")
    private String email;
    @Min(value=1000000000L, message="Phone number must be 10 digits")
    @Max(value=9999999999L, message="Phone number must be 10 digits")
    private long phoneNumber;
    @NotBlank
    @Pattern(regexp="^[A-Z]$",message="Division must be Uppercase character")
    private String divison ;

    

    /**
     * @return String return the studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @param studentName the studentName to set
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return long return the phoneNumber
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return char return the divison
     */
    public String getDivison() {
        return divison;
    }

    /**
     * @param divison the divison to set
     */
    public void setDivison(String divison) {
        this.divison = divison;
    }

}
