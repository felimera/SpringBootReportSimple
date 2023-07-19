package com.example.report.models.entityreport.subreport;

import lombok.Data;

@Data
public class Paciente {
    private Integer id;
    private String nombre;
    private String apellido;
    private String fecha;
    private String genero;
    private String dni;
    private String domicilio;
    private String poblacion;
    private String provincia;
    private String numHistoriaClinica;
}
