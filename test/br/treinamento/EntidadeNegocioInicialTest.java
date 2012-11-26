package br.treinamento;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntidadeNegocioInicialTest {

	private EntidadeNegocioInicial entidadeNegocio;
	private Entidade entidade;

	@Before
	public void setUp() {
		entidadeNegocio = new EntidadeNegocioInicial();
		entidade = new Entidade();
		entidade.setId(1L);
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
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("O nome é obrigatório", e.getMessage());
		}
	}

	@Test
	public void testNomeComMenosDeCincoCaracteres() throws Exception {
		entidade.setNome("1234");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
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
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
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
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("O número do documento é obrigatório", e.getMessage());
		}
	}

	@Test
	public void testNumeroDocumentoMaiorQueZero() throws Exception {
		entidade.setNumeroDocumento(-1L);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
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
	public void testTipoDocumentoDeveSerUmOuDois() throws Exception {
		entidade.setTipoDocumento(0);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Tipo de documento inválido", e.getMessage());
		}
	}

	@Test
	public void testEmailNaoDeverNull() throws Exception {
		entidade.setEmail(null);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
	}

	@Test
	public void testEmailMaximoVinteCaracteres() throws Exception {
		entidade.setEmail("123456789012345678901");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
	}

	@Test
	public void testEmailNaoDeverConterApenasArroba() throws Exception {
		entidade.setEmail("luis@gmailcom");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
	}

	@Test
	public void testEmailNaoDeverConterApenasPonto() throws Exception {
		entidade.setEmail("luisgmail.com");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
	}

	@Test
	public void testEmailDeveConterArrobaEPonto() throws Exception {
		entidade.setEmail("luisgmailcom");
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}
	}

	@Test
	public void testDataInicialNull() {
		entidade.setDataInicial(null);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("A data inicial não pode ser menor que a data atual",
					e.getMessage());
		}
	}

	@Test
	public void testDataInicialMenorQueDataAtual() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		entidade.setDataInicial(cal.getTime());
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
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
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
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
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("O período deve ser maior que 3 dias", e.getMessage());
		}
	}

	@Test
	public void testDataFinalObrigatoriaSeDataInicialPreenchida() {
		entidade.setDataFinal(null);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deveria ser executada. O salvar não lançou Exception.");
		} catch (Exception e) {
			assertEquals("O período deve ser informado por completo",
					e.getMessage());
		}
	}

	@Test
	public void testVerificarUnicidadeNomeDeveRetornarFalso() throws Exception {
		assertEquals(entidadeNegocio.verificarUnicidadeNome(entidade), false);
	}

	@Test
	public void testGetQuantidadeRegistrosDeveRetornarZero() throws Exception {
		assertEquals(entidadeNegocio.getQuantidadeRegistros(), 0);
	}

	@Test
	public void testGetByIdDeveRetornarNullParaQualquerId() throws Exception {
		assertNull(entidadeNegocio.getById(entidade.getId()));
		assertNull(entidadeNegocio.getById(2L));
		assertNull(entidadeNegocio.getById(null));
	}

	@Test
	public void testExcluirNaoDeveExcluirEntidadeComTipoUm() {
		entidade.setTipoDocumento(1);
		try {
			entidadeNegocio.excluir(entidade);
			fail("Linha não deveria ser executada. O excluir não lançou Exception.");
		} catch (Exception e) {
			assertEquals("Não é possível excluir entidades com cpf",
					e.getMessage());
		}
	}

	@Test
	public void testAlterarDeveRetornarEntidadeAlterada() throws Exception {
		entidadeNegocio.salvar(entidade);
		entidade.setNome("Nome alterado agora");
		Entidade entidadeAlterada = entidadeNegocio.alterar(entidade);
		assertEquals("Nome alterado agora", entidadeAlterada.getNome());
	}
}