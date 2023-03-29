package com.thevirtugroup.postitnote.model;

import static java.util.Arrays.asList;

import java.util.List;

public class Notes {

    private List<Note> items;

    public Notes() {

    }

    public Notes(Note... items) {
        this.items = asList(items);
    }

    public List<Note> getItems() {
        return items;
    }

    public void setItems(List<Note> items) {
        this.items = items;
    }

}