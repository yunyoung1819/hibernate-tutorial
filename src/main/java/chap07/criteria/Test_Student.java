package chap07.criteria;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import util.DaoCommon;

public class Test_Student {
	
	SessionFactory factory = HibernateTestUtil.getSessionFactory(Student.class);
	DaoCommon<Student> daoStu = new DaoCommon<Student>(Student.class);
	
	@Before
	public void setUp() {
		daoStu.deleteAllSetTable();
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

	// Restriction 추가
	@Test
	public void restriction() throws Exception {
		assertEquals(7, daoStu.count());
		Session session = factory.getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(Restrictions.or(
				Restrictions.between("age", 21, 25), Restrictions.between("kor", 0, 90)));

		List<Student> list = criteria.list();
		System.out.println("나이는 21세에서 25사이이거나, 국어점수가 0점에서 90점 사이");
		list.forEach(n -> System.out.println(n));

		session.getTransaction().commit();
	}

	// Projection
	@Test
	public void projection() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Student.class);
		double avg = (double) criteria.setProjection(Projections.avg("kor")).uniqueResult();
		System.out.println("국어 점수 평균 : " + avg);

		session.getTransaction().commit();
	}

	// NamedQuery
	@Test
	public void namedQuery() throws Exception {
	    Session session = factory.getCurrentSession();
	    session.beginTransaction();

	    Query query = session.getNamedQuery("FindByAge");
	    query.setInteger(0, 25);
	    List<Student> list = query.list();
        System.out.println("나이가 25살 이상인 학생");
        list.forEach(n -> System.out.println(n));
        session.getTransaction().commit();
	}

	// Native Query
	@Test
	public void nativeQuery() throws Exception {

	}

}
