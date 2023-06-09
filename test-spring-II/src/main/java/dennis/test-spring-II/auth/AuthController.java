package dennis.testspringII.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dennis.testspringII.auth.payload.AuthenticationSuccessfullPayload;
import dennis.testspringII.exception.NotFoundException;
import dennis.testspringII.exception.UnauthorizedException;
import dennis.testspringII.utente.Utente;
import dennis.testspringII.utente.UtenteService;
import dennis.testspringII.utente.payload.UtenteLoginPayload;
import dennis.testspringII.utente.payload.UtenteRegistrationPayload;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UtenteService usersService;

	@PostMapping("/register")
	public ResponseEntity<Utente> register(@RequestBody @Validated UtenteRegistrationPayload body) {
		Utente createdUser = usersService.create(body);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody UtenteLoginPayload body)
			throws NotFoundException {

		// 1. Verificare che l'email dell'utente sia presente nel db
		Utente user = usersService.findByEmail(body.getEmail());
		// 2. In caso affermativo devo verificare che la pw corrisponda a quella trovata
		// nel db
		if (!body.getPassword().matches(user.getPassword()))
			throw new UnauthorizedException("Credenziali non valide");
		// 3. Se tutto ok --> genero
		String token = JWTTools.createToken(user);
		// 4. Altrimenti --> 401 ("Credenziali non valide")

		return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token), HttpStatus.OK);
	}

}