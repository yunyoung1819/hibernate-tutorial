package util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DaoCommon<T> {
	
	public static int numPerPage = 10;
	private SessionFactory factory;
	private Class clazz;
	private String boardName;
	
	public DaoCommon(Class<?> clazz) {
		factory = HibernateUtil.getSessionFactory();
		this.clazz = clazz;
		this.boardName = clazz.getSimpleName();
	}
	
	public List<?> getPagingList(int requestPage) {
		 Session session = factory.getCurrentSession();
		 Transaction tx = session.beginTransaction();
		 Query query = (Query) session.createQuery("from " + boardName + " order by id asc");
		 
		 // query.setFirstResult((요청페이지-1) * 페이지당글의개수);
		 query.setFirstResult((requestPage-1) * numPerPage);		// 0, 10
		 // query.setMaxResults(페이지당글의개수);
		 query.setMaxResults(numPerPage);	// 9, 19
		 
		 List<?> members = query.list();
		 tx.commit();
		 return members;
	}
	
	public List<?> selectList() {
		Session session = factory.getCurrentSession();
		session.beginTransaction(); 
		Query query = session.createQuery("from "+  boardName);
		
		List<?> list = query.list();
		session.getTransaction().commit();
		return list;
	}
	
	public void delete(T member) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(member);
		session.getTransaction().commit();
	}
	
	public void update(T selectedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(selectedMember);
		session.getTransaction().commit();
	}
	
	public T selectById(int id) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		T member = (T) session.get(clazz, id);
		session.getTransaction().commit();
		return member;
	}
	
	public void insert(T member) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(member);
		session.getTransaction().commit();
	}
}
