package br.com.tads.manutencaoequipamentoapi.exceptions;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}