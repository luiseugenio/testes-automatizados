package br.treinamento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.treinamento.banco.util.DBUnitManager;
import br.treinamento.dao.EntidadeDAO;
import br.treinamento.dao.EntidadeDAOInterface;
import br.treinamento.util.CriadorEntidade;

/**
 * 
 * @author luis
 */
public class EntidadeNegocioTest {

	private EntidadeNegocio entidadeNegocio;
	private Entidade entidade;
	private EntidadeDAOInterface persistencia;
	private static DBUnitManager dbUnitManager;
	private String dbUnitXmlPath = "/br/treinamento/banco/xml/input.xml";

	@BeforeClass
	public static void setUpClass() {
		dbUnitManager = new DBUnitManager();
	}

	@Before
	public void setUp() {
		entidade = new CriadorEntidade().comId(1L).comNome("Luis Eugenio").comNumeroDocumento(1L).comTipoDocumento(1).constroi();
		persistencia = new EntidadeDAO();
		entidadeNegocio = new EntidadeNegocio();
		entidadeNegocio.setPersistencia(persistencia);
		dbUnitManager.cleanAndInsert(dbUnitXmlPath);
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
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("O tipo do documento é obrigatório", e.getMessage());
		}
	}

	@Test
	public void testTipoDocumentoDeveSer1ou2() throws Exception {
		entidade.setTipoDocumento(0);
		try {
			entidadeNegocio.salvar(entidade);
			fail("Linha não deve ser executada");
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
			assertEquals("A data inicial não pode ser menor que a data atual", e.getMessage());
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
			assertEquals("A data final não pode ser menor que a data inicial", e.getMessage());
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
			assertEquals("O período deve ser informado por completo", e.getMessage());
		}
	}

	@Test
	public void testEntidadeComTipoUmNaoPodemSerRemovidas() {
		try {
			entidade.setTipoDocumento(1);
			entidadeNegocio.excluir(entidade);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Não é possível excluir entidades com cpf", e.getMessage());
		}
	}

	@Test
	public void testEntidadeComTipoDoisPodemSerRemovidas() throws Exception {
		entidade.setTipoDocumento(2);
		entidadeNegocio.excluir(entidade);
		assertNull(entidadeNegocio.getById(entidade.getId()));
	}

	@Test
	public void testNaoEhPossivelSalvarComNomesIguais() throws Exception {
		Entidade entidadeComNomeRepetido = new CriadorEntidade().comId(1L).comNome("Luis Eugenio").comNumeroDocumento(1L).comTipoDocumento(1).constroi();
		try {
			entidadeNegocio.salvar(entidadeComNomeRepetido);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Já existe entidade cadastrada com este nome.", e.getMessage());
		}
	}

	@Test
	public void testSalvar() throws Exception {
		Entidade entidadeComNovoNome = new CriadorEntidade().comId(1L).comNome("Chico da Silva").comNumeroDocumento(1L).comTipoDocumento(1).constroi();
		Entidade entidadeAlterada = entidadeNegocio.salvar(entidadeComNovoNome);
		assertNotNull(entidadeAlterada.getId());
	}

	@Test
	public void testVerificarUnicidadeNomeRepetidoDeveRetornarFalseCasoNaoExista() throws Exception {
		Entidade entidadeComNomeNovo = new CriadorEntidade().comId(1L).comNome("Joao da Silva").comNumeroDocumento(1L).comTipoDocumento(1).constroi();
		assertFalse(entidadeNegocio.verificarUnicidadeNome(entidadeComNomeNovo));
	}

	@Test
	public void testVerificarUnicidadeNomeRepetidoDeveRetornarTrueCasoJaExista() throws Exception {
		Entidade entidadeComNomeRepetido = new CriadorEntidade().comId(1L).comNome("Luis Eugenio").comNumeroDocumento(1L).comTipoDocumento(1).constroi();
		assertTrue(entidadeNegocio.verificarUnicidadeNome(entidadeComNomeRepetido));
	}

	@Test
	public void testGetQuantidadeRegistros() throws Exception {
		assertEquals(1, entidadeNegocio.getQuantidadeRegistros());
	}
	
	@Test
	public void testNaoEhPossivelAlterarNomeDaEntidade() throws Exception {
		Entidade entidadeAlterada = entidadeNegocio.getById(entidade.getId());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		entidadeAlterada.setDataInicial(cal.getTime());
		cal.add(Calendar.DATE, 3);
		entidadeAlterada.setDataFinal(cal.getTime());
		entidadeAlterada.setNome("Luis Eugenio Alterado");
		try {
			entidadeNegocio.alterar(entidadeAlterada);
			fail("Linha não deve ser executada");
		} catch (Exception e) {
			assertEquals("Não é possível alterar o nome da entidade", e.getMessage());
		}
	}

	@Test
	public void testAlterarDeveRetornarEntidadeComCamposAlterados() throws Exception {
		Entidade entidadeAlterada = entidadeNegocio.getById(entidade.getId());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		Date data = cal.getTime();
		entidadeAlterada.setDataInicial(data);
		entidade.setDataInicial(data);
		cal.add(Calendar.DATE, 3);
		data = cal.getTime();
		entidadeAlterada.setDataFinal(data);
		entidade.setDataFinal(data);
		
		assertEquals(entidadeNegocio.alterar(entidade), entidadeAlterada);
	}

}
