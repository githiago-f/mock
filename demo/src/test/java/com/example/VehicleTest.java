package com.example;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Testes unitários para {@link Vehicle} usando Mockito para isolar {@link WeightService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class VehicleTest {

	@Mock
	private WeightService weightService;

	// instancia com limite de 100; o mock será injetado no campo weightService
	@InjectMocks
	private Vehicle vehicle;

	@Test
	public void shouldReturnTrueWhenWeightWithinLimit() {
		vehicle.addWeight(new Load(30));
		vehicle.addWeight(new Load(20)); // total 50

		when(weightService.isWeightAllowed(50, 100)).thenReturn(true);

		// executa o método sob teste antes de verificar as interações com o mock
		boolean result = vehicle.checkWeightLimit();

		verify(weightService).isWeightAllowed(50, 100);
		assertTrue(result);
	}

	@Test
	public void shouldReturnFalseWhenWeightAboveLimit() {
		vehicle.addWeight(new Load(60));
		vehicle.addWeight(new Load(50)); // total 110

		when(weightService.isWeightAllowed(110, 100)).thenReturn(false);

		assertFalse(vehicle.checkWeightLimit());
	}

	@Test(expected = RuntimeException.class)
	public void shouldPropagateExceptionFromWeightService() {
		vehicle.addWeight(new Load(10));

		when(weightService.isWeightAllowed(10, 100)).thenThrow(new RuntimeException("service failure"));

		// deve repassar a exceção lançada pelo serviço
		vehicle.checkWeightLimit();
	}
}
