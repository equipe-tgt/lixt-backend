package br.com.ifsp.pi.lixt.data.business.purchaselocal;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.ifsp.pi.lixt.data.business.purchase.Purchase;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseRepository;
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

	public Map<String, List<PurchaseLocal>> findAllPurchaseLocalRecords(Long userId) {
		List<Purchase> userPurchases = this.purchaseRepository.findUserPurchases(userId);

		List<PurchaseLocal> purchaseLocals = userPurchases.stream()
				.map(e -> e.getPurchaseLocal())
				.collect(Collectors.toList());

		Map<String, List<PurchaseLocal>> groupedPurchaseLocals = purchaseLocals.stream()
				.collect(groupingBy(PurchaseLocal::getName));

		return groupedPurchaseLocals;
	}

}
