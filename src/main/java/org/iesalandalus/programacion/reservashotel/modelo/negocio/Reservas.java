package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Reservas {

    private int capacidad;
    private int tamano;
    private Reserva[] reservas;

    public Reservas(int capacidad)  {
        if(capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano=0;
        this.reservas=new Reserva[capacidad];
    }

    public Reserva[] get() {
        return copiaProfundaReservas();
    }


    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }

        if (buscar(reserva) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }



        if (!capacidadSuperada(tamano)) {
            reservas[tamano] = reserva;
            tamano++;
        } else {
            throw new OperationNotSupportedException("ERROR: No se aceptan m�s reservas.");
        }
    }


    private Reserva[] copiaProfundaReservas() {
        Reserva[] copiaProfunda = new Reserva[tamano];
        for (int i = 0; i < tamano; i++) {
            copiaProfunda[i] = new Reserva(reservas[i]);
        }
        return copiaProfunda;
    }

    private int buscarIndice(Reserva reserva) {

        for (int i = 0; i < tamano; i++) {
            if (reservas[i].equals(reserva)) {
                return i;
            }
        }
        return -1;
    }

    private boolean tamanoSuperado(int indice) {
        return indice >= tamano;
    }

    private boolean capacidadSuperada(int indice) {
        return indice >= capacidad;
    }

    public Reserva buscar(Reserva reserva){
        if (reserva==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitaci�n nula.");
        }




        int indice = buscarIndice(reserva);
        if (indice != -1 && !tamanoSuperado(indice)) {
            return new Reserva(reservas[indice]);
        }
        return null;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException{
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if(buscarIndice(reserva)==-1){
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }
        int indice = buscarIndice(reserva);
        if (indice != -1 && !tamanoSuperado(indice)) {
            for (int i = indice; i < tamano - 1; i++) {
                reservas[i] = reservas[i + 1];
            }
            tamano--;
        }
    }

    public Reserva[] getReservas(Huesped huesped) {

        if (huesped==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");
        }
        Reserva[] reservasHuesped = new Reserva[tamano];
        int count = 0;
        for (int i = 0; i < tamano; i++) {
            if (reservas[i].getHuesped().equals(huesped)) {
                reservasHuesped[count++] = new Reserva(reservas[i]);
            }
        }
        return Arrays.copyOf(reservasHuesped, count);
    }

    public Reserva[] getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitaci�n nula.");
        }
        Reserva[] reservasTipoHabitacion = new Reserva[tamano];
        int count = 0;
        for (int i = 0; i < tamano; i++) {
            if (reservas[i].getHabitacion().getTipoHabitacion().equals(tipoHabitacion)) {
                reservasTipoHabitacion[count++] = new Reserva(reservas[i]);
            }
        }
        return Arrays.copyOf(reservasTipoHabitacion, count);
    }

    public Reserva[] getReservasFuturas(Habitacion habitacion) {

        if (habitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitaci�n nula.");
        }


        Reserva[] reservasFuturas = new Reserva[tamano];
        int count = 0;
        for (int i = 0; i < tamano; i++) {
            if (reservas[i].getHabitacion().equals(habitacion) &&
                    reservas[i].getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasFuturas[count++] = new Reserva(reservas[i]);
            }
        }
        return Arrays.copyOf(reservasFuturas, count);
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede realizar check-in de una reserva nula.");
        }
        if (fecha == null) {
            throw new NullPointerException("ERROR: La fecha de check-in no puede ser nula.");
        }
        int indice = buscarIndice(reserva);
        if (indice != -1 && !tamanoSuperado(indice)) {
            reservas[indice].setCheckIn(fecha);
        }
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede realizar check-out de una reserva nula.");
        }
        if (fecha == null) {
            throw new NullPointerException("ERROR: La fecha de check-out no puede ser nula.");
        }
        int indice = buscarIndice(reserva);
        if (indice != -1 && !tamanoSuperado(indice)) {
            reservas[indice].setCheckOut(fecha);
        }
    }



}


