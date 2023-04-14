package productos;

import java.time.LocalDate;
import java.time.LocalTime;

public record Historial(String tipoMov, String estatus, LocalDate fecha, LocalTime hora) {

}
