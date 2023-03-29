package com.thevirtugroup.postitnote.model;

import java.time.Instant;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Note {

    private UUID id;

    private String name;

    private String text;

    private Instant createdTimestamp;

    private Instant updatedTimestamp;

    public Note() {

    }

    public Note(UUID id) {
        this.id = id;
    }

    public Note(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Note note = (Note) o;

        return new EqualsBuilder()
                .append(id, note.id)
                .append(name, note.name)
                .append(text, note.text)
                .append(createdTimestamp, note.createdTimestamp)
                .append(updatedTimestamp, note.updatedTimestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(text)
                .append(createdTimestamp)
                .append(updatedTimestamp)
                .toHashCode();
    }

}