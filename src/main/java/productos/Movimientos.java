package productos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public record Movimientos(long id, String tipoMovimiento, String estado, LocalDate fecha, LocalTime hora, long cuentaId) implements Serializable {

}
