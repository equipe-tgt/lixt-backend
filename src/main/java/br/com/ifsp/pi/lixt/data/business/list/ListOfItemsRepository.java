package br.com.ifsp.pi.lixt.data.business.list;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.ifsp.pi.lixt.data.business.listmembers.ListMembers;
import br.com.ifsp.pi.lixt.data.enumeration.StatusListMember;

public interface ListOfItemsRepository extends CrudRepository<ListOfItems, Long>, JpaSpecificationExecutor<ListOfItems> {

	@Query("select distinct l from ListOfItems l left join l.listMembers lm where l.ownerId = ?1 or (lm.userId = ?1 and lm.statusListMember = ?2)")
	List<ListOfItems> findUserLists(Long idUser, StatusListMember status);
	
	@Query("select l.ownerId from ListOfItems l where l.id = ?1")
	Long findOwnerIdByListId(Long listId);
	
	@Query("select distinct lm.userId from ListOfItems l join l.listMembers lm where l.id = ?1 and lm.statusListMember = ?2")
	List<Long> findMembersIdsByListId(Long listId, StatusListMember status);
	
	@Query("select distinct lm from ListOfItems l join l.listMembers lm where l.ownerId = ?1")
	List<ListMembers> findListMembersSentByUser(Long userId);
	
	@Query("select l.nameList from ListOfItems l where l.id = ?1")
	String findNameById(Long id);
	
	
	@Query("select distinct l.ownerId from ListOfItems l left join l.productsOfList p where p.id = ?1")
	Long findOwnerIdByProductOfListId(Long productOfListId);
	
	@Query("select distinct lm.userId from ListOfItems l join l.productsOfList p join l.listMembers lm where p.id = ?1 and lm.statusListMember = ?2")
	List<Long> findMembersIdsByProductOfListId(Long productOfListId, StatusListMember status);
	
	
	@Query("select distinct l.ownerId from ListOfItems l join l.listMembers lm where lm.id = ?1")
	Long findOwnerIdByListMemberId(Long listMembersId);
	
	
	@Query("select distinct l.ownerId from ListOfItems l join l.productsOfList p join p.comments c where c.id = ?1")
	Long findOwnerIdByCommentId(Long commentId);
	
	@Query("select distinct lm.userId from ListOfItems l join l.productsOfList p join p.comments c join l.listMembers lm where c.id = ?1 and lm.statusListMember = ?2")
	List<Long> findMembersIdsByCommentId(Long commentId, StatusListMember status);
	
}
