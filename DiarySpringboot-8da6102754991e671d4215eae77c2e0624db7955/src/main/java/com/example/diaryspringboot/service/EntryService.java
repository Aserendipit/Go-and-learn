package com.example.diaryspringboot.service;

import com.example.diaryspringboot.repository.EntryRepository;
import com.example.diaryspringboot.request.EntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diaryspringboot.entity.Entry;
import com.example.diaryspringboot.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class EntryService {

    @Autowired
    EntryRepository entryRepository;

    public List<Entry> getAllEntries(String entryNameParam){
        if(entryNameParam == null || entryNameParam.isBlank())
            return (List<Entry>) entryRepository.findAll();

        return entryRepository.findAllByEntryNameIgnoreCase(entryNameParam);

    }

    public Entry addEntry(EntryRequest entryRequest){
        Entry entry = new Entry(entryRequest);
        return entryRepository.save(entry);
    }

    public Entry updateEntry(long entryId, EntryRequest entryRequest){
        entryRepository.findById(entryId)
                .orElseThrow(()-> new ResourceNotFoundException("entry id is not found"));
        Entry entryToBeUpdated = new Entry(entryRequest);
        entryToBeUpdated.setId(entryId);

        return  entryRepository.save(entryToBeUpdated);
    }

    public void deleteEntry(long entryId){
        if(entryRepository.existsById(entryId)){
            entryRepository.deleteById(entryId);
        }
        else {
            throw new ResourceNotFoundException("entry id not found");
        }
    }
}
