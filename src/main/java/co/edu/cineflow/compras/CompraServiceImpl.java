package co.edu.cineflow.compras;

import co.edu.cineflow.combos.ComboEntity;
import co.edu.cineflow.combos.ComboService;
import co.edu.cineflow.common.exception.BusinessException;
import co.edu.cineflow.common.exception.NotFoundException;
import co.edu.cineflow.common.mongo.SequenceService;
import co.edu.cineflow.funciones.FuncionEntity;
import co.edu.cineflow.funciones.FuncionService;
import co.edu.cineflow.usuarios.UsuarioEntity;
import co.edu.cineflow.usuarios.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CompraServiceImpl implements CompraService {
    private static final int MAX_BOLETAS_POR_COMPRA = 10;
    private static final String FILAS_DISPONIBLES = "ABCDEFGHIJ";
    private static final Map<String, Integer> PRECIOS_POR_FORMATO = Map.of(
            "2D", 15000,
            "3D", 18000,
            "IMAX", 23000,
            "VIP", 28000,
            "4DX", 26000,
            "XD", 21000
    );
    private static final int PRECIO_DEFAULT = 16000;

    private final CompraRepository compraRepository;
    private final FuncionService funcionService;
    private final UsuarioService usuarioService;
    private final ComboService comboService;
    private final SequenceService sequenceService;

    public CompraServiceImpl(CompraRepository compraRepository, FuncionService funcionService,
                             UsuarioService usuarioService, ComboService comboService,
                             SequenceService sequenceService) {
        this.compraRepository = compraRepository;
        this.funcionService = funcionService;
        this.usuarioService = usuarioService;
        this.comboService = comboService;
        this.sequenceService = sequenceService;
    }

    @Override
    public synchronized CompraEntity create(String email, CompraCreateDTO in) {
        UsuarioEntity usuario = usuarioService.findByEmail(email);
        FuncionEntity funcion = funcionService.findById(in.getIdFuncion());

        if (funcion.getFechaHoraFin() != null && !funcion.getFechaHoraFin().isAfter(LocalDateTime.now())) {
            throw new BusinessException("No se pueden comprar boletas para una función que ya finalizó.");
        }

        List<String> sillasNormalizadas = normalizarYValidarSillas(in.getSillas(), funcion);

        if (compraRepository.existeSillaOcupada(funcion.getId(), sillasNormalizadas)) {
            throw new BusinessException("Una o más de las sillas seleccionadas ya se encuentran ocupadas.");
        }

        int precioBoleta = calcularPrecioBoleta(funcion);
        List<CompraComboItem> itemsCombos = construirItemsCombos(in.getCombos());

        int subtotalBoletas = sillasNormalizadas.size() * precioBoleta;
        int subtotalCombos = itemsCombos.stream()
                .mapToInt(item -> item.getPrecio() * item.getCantidad())
                .sum();

        Long idCompra = sequenceService.next("compras");

        CompraEntity compra = new CompraEntity();
        compra.setId(idCompra);
        compra.setCodigo(generarCodigoCompra(idCompra));
        compra.setUsuario(usuario);
        compra.setFuncion(funcion);
        compra.setSillas(sillasNormalizadas);
        compra.setPrecioBoleta(precioBoleta);
        compra.setCombos(itemsCombos);
        compra.setTotal(subtotalBoletas + subtotalCombos);
        compra.setFechaCompra(LocalDateTime.now());
        compra.setIsActivo(true);

        return compraRepository.save(compra);
    }

    @Override
    public CompraEntity findById(Long id, String email, boolean isAdmin) {
        CompraEntity compra = compraRepository.findByIdAndIsActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Compra no encontrada con ID " + id + "."));

        if (!isAdmin && (compra.getUsuario() == null || !email.equals(compra.getUsuario().getEmail()))) {
            throw new AuthorizationDeniedException("Acceso denegado.", new AuthorizationDecision(false));
        }

        return compra;
    }

    @Override
    public List<CompraEntity> findByUsuario(String email) {
        UsuarioEntity usuario = usuarioService.findByEmail(email);
        return compraRepository.findByUsuarioIdAndIsActivoTrue(usuario.getId());
    }

    @Override
    public Page<CompraEntity> findAll(Pageable pageable) {
        return compraRepository.findAllByIsActivoTrue(pageable);
    }

    @Override
    public List<String> findSillasOcupadas(Long idFuncion) {
        funcionService.findById(idFuncion);

        Set<String> sillas = new LinkedHashSet<>();
        List<CompraEntity> compras = compraRepository.findByFuncionIdAndIsActivoTrue(idFuncion);

        for (CompraEntity compra : compras) {
            if (compra.getSillas() != null) {
                sillas.addAll(compra.getSillas());
            }
        }

        return sillas.stream()
                .sorted(Comparator.comparingInt((String s) -> s.charAt(0))
                        .thenComparingInt(s -> Integer.parseInt(s.substring(1))))
                .toList();
    }

    @Override
    public boolean existenComprasPorFuncion(Long idFuncion) {
        return !compraRepository.findByFuncionIdAndIsActivoTrue(idFuncion).isEmpty();
    }

    private List<String> normalizarYValidarSillas(List<String> sillasInput, FuncionEntity funcion) {
        if (sillasInput == null || sillasInput.isEmpty()) {
            throw new BusinessException("Debes seleccionar al menos una silla.");
        }

        if (sillasInput.size() > MAX_BOLETAS_POR_COMPRA) {
            throw new BusinessException("Puedes comprar máximo " + MAX_BOLETAS_POR_COMPRA + " boletas por compra.");
        }

        long seed = funcion.getSala() != null && funcion.getSala().getId() != null
                ? funcion.getSala().getId()
                : 1L;
        int maxRows = 7 + (int) (seed % 3);
        int maxCols = 9 + (int) (seed % 3);

        Set<String> unicas = new LinkedHashSet<>();

        for (String raw : sillasInput) {
            if (raw == null || raw.isBlank()) {
                throw new BusinessException("El código de la silla no puede estar vacío.");
            }

            String silla = raw.trim().toUpperCase(Locale.ROOT);
            if (!silla.matches("^[A-J]\\d{1,2}$")) {
                throw new BusinessException("El formato de la silla '" + raw + "' no es válido.");
            }

            char fila = silla.charAt(0);
            int filaIndex = FILAS_DISPONIBLES.indexOf(fila);
            int columna = Integer.parseInt(silla.substring(1));

            if (filaIndex < 0 || filaIndex >= maxRows || columna < 1 || columna > maxCols) {
                throw new BusinessException("La silla '" + silla + "' no existe en la sala asignada a la función.");
            }

            if (!unicas.add(silla)) {
                throw new BusinessException("No puedes seleccionar la misma silla más de una vez (" + silla + ").");
            }
        }

        return unicas.stream()
                .sorted(Comparator.comparingInt((String s) -> s.charAt(0))
                        .thenComparingInt(s -> Integer.parseInt(s.substring(1))))
                .toList();
    }

    private List<CompraComboItem> construirItemsCombos(List<CompraComboCreateDTO> combosInput) {
        if (combosInput == null || combosInput.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, Integer> cantidadesPorCombo = new LinkedHashMap<>();
        for (CompraComboCreateDTO itemDto : combosInput) {
            if (itemDto.getIdCombo() == null) {
                throw new BusinessException("El ID del combo es requerido.");
            }
            if (itemDto.getCantidad() == null || itemDto.getCantidad() < 1) {
                throw new BusinessException("La cantidad del combo debe ser al menos 1.");
            }
            cantidadesPorCombo.merge(itemDto.getIdCombo(), itemDto.getCantidad(), Integer::sum);
        }

        List<CompraComboItem> resultado = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cantidadesPorCombo.entrySet()) {
            ComboEntity combo = comboService.findById(entry.getKey());

            CompraComboItem item = new CompraComboItem();
            item.setIdCombo(combo.getId());
            item.setNombre(combo.getNombre());
            item.setPrecio(combo.getPrecio());
            item.setCantidad(entry.getValue());

            resultado.add(item);
        }

        return resultado;
    }

    private int calcularPrecioBoleta(FuncionEntity funcion) {
        if (funcion.getSala() == null || funcion.getSala().getFormato() == null) {
            return PRECIO_DEFAULT;
        }
        String formato = funcion.getSala().getFormato().trim().toUpperCase(Locale.ROOT);
        return PRECIOS_POR_FORMATO.getOrDefault(formato, PRECIO_DEFAULT);
    }

    private String generarCodigoCompra(Long idCompra) {
        String timestamp = Long.toString(System.currentTimeMillis(), 36).toUpperCase(Locale.ROOT);
        int random = ThreadLocalRandom.current().nextInt(100, 1000);
        return "CF-" + timestamp + random + "-" + idCompra;
    }
}
