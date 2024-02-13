package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;

public class Huespedes {

    private int capacidad;
    private static int tamano;
    private static Huesped[] huespedes;


    public Huespedes(int capacidad) {
        if (capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad = capacidad;
        this.tamano = 0;
        this.huespedes = new Huesped[capacidad];
    }

    // M�todo para obtener una copia profunda de la colecci�n
    public static Huesped[] get() {
        return copiaProfundaHuespedes();
    }

    private static Huesped[] copiaProfundaHuespedes() {
        Huesped[] copia = new Huesped[tamano];
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Huesped(huespedes[i]);  // Copia profunda de cada Huesped
        }
        return copia;
    }

    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }


    // M�todo para insertar hu�spedes no nulos al final de la colecci�n sin admitir repetidos
    public void insertar(Huesped huesped) throws OperationNotSupportedException  {

            if (huesped == null) {
                throw new NullPointerException("ERROR: No se puede insertar un hu�sped nulo.");
            }
            if (contieneHuesped(huesped)){
            throw new OperationNotSupportedException("ERROR: Ya existe un hu�sped con ese dni.");
            }
            if (tamanoSuperado(tamano)){
            throw new OperationNotSupportedException("ERROR: No se aceptan m�s hu�spedes.");
            }

            if (!contieneHuesped(huesped) && !tamanoSuperado(tamano)) {
                huespedes[tamano++] = new Huesped(huesped); // Copia profunda del hu�sped
            } else {
                throw new OperationNotSupportedException("ERROR: No se aceptan m�s hu�spedes.");
            }

    }

    private boolean contieneHuesped(Huesped huesped) {
        for (int i = 0; i < tamano; i++) {
            if (huespedes[i].equals(huesped)) {
                return true;
            }
        }
        return false;
    }




    // M�todo para buscar un hu�sped en la colecci�n
    public Huesped buscar(Huesped huesped) {
        int indice = buscarIndice(huesped);
        return (indice != -1) ? huespedes[indice] : null;
    }

    private int buscarIndice(Huesped huesped) {
        for (int i = 0; i < tamano; i++) {
            if (huespedes[i].equals(huesped)) {
                return i;
            }
        }
        return -1;
    }


    // M�todo para borrar un hu�sped de la colecci�n
    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped==null){
            throw new NullPointerException("ERROR: No se puede borrar un hu�sped nulo.");
        }
        if (!contieneHuesped(huesped)){
            throw new OperationNotSupportedException("ERROR: No existe ning�n hu�sped como el indicado.");
        }


        int indice = buscarIndice(huesped);
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        }
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            huespedes[i] = huespedes[i + 1];
        }
        huespedes[tamano - 1] = null;
    }

    private boolean tamanoSuperado(int indice) {
            return indice >= capacidad;
        }



}
