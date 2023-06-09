package dennis.testspringII.utente.payload;

import lombok.Getter;

@Getter
public class UtenteLoginPayload {
	String email;
	String password;
}