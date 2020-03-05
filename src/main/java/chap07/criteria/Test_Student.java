package chap07.criteria;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import util.DaoCommon;

public class Test_Student {
	
	SessionFactory factory = HibernateTestUtil.getSessionFactory(Student.class);
	DaoCommon<Student> daoStu = new DaoCommon<Student>(Student.class);
	
	@Before
	public void setUp() {
		daoStu.insert(new Student("윤영", 100, 100, 100, 100));
		daoStu.insert(new Student("아이린", 25, 90, 100, 100));
		daoStu.insert(new Student("이시하라 사토미", 24, 80, 80, 80));
		daoStu.insert(new Student("사나", 20, 95, 70, 70));
		daoStu.insert(new Student("쯔위", 20, 100, 100, 100));
		daoStu.insert(new Student("제니", 25, 90, 100, 100));
		daoStu.insert(new Student("수지", 100, 100, 100, 100));
	}

	@Test
	public void test() {
		assertEquals(7, daoStu.count());
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(Restrictions.gt("age", 22));
		criteria.add(Restrictions.le("math", 90));
		criteria.addOrder(Order.desc("id"));
		// 페이징
		criteria.setFirstResult(1);
		criteria.setMaxResults(2);
		
		List<Student> students = criteria.list();
		
//		for (Student student : students) {
//			System.out.println(student);
//		}
		
		students.forEach(n -> System.out.println(n));	// 람다식 
		session.getTransaction().commit();
	}

}
