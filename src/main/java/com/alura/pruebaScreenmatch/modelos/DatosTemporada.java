package com.alura.pruebaScreenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporada(
        @JsonAlias("Season") Integer numero,
        @JsonAlias("Episodes") List<DatosEpisodio> episodios) {
}
