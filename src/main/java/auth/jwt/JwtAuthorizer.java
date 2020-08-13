package auth.jwt;

import data.User;
import io.dropwizard.auth.Authorizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtAuthorizer implements Authorizer<User> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizer.class);

	@Override
	public boolean authorize(User user, String requiredRole) {
		if (user == null) {
			LOGGER.warn("user object was null");
			return false;
		}

		String roles = user.getRoles();
		if (roles == null) {
			LOGGER.warn("roles were null, user={}, userId={}", user.getName(), user.getId());
			return false;
		}
		return roles.contains(requiredRole);
	}
}