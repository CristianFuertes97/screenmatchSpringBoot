package com.alura.pruebaScreenmatch.principal;

import com.alura.pruebaScreenmatch.modelos.DatosEpisodio;
import com.alura.pruebaScreenmatch.modelos.DatosSerie;
import com.alura.pruebaScreenmatch.modelos.DatosTemporada;
import com.alura.pruebaScreenmatch.modelos.Episodio;
import com.alura.pruebaScreenmatch.servidor.ConsumoAPI;
import com.alura.pruebaScreenmatch.servidor.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    //creamos constantes
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=44d23fad";

    // Creamos la funcion que nos muestra en pantalla los datos de la pelicula u seria buscada

    public void muestraElMenu(){
        //preguntamos el nombre de la pelicula
        System.out.println("Ingrese el nombre de la serie a buscar:");
        var serie = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE+serie.replace(" ","+")+API_KEY);

        DatosSerie datosSerie = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datosSerie);

       //busca los datos de todas las temporadas de la serie
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datosSerie.totalTemporadas() ; i++) {
            json = consumoApi.obtenerDatos(URL_BASE +serie.replace(" ","+")+"&Season="+ i +API_KEY);
            DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }

        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        /*System.out.println("********TOP 5 EPISODIOS********");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

         */

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());

        System.out.println("***********TOP 5 EPISODIOS***********");
        episodios.stream()
                .filter(m -> !m.getEvaluacion().equals("N/A"))
                .sorted(Comparator.comparing(Episodio::getEvaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

        /*buscar episodio por año
        System.out.println("Ingrese el año a partir del cual deseas buscar");
        var anio = sc.nextInt();
        sc.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(anio,1,1);

        //cambiar formato de fecha
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMMM/yyyy");
        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada: "+e.getTemporada()+
                                ", Episodio: "+e.getTemporada()+
                                ", Fecha de lanzamiento: "+e.getFechaDeLanzamiento().format(dtf)
                ));

         */

        //Busqueda de episodio por pedazo del titulo
        System.out.println("Ingrese el pedazo de tirulo a buscar");
        var pedazoTitulo = sc.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println("Episodio encontrado");
            System.out.println("Los datos son : "+episodioBuscado.get());
        }else{
            System.out.println("Episodio no encontrado");
        }

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio:: getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println("****Evaluaciones por temporada***");
        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e ->e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media: "+est.getAverage());
        System.out.println("Episodio con mayor puntuacion de: "+est.getMax());

    }
}
