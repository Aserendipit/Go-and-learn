package com.example.diaryspringboot.controller;


import com.example.diaryspringboot.entity.Entry;
import com.example.diaryspringboot.request.EntryRequest;
import com.example.diaryspringboot.response.EntryResponse;
import com.example.diaryspringboot.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/entries")
public class EntryController {

    @Autowired
    EntryService entryService;

    @GetMapping()
    public List<EntryResponse> getAllEntries(@RequestParam(required = false) String entryName){

        List<Entry> entries = entryService.getAllEntries(entryName);
        List<EntryResponse> entryResponses = new ArrayList<>();
        entries.forEach(entry -> {
            EntryResponse entryResponse = new EntryResponse(entry);
            entryResponses.add(entryResponse);
        });
        return entryResponses;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EntryResponse addEntry(@Valid @RequestBody EntryRequest entryRequest){

        Entry savedEntry = entryService.addEntry(entryRequest);

        return new EntryResponse(savedEntry);
    }

    @PutMapping("/{id}")
    public EntryResponse updatedEntry
            (@PathVariable long id,
             @Valid @RequestBody EntryRequest entryRequest){

        Entry updatedEntry = entryService.updateEntry(id,entryRequest);
        return new EntryResponse(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable long id){ entryService.deleteEntry(id);}

}
