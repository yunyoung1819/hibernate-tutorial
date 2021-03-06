package chap05.paging;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import util.DaoCommon;

public class PagingTest {

	@Before
	public void setUp() throws Exception {

	}
	
	DaoCommon<BoardArticle> daoBoard = new DaoCommon<BoardArticle>(BoardArticle.class);

	@Test
	public void testName() throws Exception {
		daoBoard.insert(new BoardArticle(1, "yunyoung1819", "hello Count", new Date()));
		assertEquals(1, daoBoard.count());
		
		daoBoard.deleteAllSetTable();
		assertEquals(0, daoBoard.count());
	}

	public void test() throws NumberFormatException, IOException, ParseException {
		DaoCommon<BoardArticle> daoBoard = new DaoCommon<BoardArticle>(BoardArticle.class);
		
		List<BoardArticle> list = FileReader_CVS.getArticles();
		for (BoardArticle boardArticle : list) {
			daoBoard.insert(boardArticle);
		}
		
		List<BoardArticle> pagingList1 = (List<BoardArticle>) daoBoard.getPagingList(1);
		System.out.println("첫번쨰 페이지");
		for (BoardArticle boardArticle : pagingList1) {
			System.out.println(boardArticle);
		}
		
		List<BoardArticle> pagingList2 = (List<BoardArticle>) daoBoard.getPagingList(2);
		System.out.println("두번쨰 페이지");
		for (BoardArticle boardArticle : pagingList2) {
			System.out.println(boardArticle);
		}
	}
}
