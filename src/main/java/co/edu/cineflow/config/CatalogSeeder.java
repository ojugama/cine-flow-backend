package co.edu.cineflow.config;

import co.edu.cineflow.common.mongo.SequenceService;
import co.edu.cineflow.funciones.FuncionEntity;
import co.edu.cineflow.funciones.FuncionRepository;
import co.edu.cineflow.peliculas.PeliculaEntity;
import co.edu.cineflow.peliculas.PeliculaRepository;
import co.edu.cineflow.salas.SalaEntity;
import co.edu.cineflow.salas.SalaRepository;
import co.edu.cineflow.sucursales.SucursalEntity;
import co.edu.cineflow.sucursales.SucursalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("!test")
public class CatalogSeeder {
    private static final int[] HORAS = {14, 17, 20};

    @Bean
    public CommandLineRunner initCatalog(SucursalRepository sucursalRepository, SalaRepository salaRepository,
                                         PeliculaRepository peliculaRepository, FuncionRepository funcionRepository,
                                         SequenceService sequenceService) {
        return args -> {
            List<SucursalEntity> sucursales = sucursalRepository.count() == 0
                    ? crearSucursales(sucursalRepository, sequenceService)
                    : sucursalRepository.findAll();

            List<SalaEntity> salas = salaRepository.count() == 0
                    ? crearSalas(salaRepository, sequenceService, sucursales)
                    : salaRepository.findAll();

            List<PeliculaEntity> peliculas = peliculaRepository.count() == 0
                    ? crearPeliculas(peliculaRepository, sequenceService)
                    : peliculaRepository.findAll();

            if (funcionRepository.count() == 0 && !salas.isEmpty() && !peliculas.isEmpty()) {
                crearFunciones(funcionRepository, sequenceService, salas, peliculas);
            }
        };
    }

    private List<SucursalEntity> crearSucursales(SucursalRepository repository, SequenceService sequenceService) {
        List<SucursalEntity> sucursales = new ArrayList<>();
        sucursales.add(crearSucursal(repository, sequenceService, "CineFlow Centro", "Bucaramanga"));
        sucursales.add(crearSucursal(repository, sequenceService, "CineFlow Cabecera", "Bucaramanga"));
        sucursales.add(crearSucursal(repository, sequenceService, "CineFlow Norte", "Piedecuesta"));
        return sucursales;
    }

    private SucursalEntity crearSucursal(SucursalRepository repository, SequenceService sequenceService,
                                         String nombre, String ciudad) {
        SucursalEntity sucursal = new SucursalEntity();
        sucursal.setId(sequenceService.next("sucursales"));
        sucursal.setNombre(nombre);
        sucursal.setCiudad(ciudad);
        return repository.save(sucursal);
    }

    private List<SalaEntity> crearSalas(SalaRepository repository, SequenceService sequenceService,
                                        List<SucursalEntity> sucursales) {
        List<SalaEntity> salas = new ArrayList<>();

        if (sucursales.isEmpty()) {
            return salas;
        }

        String[][] definiciones = {
                {"Sala 1", "2D"},
                {"Sala 2", "3D"},
                {"Sala Premium", "VIP"},
                {"Sala 1", "2D"},
                {"Sala 2", "IMAX"},
                {"Sala 1", "3D"},
                {"Sala 2", "2D"}
        };

        for (int index = 0; index < definiciones.length; index++) {
            SalaEntity sala = new SalaEntity();
            sala.setId(sequenceService.next("salas"));
            sala.setSucursal(sucursales.get(index % sucursales.size()));
            sala.setNombre(definiciones[index][0]);
            sala.setFormato(definiciones[index][1]);
            salas.add(repository.save(sala));
        }

        return salas;
    }

    private List<PeliculaEntity> crearPeliculas(PeliculaRepository repository, SequenceService sequenceService) {
        List<PeliculaEntity> peliculas = new ArrayList<>();
        peliculas.add(crearPelicula(repository, sequenceService, "El Último Horizonte", "Ciencia ficción", 128,
                "Una tripulación debe decidir entre volver a casa o salvar el último refugio de la humanidad."));
        peliculas.add(crearPelicula(repository, sequenceService, "Risas de Medianoche", "Comedia", 102,
                "Un grupo de amigos descubre que la ciudad esconde mucho más de lo que imaginaban."));
        peliculas.add(crearPelicula(repository, sequenceService, "Sombras del Valle", "Suspenso", 115,
                "Una investigación periodística revela secretos que nadie quería desenterrar."));
        peliculas.add(crearPelicula(repository, sequenceService, "Aventura Salvaje", "Aventura", 95,
                "Una familia emprende un viaje inolvidable por los paisajes más extremos del continente."));
        return peliculas;
    }

    private PeliculaEntity crearPelicula(PeliculaRepository repository, SequenceService sequenceService,
                                         String titulo, String genero, Integer duracion, String sinopsis) {
        PeliculaEntity pelicula = new PeliculaEntity();
        pelicula.setId(sequenceService.next("peliculas"));
        pelicula.setTitulo(titulo);
        pelicula.setGenero(genero);
        pelicula.setDuracionMinutos(duracion);
        pelicula.setSinopsis(sinopsis);
        return repository.save(pelicula);
    }

    private void crearFunciones(FuncionRepository repository, SequenceService sequenceService,
                                List<SalaEntity> salas, List<PeliculaEntity> peliculas) {
        LocalDateTime base = LocalDateTime.now().plusDays(1)
                .withHour(HORAS[0]).withMinute(0).withSecond(0).withNano(0);

        for (int dia = 0; dia < 3; dia++) {
            for (int index = 0; index < peliculas.size(); index++) {
                PeliculaEntity pelicula = peliculas.get(index);
                SalaEntity sala = salas.get((dia + index) % salas.size());
                LocalDateTime inicio = base.plusDays(dia).withHour(HORAS[index % HORAS.length]);
                LocalDateTime fin = inicio.plusMinutes(pelicula.getDuracionMinutos() + 15);

                if (repository.existeCruceHorario(sala.getId(), inicio, fin, null)) {
                    continue;
                }

                FuncionEntity funcion = new FuncionEntity();
                funcion.setId(sequenceService.next("funciones"));
                funcion.setPelicula(pelicula);
                funcion.setSala(sala);
                funcion.setFechaHoraInicio(inicio);
                funcion.setFechaHoraFin(fin);
                repository.save(funcion);
            }
        }
    }
}
