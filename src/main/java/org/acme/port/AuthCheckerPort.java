package org.acme.port;

import jakarta.ws.rs.core.Response;
import org.acme.domain.entity.RoleName;

public interface AuthCheckerPort {

    public Response check(String token, RoleName roleRequired);
}
