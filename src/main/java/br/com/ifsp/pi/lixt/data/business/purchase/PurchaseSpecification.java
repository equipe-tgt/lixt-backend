package br.com.ifsp.pi.lixt.data.business.purchase;

import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.utils.database.operations.Like;

@SuppressWarnings("serial")
public class PurchaseSpecification {
	
	public static final Specification<Purchase> byProductName(String productName) {
		return new Specification<Purchase>() {
			@Override
			public Predicate toPredicate(Root<Purchase> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if(Objects.isNull(productName))
					return null;
				return builder.like(root.get("purchaseLists").get("itemsOfPurchase").get("productName"), Like.contains(productName));
			}
		};
	}
	
	public static final Specification<Purchase> byPurchaseLocalId(Long purchaseLocalId) {
		return new Specification<Purchase>() {
			@Override
			public Predicate toPredicate(Root<Purchase> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if(Objects.isNull(purchaseLocalId))
					return null;
				return builder.equal(root.get("purchaseLocalId"), purchaseLocalId);
			}
		};
	}
	
	public static final Specification<Purchase> byMeasureType(MeasureType measureType) {
		return new Specification<Purchase>() {
			@Override
			public Predicate toPredicate(Root<Purchase> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if(Objects.isNull(measureType))
					return null;
				return builder.equal(root.get("purchaseLists").get("itemsOfPurchase").get("measureType"), measureType);
			}
		};
	}

}
