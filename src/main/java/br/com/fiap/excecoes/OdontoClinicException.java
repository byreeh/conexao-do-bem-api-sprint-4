package br.com.fiap.excecoes;

public class OdontoClinicException extends RuntimeException {

        public OdontoClinicException(String message) {
            super(message);
        }

        public OdontoClinicException(String message, Throwable cause) {
            super(message, cause);
        }

}
