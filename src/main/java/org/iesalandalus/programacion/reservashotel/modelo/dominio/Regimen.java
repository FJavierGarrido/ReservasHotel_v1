package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {

    SOLO_ALOJAMIENTO("SOLO_ALOJAMIENTO", 0),
    ALOJAMIENTO_DESAYUNO("ALOJAMIENTO_DESAYUNO", 15),
    MEDIA_PENSION("MEDIA_PENSION", 30),
    PENSION_COMPLETA("PENSION_COMPLETA", 50);


    private final String descripcion;
    private final double incrementoPrecio;

    // Constructor
    private Regimen(String descripcion, int incrementoPrecio) {
        this.descripcion = descripcion;
        this.incrementoPrecio = incrementoPrecio;
    }

    // M�todo getter para la descripci�n
    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }



    // M�todo toString
    @Override
    public String toString() {
        return "R�gimen{" +
                "descripcion='" + descripcion + '\'' +
                ", incrementoPrecioPorPersona=" + incrementoPrecio +
                '}';
    }

}
