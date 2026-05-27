package com.registration.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RegistrationUpdateDTO {

    @NotNull
    @FutureOrPresent(message="Training start date ust be present or future")
    private LocalDate startDate;

    @NotNull
    @Future(message="Training end date must be future date")
    private LocalDate endDate;

    @NotBlank
    @Pattern(regexp="^(?i)(Morning | Afternoon | Evening)$",
            message="Choose valid slot among Morning, Afternoon, Evening")
    private String slot;

    

    /**
     * @return LocalDate return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return LocalDate return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return String return the slot
     */
    public String getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(String slot) {
        this.slot = slot;
    }

}
