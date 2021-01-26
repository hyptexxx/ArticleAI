package com.example.ArticleAI;

import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import com.example.ArticleAI.modules.recomendationsResolver.service.implementations.NlpRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class NlpRequestServiceTest {
	private final List<Actuality> actualityList = new ArrayList<>();

	@Test
	public void setRecommendations(){
		actualityList.add(Actuality.builder()
				.actuality(new Random().nextInt() % 10)
				.classId(new Random().nextInt() % 10)
				.build());
	}

	@Test
	void testNlpRequest() {
		List<Recommendation> recommendations = new NlpRequestService().getRecommendations(actualityList);
		Assertions.assertNotNull(recommendations);
	}
}
