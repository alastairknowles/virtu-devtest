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

    public Notes(List<Note> items) {
        this.items = items;
    }

    public List<Note> getItems() {
        return items;
    }

    @SuppressWarnings("unused")
    public void setItems(List<Note> items) {
        this.items = items;
    }

}