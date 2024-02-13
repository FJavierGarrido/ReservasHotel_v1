package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {



    private final String ER_TELEFONO= "[6|7|8|9][0-9]{8}";
    private final String ER_CORREO="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String ER_DNI= "([0-9]{8})([a-zA-Z])";
    public String FORMATO_FECHA="\\d{2}/\\d{2}/\\d{4}";


    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    //Constructor
    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) {

        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    // Constructor copia
    public Huesped(Huesped h) {
        if (h==null){
            throw new NullPointerException("ERROR: No es posible copiar un hu�sped nulo.");
        }
        this.nombre = h.getNombre();
        this.dni = h.getDni();
        this.correo = h.getCorreo();
        this.telefono = h.getTelefono();
        this.fechaNacimiento = h.getFechaNacimiento();
    }


    private String formateaNombre(String nombre) {

        if (nombre==null) {
            throw new NullPointerException("ERROR: El nombre de un hu�sped no puede ser nulo.");
        }
        if (nombre.trim().isEmpty()){
            throw new IllegalArgumentException("ERROR: El nombre de un hu�sped no puede estar vac�o.");
        }

        //elimina espacios en blanco del principio y final y divide palabras
        nombre = nombre.trim();
        String[] palabras =nombre.split("\\s");
        StringBuilder nombreFormateado = new StringBuilder();

        //recorre cada palabra y pone may�scula la primera letra, min�sculas las dem�s y un espacio al final
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                palabra = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();

                // A�ade la palabra formateada al StringBuilder
                nombreFormateado.append(palabra).append(" ");
            }
        }
        return nombreFormateado.toString().trim();
    }


    private String comprobarLetraDni(String dni) {

        if (dni == null) {
            throw new NullPointerException("ERROR: El DNI no puede ser nulo");
        }

        if (!dni.matches(ER_DNI)) {
            throw new IllegalArgumentException("ERROR: Formato de DNI no v�lido");
        }


        // Expresi�n regular para validar un DNI espa�ol (8 d�gitos y una letra al final)
        Pattern pattern = Pattern.compile(ER_DNI);
        Matcher matcher = pattern.matcher(dni);

        // Verificar si el formato del DNI es correcto
        if (matcher.matches()) {
            // Obtener el n�mero y la letra del DNI usando grupos
            String numeroDni = matcher.group(1);
            String letraDni = matcher.group(2).toUpperCase(); // Convertir a may�sculas

            // Calcular la letra correspondiente al n�mero del DNI directamente
            String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";

            // Verificar si la letra pasada es v�lida
            char letraEsperada = letrasValidas.charAt(Integer.parseInt(numeroDni) % 23);
            if (letraDni.charAt(0) == letraEsperada) {
                return dni;
            } else {
                throw new IllegalArgumentException("ERROR: La letra del dni del hu�sped no es correcta.");
            }
        } else {
            // El formato del DNI no es v�lido
            throw new IllegalArgumentException("Formato de DNI no v�lido");
        }
    }




    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre){

        if (nombre==null) {
            throw new NullPointerException("ERROR: El nombre de un hu�sped no puede ser nulo.");
        }
        if (nombre.trim().isEmpty()){
            throw new IllegalArgumentException("ERROR: El nombre de un hu�sped no puede estar vac�o.");
        }

        this.nombre=formateaNombre(nombre);
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono==null){
            throw new NullPointerException("ERROR: El tel�fono de un hu�sped no puede ser nulo.");
        }
        if (telefono.trim().isEmpty() || !telefono.matches(ER_TELEFONO)) {
            throw new IllegalArgumentException("ERROR: El tel�fono del hu�sped no tiene un formato v�lido.");
        }
            this.telefono = telefono;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo==null){
            throw new NullPointerException("ERROR: El correo de un hu�sped no puede ser nulo.");
        }
        if (correo.trim().isEmpty() || !correo.matches(ER_CORREO)) {
            throw new IllegalArgumentException("ERROR: El correo del hu�sped no tiene un formato v�lido.");
        }
            this.correo = correo;
    }

    public String getDni(){
        return dni;
    }

    private void setDni(String dni) {

        if (dni==null){
            throw new NullPointerException("ERROR: El dni de un hu�sped no puede ser nulo.");
        }
        if (dni.trim().isEmpty() || !dni.matches(ER_DNI)){
            throw new IllegalArgumentException("ERROR: El dni del hu�sped no tiene un formato v�lido.");
        }else {
            comprobarLetraDni(dni);
            this.dni=dni;
        }

    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }




    // Formato de fecha para el parsing
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private void setFechaNacimiento(LocalDate fechaNacimiento) {

        if (fechaNacimiento==null){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un hu�sped no puede ser nula.");
        }
        // Verificar el formato de la fecha como cadena
        String fechaComoCadena = fechaNacimiento.format(DATE_FORMAT);

        if (!fechaComoCadena.matches(FORMATO_FECHA)) {
            throw new IllegalArgumentException("ERROR: Formato de fecha de nacimiento no v�lido");
        }

        this.fechaNacimiento = fechaNacimiento;
    }


    private String getIniciales() {
        StringBuilder iniciales = new StringBuilder();

        // Dividir el nombre en palabras
        String[] palabras = nombre.split(" ");

        // Obtener la inicial de cada palabra
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                iniciales.append(palabra.charAt(0));
            }
        }
        return iniciales.toString().toUpperCase();
    }


    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Huesped huesped = (Huesped) o;
        return Objects.equals(dni, huesped.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    public String toString() {
        return  "nombre=" + getNombre() +
                " ("+getIniciales()+
                "), DNI=" + getDni() +
                ", correo=" + getCorreo() +
                ", tel�fono=" + getTelefono()  +
                ", fecha nacimiento=" + getFechaNacimiento().format(DATE_FORMAT);
    }

}