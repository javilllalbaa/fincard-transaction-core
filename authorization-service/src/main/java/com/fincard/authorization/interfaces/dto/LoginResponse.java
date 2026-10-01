package com.fincard.authorization.interfaces.dto;

public record LoginResponse(String accessToken, String tokenType, long expiresIn) {}
