package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;

import javax.naming.OperationNotSupportedException;

public class Habitaciones {

    private int capacidad;
    private int tamano;
    private Habitacion[] habitaciones;


    public Habitaciones(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.habitaciones = new Habitacion[capacidad];
    }

    // M�todo para obtener una copia profunda de la colecci�n
    public Habitacion[] get() {
        return copiaProfundaHabitaciones();
    }

    private Habitacion[] copiaProfundaHabitaciones() {
        Habitacion[] copia = new Habitacion[tamano];
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Habitacion(habitaciones[i]); // Copia profunda de cada Habitacion
        }
        return copia;
    }

    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    // M�todo para insertar habitaciones no nulas al final de la colecci�n sin admitir repetidos
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitaci�n nula.");
        }
        if (contieneHabitacion(habitacion)){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");
        }
        if (tamanoSuperado(tamano)){
            throw new OperationNotSupportedException("ERROR: No se aceptan m�s habitaciones.");
        }

        if (!contieneHabitacion(habitacion) && !tamanoSuperado(tamano)) {
            habitaciones[tamano++] = new Habitacion(habitacion); // Copia profunda de la habitaci�n
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");
        }
    }

    private boolean contieneHabitacion(Habitacion habitacion) {
        for (int i = 0; i < tamano; i++) {
            if (habitaciones[i].equals(habitacion)) {
                return true;
            }
        }
        return false;
    }

    // M�todo para buscar una habitaci�n en la colecci�n
    public Habitacion buscar(Habitacion habitacion) {
        int indice = buscarIndice(habitacion);
        return (indice != -1) ? habitaciones[indice] : null;
    }

    private int buscarIndice(Habitacion habitacion) {
        for (int i = 0; i < tamano; i++) {
            if (habitaciones[i].equals(habitacion)) {
                return i;
            }
        }
        return -1;
    }

    // M�todo para borrar una habitaci�n de la colecci�n
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitaci�n nula.");
        }

        if (!contieneHabitacion(habitacion)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitaci�n como la indicada.");
        }

        int indice = buscarIndice(habitacion);
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        }
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            habitaciones[i] = habitaciones[i + 1];
        }
        habitaciones[tamano - 1] = null;
    }

    private boolean tamanoSuperado(int indice) {
        return indice >= capacidad;
    }









}
