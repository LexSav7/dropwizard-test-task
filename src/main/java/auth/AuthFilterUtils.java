package auth;

import auth.jwt.JwtAuthenticator;
import auth.jwt.JwtAuthorizer;
import auth.jwt.Secrets;
import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;
import auth.basic.BasicAuthenticator;

import data.User;

public class AuthFilterUtils {

	public BasicCredentialAuthFilter<PrincipalImpl> buildBasicAuthFilter() {
		return new BasicCredentialAuthFilter.Builder<PrincipalImpl>().setAuthenticator(new BasicAuthenticator()).setPrefix("Basic")
				.buildAuthFilter();
	}

	public AuthFilter<JwtContext, User> buildJwtAuthFilter() {
		final JwtConsumer consumer = new JwtConsumerBuilder()
				.setAllowedClockSkewInSeconds(300)
				.setRequireSubject()
				.setVerificationKey(new HmacKey(Secrets.JWT_SECRET_KEY)).build();

		return new JwtAuthFilter.Builder<User>()
				.setJwtConsumer(consumer)
				.setRealm("realm")
				.setPrefix("Bearer")
				.setAuthenticator(new JwtAuthenticator()).setAuthorizer(new JwtAuthorizer()).buildAuthFilter();
	}
}
