package chap04;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import chap01.crud.Member;
import util.DaoCommon;

public class BoardArticleTest {
	
	private static final String TEST = "yunyoung1819";
	
	DaoCommon<BoardArticle> daoBoard = new DaoCommon<BoardArticle>(BoardArticle.class);
	DaoCommon<Member> daoMember = new DaoCommon<Member>(Member.class);
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void test() {
		BoardArticle boardArticle = new BoardArticle(1, TEST, "hello", new Date());
		daoBoard.insert(boardArticle);
		
		BoardArticle getBoardArticle = daoBoard.selectById(1);
		assertEquals(TEST, getBoardArticle.getUserId());
	}
}
