package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros.exception;

import java.io.Serial;

public class UserNonexistentException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNonexistentException(String msg) {
        super(msg);
    }
}
