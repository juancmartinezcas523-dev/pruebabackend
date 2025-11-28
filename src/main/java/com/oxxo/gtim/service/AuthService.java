package com.oxxo.gtim.service;

import com.oxxo.gtim.dto.request.AuthRequest;
import com.oxxo.gtim.dto.response.AuthResponseFront;

public interface AuthService {

    public AuthResponseFront authenticate(AuthRequest request);
}
