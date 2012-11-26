package br.treinamento.util;

import java.util.Calendar;
import java.util.Date;

import br.treinamento.Entidade;

public class CriadorEntidade {

	private Entidade entidade;

	public CriadorEntidade() {
		entidade = new Entidade();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		entidade.setDataInicial(cal.getTime());
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		entidade.setDataFinal(cal.getTime());
	}

	public CriadorEntidade comId(Long id) {
		entidade.setId(id);
		return this;
	}

	public CriadorEntidade comNome(String nome) {
		entidade.setNome(nome);
		return this;
	}

	public CriadorEntidade comNumeroDocumento(Long numeroDocumento) {
		entidade.setNumeroDocumento(numeroDocumento);
		return this;
	}

	public CriadorEntidade comTipoDocumento(Integer tipoDocumento) {
		entidade.setTipoDocumento(tipoDocumento);
		return this;
	}

	public CriadorEntidade comEmail(String email) {
		entidade.setEmail(email);
		return this;
	}

	public CriadorEntidade comDataInicial(Date dataInicial) {
		entidade.setDataInicial(dataInicial);
		return this;
	}

	public CriadorEntidade comDataFinal(Date dataFinal) {
		entidade.setDataFinal(dataFinal);
		return this;
	}

	public Entidade constroi() {
		return entidade;
	}
}
