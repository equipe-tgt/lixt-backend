package br.com.ifsp.pi.lixt.facade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import br.com.ifsp.pi.lixt.data.business.itemofpurchase.ItemOfPurchase;
import br.com.ifsp.pi.lixt.data.business.itemofpurchase.ItemOfPurchaseService;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.purchase.Purchase;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseService;
import br.com.ifsp.pi.lixt.data.business.purchaselist.PurchaseList;
import br.com.ifsp.pi.lixt.data.business.purchaselist.PurchaseListService;
import br.com.ifsp.pi.lixt.dto.PurchaseDto;
import br.com.ifsp.pi.lixt.mapper.PurchaseListMapper;
import br.com.ifsp.pi.lixt.mapper.PurchaseMapper;
import br.com.ifsp.pi.lixt.utils.security.Users;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseFacade {
	
	private final ListOfItemsService listOfItemsService;
	private final PurchaseService purchaseService;
	private final ItemOfPurchaseService itemOfPurchaseService;
	private final PurchaseListService purchaseListService;
	
	public Purchase findById(Long id) {
		return this.purchaseService.findById(id);
	}
		
	public Purchase save(PurchaseDto purchaseDto) {
		purchaseDto.setUserId(Users.getUserId());
		purchaseDto.setPurchaseDate(LocalDateTime.now());
		
		var result = createPurchase(purchaseDto);
		
		savePurchasesList(purchaseDto, result);
		saveItensOfPurchase(purchaseDto, result);
		
		return result;
	}
	
	public List<Purchase> findUserPurchases() {
		return this.purchaseService.findUserPurchases(Users.getUserId());
	}
	
	private Purchase createPurchase(PurchaseDto purchaseDto) {
		var purchase = PurchaseMapper.dtoToEntity(purchaseDto);
		purchase.setPurchaseLists(null);		
		return this.purchaseService.save(purchase);
	}
	
	private void savePurchasesList(PurchaseDto purchaseDto, Purchase purchase) {
		List<PurchaseList> purchaseLists = new ArrayList<>();

		for(var i=0; i<purchaseDto.getPurchaseLists().size(); i++) {
			var purchaseListTemp = PurchaseListMapper.dtoToEntity(purchaseDto.getPurchaseLists().get(i));
			purchaseListTemp.setPurchaseId(purchase.getId());
			purchaseListTemp.setItemsOfPurchase(null);
			purchaseListTemp.setNameList(this.listOfItemsService.findNameById(purchaseListTemp.getListId()));
			purchaseLists.add(purchaseListTemp);
		}
		
		purchase.setPurchaseLists(this.purchaseListService.saveAll(purchaseLists));
	}
	
	private void saveItensOfPurchase(PurchaseDto purchaseDto, Purchase purchase) {
		for(var i=0; i<purchaseDto.getPurchaseLists().size(); i++) {
			
			var purchaseList = PurchaseListMapper.dtoToEntity(purchaseDto.getPurchaseLists().get(i));
			List<ItemOfPurchase> itemsOfPurchase = new ArrayList<>();
			
			for(var j=0; j<purchaseList.getItemsOfPurchase().size(); j++) {
				var itemOfPurchaseTemp = purchaseList.getItemsOfPurchase().get(j);
				itemOfPurchaseTemp.setPurcharseListId(purchase.getPurchaseLists().get(i).getId());
				itemsOfPurchase.add(itemOfPurchaseTemp);
			}
			
			purchase.getPurchaseLists().get(i).setItemsOfPurchase(
					this.itemOfPurchaseService.saveAll(itemsOfPurchase)
			);
		}
	}
	
}
