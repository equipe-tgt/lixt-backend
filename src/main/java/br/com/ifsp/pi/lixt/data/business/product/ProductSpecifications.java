package br.com.ifsp.pi.lixt.data.business.product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.ifsp.pi.lixt.utils.database.operations.Like;
import br.com.ifsp.pi.lixt.utils.security.Users;

@SuppressWarnings("serial")
public class ProductSpecifications {
	
	public static Specification<Product> byBarcode(String barcode) {
		return (root, query, builder) -> {
			if(StringUtils.isNumeric(barcode))
				return builder.like(root.get("barcode"), Like.startsWith(barcode));
			return null;
		};
	}
	
	public static Specification<Product> byName(String name) {
		return new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.get("name"), Like.contains(name));
			}
		};
	}
	
	public static Specification<Product> byUser() {
		return new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.or(builder.equal(root.get("userId"), Users.getUserId()), builder.isNull(root.get("userId")));
			}
		};
	}

}
