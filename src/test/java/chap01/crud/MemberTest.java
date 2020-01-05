package chap01.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import util.HibernateUtil;

public class MemberTest {

	private static final String HELLO_HIBERNETE = "hello hibernete";
	private static final String HELLO_WORLD = "hello world";
	private static final String YUNYOUNG1819 = "yunyoung1819";
	
	SessionFactory factory = HibernateUtil.getSessionFactory();
	
	@Test
	public void crudTest() {
		// Insert
		Member member = new Member(YUNYOUNG1819, HELLO_WORLD);
		Insert(member);
		
		// Select One
		Member selectedMember = selectedById(1);
		assertEquals(HELLO_WORLD, selectedMember.getMessage());
		
		// Update
		selectedMember.setMessage(HELLO_HIBERNETE);
		update(selectedMember);
		Member updatedMember = selectedById(1);
		assertEquals(HELLO_HIBERNETE, updatedMember.getMessage());
		
		// Delete
		delete(updatedMember);
		Member deletedMember = selectedById(1);
		assertNull(deletedMember);
	}

	public void delete(Member updatedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(updatedMember);
		session.getTransaction().commit();
	}

	public void update(Member selectedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(selectedMember);
		session.getTransaction().commit();
	}

	private Member selectedById(int id) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Member selectedMember = (Member) session.get(Member.class, id);
		session.getTransaction().commit();
		return selectedMember;
	}

	public void Insert(Member member) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(member);
		session.getTransaction().commit();
	}

}
