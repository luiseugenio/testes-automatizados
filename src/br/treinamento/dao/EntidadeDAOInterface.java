package br.treinamento.dao;

import java.util.List;

import br.treinamento.Entidade;

public interface EntidadeDAOInterface {

    Entidade salvar(Entidade entidade) throws Exception;

    Entidade alterar(Entidade entidade) throws Exception;

    void excluir(Entidade entidade) throws Exception;

    Entidade getById(Long id) throws Exception;

    int getQuantidadeRegistros() throws Exception;

    List<Entidade> retrieveAll() throws Exception;

    boolean verificarUnicidadeNome(Entidade entidade) throws Exception;
}