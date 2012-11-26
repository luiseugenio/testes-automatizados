package br.treinamento;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.treinamento.dao.EntidadeDAOInterface;

public class EntidadeNegocioComInterfaceTest {

	private EntidadeNegocioComInterface entidadeNegocio;
	private Entidade entidade;
	private EntidadeDAOInterface persistencia;

	@Before
	public void setUp() {
		entidade = getEntidadeValida();
		persistencia = EasyMock.createMock(EntidadeDAOInterface.class);
		entidadeNegocio = new EntidadeNegocioComInterface();
		entidadeNegocio.setPersistencia(persistencia);
	}

	private Entidade getEntidadeValida() {
		Entidade entidade = new Entidade();
		entidade.setNome("Luis Eugenio");
		entidade.setNumeroDocumento(1L);
		entidade.setTipoDocumento(1);
		entidade.setEmail("luis@gmail.com.br");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		entidade.setDataInicial(cal.getTime());
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		entidade.setDataFinal(cal.getTime());
		return entidade;
	}

	@After
	public void tearDown() {
		entidadeNegocio = null;
		entidade = null;
	}

	@Test
	public void testNomeObrigatorio() throws Exception {
		entidade.setNome(null);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O nome é obrigatório", e.getMessage());
		}
	}

	@Test
	public void testNomeComMenosDeCincoCaracteres() throws Exception {
		entidade.setNome("1234");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O nome não pode ter menos que 5 caracteres",
					e.getMessage());
		}
	}

	@Test
	public void testNomeComMaisDeTrintaCaracteres() throws Exception {
		entidade.setNome("1234567890123456789012345678901");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O nome não pode ter mais que 30 caracteres",
					e.getMessage());
		}
	}

	@Test
	public void testNumeroDocumentoObrigatorio() throws Exception {
		entidade.setNumeroDocumento(null);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O número do documento é obrigatório", e.getMessage());
		}
	}

	@Test
	public void testNumeroDocumentoMaiorQueZero() throws Exception {
		entidade.setNumeroDocumento(-1L);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O número do documento deve ser maior que zero",
					e.getMessage());
		}
	}

	@Test
	public void testTipoDocumentoObrigatorio() throws Exception {
		entidade.setTipoDocumento(null);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("O tipo do documento é obrigatório", e.getMessage());
		}
	}

	@Test
	public void testTipoDocumentoPodeSerUm() throws Exception {
		entidade.setTipoDocumento(1);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Tipo de documento inválido", e.getMessage());
		}
	}

	@Test
	public void testTipoDocumentoPodeSerDois() throws Exception {
		entidade.setTipoDocumento(2);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Tipo de documento inválido", e.getMessage());
		}
	}
	
	@Test
	public void testTipoDocumentoNaoDeveSerTres() throws Exception {
		entidade.setTipoDocumento(3);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Tipo de documento inválido", e.getMessage());
		}
	}

	@Test
	public void testEmailMaximoVinteCaracteres() throws Exception {
		entidade.setEmail("123456789012345678901");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
	}

	@Test
	public void testEmailValido() throws Exception {
		entidade.setEmail("luisgmailcom");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
	}

	@Test
	public void testDataInicialMenorQueDataAtual() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		entidade.setDataInicial(cal.getTime());
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("A data inicial não pode ser menor que a data atual",
					e.getMessage());
		}
	}

	@Test
	public void testDataFinalMenorQueDataInicial() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(entidade.getDataInicial());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		entidade.setDataFinal(cal.getTime());
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("A data final não pode ser menor que a data inicial",
					e.getMessage());
		}
	}

	@Test
	public void testPeriodoDeveSerMaiorQueTres() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(entidade.getDataInicial());
		cal.add(Calendar.DAY_OF_MONTH, 2);
		entidade.setDataFinal(cal.getTime());
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O período deve ser maior que 3 dias", e.getMessage());
		}
	}

	@Test
	public void testDataFinalObrigatoriaSeDataInicialPreenchida() {
		entidade.setDataFinal(null);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O período deve ser informado por completo",
					e.getMessage());
		}
	}

	@Test
	public void testEntidadeComTipoUmNaoPodemSerRemovidas() {
		try {
			entidade.setTipoDocumento(1);
			entidadeNegocio.excluir(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Não é possível excluir entidades com cpf",
					e.getMessage());
		}
	}

	@Test
	public void testEntidadeComTipoDoisPodemSerRemovidas() throws Exception {
		entidade.setTipoDocumento(2);
		entidadeNegocio.excluir(entidade);
		assertNull(entidadeNegocio.getById(entidade.getId()));
	}

	@Test
	public void testVerificarUnicidadeNomeDeveRetornarTrue() throws Exception {
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade))
				.andReturn(true);
		EasyMock.replay(persistencia);

		assertEquals(entidadeNegocio.verificarUnicidadeNome(entidade), true);
	}

	@Test
	public void testGetQuantidadeRegistrosDeveRetornarUm() throws Exception {
		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(1);
		EasyMock.replay(persistencia);

		assertEquals(entidadeNegocio.getQuantidadeRegistros(), 1);
	}

	@Test
	public void testNaoEhPossivelSalvarComNomesIguais() throws Exception {
		entidade.setNome("Luis Eugenio");
		Entidade entidadeRetornada = entidade;
		entidadeRetornada.setId(1L);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(true);
		EasyMock.expect(persistencia.salvar(entidade)).andReturn(entidadeRetornada);

		Entidade entidadeComNomeRepetido = getEntidadeValida();
		entidadeRetornada.setNome("Luis Eugenio");
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeComNomeRepetido)).andReturn(false);
		EasyMock.expect(persistencia.salvar(entidadeComNomeRepetido)).andReturn(entidadeComNomeRepetido);
		EasyMock.replay(persistencia);

		entidadeNegocio.salvar(entidade);
		try {
			entidadeNegocio.salvar(entidadeComNomeRepetido);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Já existe entidade cadastrada com este nome.", e.getMessage());
		}
	}

	@Test
	public void testNaoEhPossivelAlterarNomeDaEntidade() throws Exception {
		Entidade entidadeRetornada = getEntidadeValida();
		entidadeRetornada.setId(1L);
		entidadeRetornada.setNome("Luis Eugenio");
		
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(true);
		EasyMock.expect(persistencia.salvar(entidade)).andReturn(entidade);
		
		EasyMock.expect(persistencia.getById(EasyMock.anyLong())).andReturn(entidadeRetornada);
		EasyMock.expect(persistencia.alterar(entidade)).andReturn(entidade); 
		
		EasyMock.replay(persistencia);

		entidadeNegocio.salvar(entidade);
		try {
			entidade.setNome("Luis Eugenio Alterado");
			entidadeNegocio.alterar(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Não é possível alterar o nome da entidade", e.getMessage());
		}
	}

	@Test
	public void testAlterarDeveRetornarEntidadeComCamposAlterados()
			throws Exception {
		Entidade entidadeRetornada = entidade;
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidade)).andReturn(false);
		EasyMock.expect(persistencia.getById(entidade.getId())).andReturn(entidadeRetornada);
		EasyMock.expect(persistencia.alterar(entidade)).andReturn(entidadeRetornada);
		EasyMock.replay(persistencia);

		assertEquals(entidadeNegocio.alterar(entidade), entidadeRetornada);
	}
}
