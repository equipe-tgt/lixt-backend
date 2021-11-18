package br.com.ifsp.pi.lixt.data.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.ifsp.pi.lixt.dto.ItemOfPurchaseDto;
import br.com.ifsp.pi.lixt.dto.ProductOfListDto;
import br.com.ifsp.pi.lixt.dto.PurchaseDto;
import br.com.ifsp.pi.lixt.dto.PurchaseListDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseDtoData {
	
	public static PurchaseDto createPurchase(Long purchaseLocalId, List<ProductOfListDto> products) {
		return PurchaseDto.builder()
				.purchaseLocalId(purchaseLocalId)
				.purchasePrice(BigDecimal.TEN)
				.purchaseDate(LocalDateTime.now())
				.purchaseLists(createPurchaseLists(products))
				.build();
	}
	
	public static List<PurchaseListDto> createPurchaseLists(List<ProductOfListDto> products) {
		List<PurchaseListDto> purchaseLists = new ArrayList<>();
		List<ItemOfPurchaseDto> items = new ArrayList<>();
		
		purchaseLists.add(
				PurchaseListDto.builder()
						.listId(products.get(0).getListId())
						.partialPurchasePrice(BigDecimal.TEN)
						.build()
		);
		
		for(ProductOfListDto product : products)
			items.add(
					ItemOfPurchaseDto.builder()
							.productId(product.getProductId())
							.productOfListId(product.getId())
							.name(product.getName())
							.price(new BigDecimal(2))
							.measureType(product.getMeasureType())
							.measureValue(product.getMeasureValue())
							.build()
			);
		
		purchaseLists.get(0).setItemsOfPurchase(items);
		return purchaseLists;
	}

}
