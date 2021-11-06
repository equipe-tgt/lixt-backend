package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import br.com.ifsp.pi.lixt.data.business.purchase.Purchase;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseRepository;
import br.com.ifsp.pi.lixt.dto.specific.PurchaseLocalDataDto;
import br.com.ifsp.pi.lixt.integration.geolocation.GeolocationService;
import br.com.ifsp.pi.lixt.mapper.PurchaseLocalMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class PurchaseLocalService {
	
	private final PurchaseLocalRepository purchaseLocalRepository;
	private final PurchaseLocalSpecRepository purchaseLocalSpecRepository;
	private final GeolocationService geolocationService;
	private final PurchaseRepository purchaseRepository;
	
	private static final double DISTANCE_IN_METERS_NEAR = 10;
	
	public PurchaseLocal findById(Long id) {
		return this.purchaseLocalRepository.findById(id).orElse(null);
	}
	
	public PurchaseLocal save(PurchaseLocal purchaseLocal) {
		return this.purchaseLocalRepository.save(purchaseLocal);
	}
	
	public void deleteById(Long id) {
		this.purchaseLocalRepository.deleteById(id);
	}
	
	public Integer updateNamePurchaseLocal(Long id, String name) {
		return this.purchaseLocalRepository.updateNamePurchaseLocal(id, name);
	}
	
	public List<PurchaseLocal> findPurchasesLocalNear(PurchaseLocal purchaseLocal) {

		List<PurchaseLocal> locals = this.purchaseLocalSpecRepository.findPurchasesLocalNear(purchaseLocal, DISTANCE_IN_METERS_NEAR);

		PurchaseLocal searchedLocal = PurchaseLocalMapper.dtoToEntity(this.geolocationService.getPurchaseLocalByCoordinates(PurchaseLocalMapper.entityToDto(purchaseLocal)));
		if(!locals.isEmpty() && Objects.nonNull(searchedLocal)){
			for(PurchaseLocal local : locals){
				if(local.getName().equalsIgnoreCase(searchedLocal.getName())){
					return locals;
				}
			}
		}

		if(Objects.nonNull(searchedLocal))
			locals.add(searchedLocal);

		return locals;
	}

	public List<PurchaseLocalDataDto> findAllPurchaseLocalRecords(Long userId) {
		List<PurchaseLocalDataDto> localsData = new ArrayList<>();
		List<Purchase> userPurchases = this.purchaseRepository.findUserPurchases(userId);

		List<String> localsNames = userPurchases.stream()
				.map(e -> e.getPurchaseLocal().getName())
				.distinct()
				.collect(Collectors.toList());

		for (String localName : localsNames) {
			PurchaseLocalDataDto localData = new PurchaseLocalDataDto();

			localData.setPurchaseLocalName(localName);

			List<Purchase> purchasesOnLocal = userPurchases.stream()
					.filter(e -> e.getPurchaseLocal().getName().equalsIgnoreCase(localName))
					.collect(Collectors.toList());
			purchasesOnLocal.sort(Comparator.comparing(Purchase::getPurchaseDate));

			Integer purchaseAmount = Math.toIntExact(purchasesOnLocal.stream().count());
			localData.setPurchaseAmount(purchaseAmount);

			localData.setFirstPurchase(purchasesOnLocal.get(0).getPurchaseDate());
			localData.setLastPurchase(purchasesOnLocal.get(purchasesOnLocal.size()-1).getPurchaseDate());

			BigDecimal totalValue = purchasesOnLocal.stream()
					.map(e -> e.getPurchasePrice())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			localData.setTotalValue(totalValue);

			BigDecimal averageValue = totalValue.divide(BigDecimal.valueOf(purchaseAmount))
					.setScale(2, RoundingMode.CEILING);
			localData.setAverageValue(averageValue);

			localsData.add(localData);
		}

		return localsData;
	}

}
