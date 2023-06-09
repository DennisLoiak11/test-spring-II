package dennis.testspringII.dispositivo.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import dennis.testspringII.utente.Utente;
import dennis.testspringII.util.Stato;
import dennis.testspringII.util.Tipo;

@Data
public class DispositivoPayload {

	@NotNull(message = "Il modello è obbligatorio")
	String modello;
	@NotNull(message = "Non hai inserito un Tipo valido")
	Tipo tipo;
	@NotNull(message = "Non hai inserito unoStato valido")
	Stato stato;
	Utente utente;
}