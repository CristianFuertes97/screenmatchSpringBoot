package com.alura.pruebaScreenmatch.servidor;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
