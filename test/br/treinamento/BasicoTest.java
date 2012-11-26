package br.treinamento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class BasicoTest {

	private Basico negocio;
	private static Integer contador = 0;

	@BeforeClass
	public static void setUpBeforeClass() {
		System.out.println("SetUp do BeforeClass: uma única vez");
	}

	@AfterClass
	public static void tearDownAfterClass() {
		System.out.println("Tear Down do AfterClass: uma única vez");
		System.out.println("Contador = " + contador);
	}

	@Before
	public void setUpBefore() {
		System.out.println("SetUp do Before");
		negocio = new Basico();
		contador++;
	}

	@After
	public void tearDownAfter() {
		System.out.println("Tear Down After");
		negocio = null;
	}

	@Test
	public void testSomar() {
		System.out.println("teste 1");
		assertEquals(negocio.somar(1, 1), 2);
	}

	@Test
	public void testDividir() throws Exception {
		assertEquals("Resultado da divisão errado", negocio.dividir("10", "1"),
				10d, 0d);
	}

	@Test
	public void testDividirPorZero_robusto() {
		try {
			assertEquals(negocio.dividir("10", "0"), 10d, 0d);
			fail("Não deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("/ by zero", e.getMessage());
		}
	}

	@Ignore("Não lembro como faz isso")
	@Test
	public void testMDC() {
		assertEquals(negocio.mdc(1, 2, 3), 100);
	}

	@Test(expected = Exception.class)
	public void testDividirPorZero_elegante() throws Exception {
		negocio.dividir("10", "0");
	}

	@Test(timeout = 1000)
	public void infinito() {
		while (true);
	}

}
