package br.com.tads.manutencao_equipamento_api.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}