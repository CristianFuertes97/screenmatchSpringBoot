package com.alura.pruebaScreenmatch.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodios;
    private Double evaluacion;
    private LocalDate fechaDeLanzamiento;

    public Episodio(Integer numero, DatosEpisodio d){
        this.temporada = numero;
        this.titulo = d.titulo();
        this.numeroEpisodios = d.numeroEpisodio();
        try {
            this.evaluacion = Double.valueOf(d.evaluacion());
        }catch (NumberFormatException e){
            this.evaluacion = 0.0;
        }
        try {
            this.fechaDeLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
        }catch (DateTimeParseException e){
            this.fechaDeLanzamiento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodios() {
        return numeroEpisodios;
    }

    public void setNumeroEpisodios(Integer numeroEpisodios) {
        this.numeroEpisodios = numeroEpisodios;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    @Override
    public String toString() {
        return
                "Temporada: " + temporada +
                ", Titulo: " + titulo + '\'' +
                ", Número de Episodio: " + numeroEpisodios +
                ", Evaluación: " + evaluacion +
                ", Fecha De Lanzamiento: " + fechaDeLanzamiento;
    }
}
