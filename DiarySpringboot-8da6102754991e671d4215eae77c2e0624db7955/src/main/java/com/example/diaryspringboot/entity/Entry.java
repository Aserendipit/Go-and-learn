package com.example.diaryspringboot.entity;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.diaryspringboot.request.EntryRequest;


@Entity
@Table(name="entries")
@Getter
@Setter
@NoArgsConstructor

public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "entry_name", nullable = false)
    private String entryName;
    @Column(name = "date", nullable = false)
    private String date;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Rating rating;

    public Entry(EntryRequest entryRequest)
    {
        entryName = entryRequest.getEntryName();
        date = entryRequest.getDate();
        rating = new Rating(entryRequest.getRating());
    }

}
