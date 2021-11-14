package br.com.ifsp.pi.lixt.instantiator;

import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.utils.conversion.Operators;

public abstract class ProductOfListDtoInstantiator extends Operators {

	private ProductOfListDtoInstantiator() {}

	public static String createProductOfListJson(int plannedAmount, int markedAmount, Long listId, String measureType, String measureValue, Long productId, String name) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("plannedAmount", plannedAmount)).append(COMMA)
				.append(createJsonLine("markedAmount")).append(COMMA)
				.append(createJsonLine("assignedUserId")).append(COMMA)
				.append(createJsonLine("amountComment")).append(COMMA)
				.append(createJsonLine("id")).append(COMMA)
				.append(createJsonLine("isMarked", false)).append(COMMA)
				.append(createJsonLine("listId", listId)).append(COMMA)
				.append(createJsonLine("measureType", measureType)).append(COMMA)
				.append(createJsonLine("measureValue", measureValue)).append(COMMA)
				.append(createJsonLine("name", name)).append(COMMA)
				.append(createJsonLine("price")).append(COMMA)
				.append(createJsonLine("product")).append(COMMA)
				.append(createJsonLine("userWhoMarkedId")).append(COMMA)
				.append(createJsonLine("productId", productId))
				.append(KEY_CLOSE);

		return stringBuilder.toString();
	}

	public static String createProductOfListJson(ProductOfListDto productOfList) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(KEY_OPEN)
				.append(createJsonLine("plannedAmount", productOfList.getPlannedAmount())).append(COMMA)
				.append(createJsonLine("markedAmount")).append(COMMA)
				.append(createJsonLine("assignedUserId", productOfList.getAssignedUserId())).append(COMMA)
				.append(createJsonLine("amountComment")).append(COMMA)
				.append(createJsonLine("id", productOfList.getId())).append(COMMA)
				.append(createJsonLine("isMarked", productOfList.getIsMarked())).append(COMMA)
				.append(createJsonLine("listId", productOfList.getListId())).append(COMMA)
				.append(createJsonLine("measureType", productOfList.getMeasureType().toString())).append(COMMA)
				.append(createJsonLine("measureValue", productOfList.getMeasureValue())).append(COMMA)
				.append(createJsonLine("name", productOfList.getName())).append(COMMA)
				.append(createJsonLine("price", productOfList.getPrice())).append(COMMA)
				.append(createJsonLine("product")).append(COMMA)
				.append(createJsonLine("userWhoMarkedId", productOfList.getUserWhoMarkedId())).append(COMMA)
				.append(createJsonLine("productId", productOfList.getProductId()))
				.append(KEY_CLOSE);

		return stringBuilder.toString();
	}

}
