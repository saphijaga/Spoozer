package de.saphijaga.spoozer.persistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jan-ericgaidusch on 31.03.16.
 */
@Document
public class History {

    @Id
    private String id;
    private List<HTrack> songs;

    public History() {
        songs = new ArrayList<>();
    }

    public void addSong(Track track) {
        songs.add(new HTrack(UUID.randomUUID().toString(), track, new Date()));
    }

    public void cutListtoLimit(int limit) {
        if (limit > -1)
            while (songs.size() > limit)
                songs.remove(0);
    }

    public List<HTrack> getSongs(){
        return songs;
    }

    public void clearHistory(){
        songs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;

        if (id != null ? !id.equals(history.id) : history.id != null) return false;
        return songs != null ? songs.equals(history.songs) : history.songs == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (songs != null ? songs.hashCode() : 0);
        return result;
    }
}
