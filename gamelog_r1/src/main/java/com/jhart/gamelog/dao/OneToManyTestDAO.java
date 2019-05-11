package com.jhart.gamelog.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.Cart;
import com.jhart.gamelog.entities.Items;
import com.jhart.gamelog.entities.person.EntityBioStat;
import com.jhart.gamelog.entities.person.EntityPerson;
import com.jhart.gamelog.entities.person.EntityPersonDetail;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.types.BattingType;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class OneToManyTestDAO extends GameLogDao{

//	 public Session getSession() throws NullSessionException{
//		 return null;
//	 }
	
	 public List<IModel> getAll() throws NullSessionException, DbExecutionException{
		 return null;
	 }
	 
	 public void saveEntityBioStat() {
		 System.out.println("Hello");
		 EntityPerson person = new EntityPerson();
		 person.setCode("2");
		 person.setFullName("Joe Blow22");
		 person.setBats(BattingType.L);
		 //EntityPersonDetail detail = new EntityPersonDetail();
		 //EntityBioStat bio = new EntityBioStat();
		 //bio.setBattingType(BattingType.U);
		 //bio.setPersonDetail(detail);
		 //detail.setBioStat(bio);
		 //person.setPersonDetail(detail);
		 //detail.setPerson(person);
		 
		 Session session = null;
		 Transaction tx;
		 try {
			 session = getSession();
			 tx = session.beginTransaction();
			 session.save(person);
			 tx.commit();
		 }
		 catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
		 session.close();
	 }
	 
	 public void saveCart() {
		 Cart cart = new Cart();
		 Items item1 = new Items(cart);
		 Items item2 = new Items(cart);
		 Set<Items> itemSet = new HashSet<Items>();
		 itemSet.add(item1);
		 itemSet.add(item2);
		 cart.setItems(itemSet);
		 
		 Transaction tx;
		 try {
			 Session session = getSession();
			 tx = session.beginTransaction();
			 session.save(cart);
			 session.save(item1);
			 session.save(item2);
			 tx.commit();
			 
			 System.out.println("Cart ID=" + cart.getId());
	         System.out.println("item1 ID=" + item1.getId() + ", Foreign Key Cart ID=" + item1.getCart().getId());
	         System.out.println("item2 ID=" + item2.getId() + ", Foreign Key Cart ID=" + item1.getCart().getId());
		 }
		 catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
	 }
}
