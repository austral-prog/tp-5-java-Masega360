package com.cinema;

/**
 * Clase que representa un cine con varias filas de asientos.
 */
public class Cinema {
    private Seat[][] seats;

    public Cinema(int[] rows) {
        seats = new Seat[rows.length][];
        initSeats(rows);
    }

    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seats[i] = new Seat[rows[i]];
        }
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    public int countAvailableSeats() {
        int count = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].isAvailable()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        if (row < 0 || row >= seats.length) {
            return null;
        }
        for (int i = 0; i < seats[row].length; i++) {
            if (seats[row][i].isAvailable()) {
                return seats[row][i];
            }
        }
        return null;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        for (int i = 0; i < seats.length; i++) {
            Seat seat = findFirstAvailableSeatInRow(i);
            if (seat != null) {
                return seat;
            }
        }
        return null;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        if (row < 0 || row >= seats.length || amount <= 0) {
            return null;
        }
        int count = 0;
        for (int i = 0; i < seats[row].length; i++) {
            if (seats[row][i].isAvailable()) {
                count++;
                if (count == amount) {
                    return seats[row][i - amount + 1];
                }
            } else {
                count = 0;
            }
        }
        return null;
    }

    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        if (amount <= 0) {
            return null;
        }
        for (int i = 0; i < seats.length; i++) {
            Seat seat = getAvailableSeatsInRow(i, amount);
            if (seat != null) {
                return seat;
            }
        }
        return null;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        if (seat == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid seat or amount");
        }
        int row = seat.getRow();
        int seatNumber = seat.getSeatNumber();
        for (int j = seatNumber; j < seatNumber + amount; j++) {
            if (j >= seats[row].length || !seats[row][j].isAvailable()) {
                throw new ArrayIndexOutOfBoundsException("Not enough available seats");
            }
            seats[row][j].takeSeat();
        }
    }

    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        if (seat == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid seat or amount");
        }
        int row = seat.getRow();
        int seatNumber = seat.getSeatNumber();
        for (int j = seatNumber; j < seatNumber + amount; j++) {
            if (j >= seats[row].length) {
                throw new ArrayIndexOutOfBoundsException("Trying to release seats out of bounds");
            }
            seats[row][j].releaseSeat();
        }
    }
}
