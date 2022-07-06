package ru.job4j.cinema.model;

import java.util.Objects;

public class Seat {
    private int id;
    private int pos_row;
    private int cell;


    public Seat(int id, int pos_row, int cell) {
        this.id = id;
        this.pos_row = pos_row;
        this.cell = cell;
    }

    public Seat() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos_row() {
        return pos_row;
    }

    public void setPos_row(int pos_row) {
        this.pos_row = pos_row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id == seat.id && pos_row == seat.pos_row && cell == seat.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pos_row, cell);
    }

    @Override
    public String toString() {
        return "Seats{"
                + "id=" + id
                +", pos_row=" + pos_row
                +", cell=" + cell
                + '}';
    }
}
