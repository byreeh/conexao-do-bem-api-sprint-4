package br.com.fiap.excecoes;

public class RecursoNaoEncontradoException extends RuntimeException {

        public RecursoNaoEncontradoException(String recurso, Long id) {
            super(recurso + " com ID " + id + " não encontrado.");
        }
    }
}
