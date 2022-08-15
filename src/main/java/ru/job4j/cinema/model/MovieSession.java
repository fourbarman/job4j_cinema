package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * MovieSession.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 17.06.2022.
 */
public class MovieSession {
    private int id;
    private String name;

    public MovieSession() {
    }

    public MovieSession(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieSession movieSession = (MovieSession) o;
        return id == movieSession.id && Objects.equals(name, movieSession.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Session{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
