package com.example.diaryspringboot.response;
import com.example.diaryspringboot.entity.Entry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryResponse {
    private long id;
    private String entryName;
    private String date;
    private RatingResponse rating;

    public EntryResponse(Entry entry){

        id = entry.getId();
        entryName = entry.getEntryName();
        date = entry.getDate();
        rating = new RatingResponse(entry.getRating());
    }
}
