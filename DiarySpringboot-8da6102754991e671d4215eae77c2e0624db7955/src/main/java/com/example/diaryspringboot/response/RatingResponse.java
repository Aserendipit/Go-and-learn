package com.example.diaryspringboot.response;
import com.example.diaryspringboot.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private String rating;

    public RatingResponse(Rating rating){
        this.rating = rating.getRating();
    }


}

