package resources;

import auth.jwt.Secrets;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.jersey.caching.CacheControl;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

@Path("v1/jwt")
@Produces(APPLICATION_JSON)
public class JWTGenResource {

	@GET
	@Path("/generate")
	@CacheControl(noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
	public final Map<String, String> login(@Auth PrincipalImpl user) throws JoseException {
		return buildToken(user);
	}

	private Map<String, String> buildToken(PrincipalImpl user) {

		final JwtClaims claims = new JwtClaims();
		claims.setSubject("1");
		claims.setStringClaim("role", "ADMIN");
		claims.setStringClaim("user", user.getName());
		claims.setIssuedAtToNow();
		claims.setGeneratedJwtId();

		final JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setAlgorithmHeaderValue(HMAC_SHA256);
		jws.setKey(new HmacKey(Secrets.JWT_SECRET_KEY));

		Map token = null;
		try {
			token = singletonMap("token", jws.getCompactSerialization());
		}
		catch (JoseException e) { e.printStackTrace(); }
		return token;
	}
}