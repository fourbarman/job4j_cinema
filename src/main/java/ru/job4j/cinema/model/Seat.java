package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Seat.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version 1.
 * @since 15.08.2022.
 */
public class Seat {
    private int id;
    private int posRow;
    private int cell;


    public Seat(int id, int posRow, int cell) {
        this.id = id;
        this.posRow = posRow;
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

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return id == seat.id && posRow == seat.posRow && cell == seat.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, posRow, cell);
    }

    @Override
    public String toString() {
        return "Seats{"
                + "id=" + id
                + ", pos_row=" + posRow
                + ", cell=" + cell
                + '}';
    }
}
