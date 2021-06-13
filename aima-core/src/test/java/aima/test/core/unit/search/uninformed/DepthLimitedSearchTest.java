package aima.test.core.unit.search.uninformed;

import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFunctions;
import aima.core.environment.nqueens.QueenAction;
import aima.core.search.framework.Node;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.GeneralProblem;
import aima.core.search.framework.problem.Problem;
import aima.core.search.uninformed.DepthLimitedSearch;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * Tests depth-limited search.
 * 
 * @author Ruediger Lunde
 */
public class DepthLimitedSearchTest {

	@Test
	public void testSuccessfulDepthLimitedSearch() throws Exception {
		// instância um novo problema, com tabuleiro, açoes possiveis de uma dama,
		// resultado das açoes e valida se atingiu o objetivo
		Problem<NQueensBoard, QueenAction> problem = new GeneralProblem<>(new NQueensBoard(8),
				NQueensFunctions::getIFActions, 
				NQueensFunctions::getResult, // qual o resultadod aquela ação
				NQueensFunctions::testGoal); // se atingiu o objetivo
		// Busca as ações passando com parametros o tabuleiro e as açoes possíveis (utilizando busca em profundidade)
		SearchForActions<NQueensBoard, QueenAction> search = new DepthLimitedSearch<>(8);
		// Cria lista de açoes para resolução do problema
		Optional<List<QueenAction>> actions = search.findActions(problem);
		// Verifica se a lista nao é null
		Assert.assertTrue(actions.isPresent());
		// valida com a resolução ótima
		assertCorrectPlacement(actions.get());
		// resoluçao otima de nós expandidos
		/*
		 * o 113 é por ser uma arvore bidimensional = 2 e 8 por ser a profundidade da
		 * arvore ou seja, 2^8 = 256. senndo o nó 113 o menor nó com uma resolução
		 */
		Assert.assertEquals("113", search.getMetrics().get("nodesExpanded"));

	}

	// Cutoff é o limite de profundidade da busca da arvore.
	@Test
	public void testCutOff() throws Exception {
		Problem<NQueensBoard, QueenAction> problem = new GeneralProblem<>(new NQueensBoard(8),
				NQueensFunctions::getIFActions, NQueensFunctions::getResult, NQueensFunctions::testGoal);
		DepthLimitedSearch<NQueensBoard, QueenAction> search = new DepthLimitedSearch<>(1);
		Optional<Node<NQueensBoard, QueenAction>> result = search.findNode(problem);
		Assert.assertEquals(true, search.isCutoffResult(result));
	}

	@Test
	public void testFailure() throws Exception {
		Problem<NQueensBoard, QueenAction> problem = new GeneralProblem<>(new NQueensBoard(3),
				NQueensFunctions::getIFActions, NQueensFunctions::getResult, NQueensFunctions::testGoal);
		DepthLimitedSearch<NQueensBoard, QueenAction> search = new DepthLimitedSearch<>(5);
		Optional<List<QueenAction>> actions = search.findActions(problem);
		Assert.assertFalse(actions.isPresent()); // failure
	}

	//
	// PRIVATE METHODS
	//
	private void assertCorrectPlacement(List<QueenAction> actions) {
		Assert.assertEquals(8, actions.size());
		Assert.assertEquals("Action[name=placeQueenAt, location=(0, 0)]", actions.get(0).toString());
		Assert.assertEquals("Action[name=placeQueenAt, location=(1, 4)]", actions.get(1).toString());
		Assert.assertEquals("Action[name=placeQueenAt, location=(2, 7)]", actions.get(2).toString());
		Assert.assertEquals("Action[name=placeQueenAt, location=(3, 5)]", actions.get(3).toString());
		Assert.assertEquals("Action[name=placeQueenAt, location=(4, 2)]", actions.get(4).toString());
		Assert.assertEquals("Action[name=placeQueenAt, location=(5, 6)]", actions.get(5).toString());
		Assert.assertEquals("Action[name=placeQueenAt, location=(6, 1)]", actions.get(6).toString());
		Assert.assertEquals("Action[name=placeQueenAt, location=(7, 3)]", actions.get(7).toString());
	}

}
