package com.example.diaryspringboot.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import com.example.diaryspringboot.request.RatingRequest;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String rating;
    public Rating(RatingRequest ratingRequest)
    {

        rating = ratingRequest.getRating();
    }
}

