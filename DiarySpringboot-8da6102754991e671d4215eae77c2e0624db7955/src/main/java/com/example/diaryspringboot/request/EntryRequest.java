package com.example.diaryspringboot.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntryRequest {
    @NotBlank
    private String entryName;
    @NotBlank
    private String date;

    @NotNull
    @Valid
    private RatingRequest rating;

}
