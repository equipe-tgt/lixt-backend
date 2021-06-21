package br.com.ifsp.pi.lixt.data.business.category;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>, JpaSpecificationExecutor<Category> {

}
