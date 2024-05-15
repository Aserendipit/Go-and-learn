package com.example.diaryspringboot.repository;

import com.example.diaryspringboot.entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Long> {

    public List<Entry> findAllByEntryNameIgnoreCase(String entryName);

}
