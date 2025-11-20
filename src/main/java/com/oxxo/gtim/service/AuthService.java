package com.oxxo.gtim.service;

import com.oxxo.gtim.dto.request.AuthRequest;
import com.oxxo.gtim.dto.response.AuthResponse;

public interface AuthService {

    public AuthResponse authenticate(AuthRequest request);
}
