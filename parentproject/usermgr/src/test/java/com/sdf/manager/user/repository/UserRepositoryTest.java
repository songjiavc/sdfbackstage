/*package com.sdf.manager.user.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class UserRepositoryTest {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testGetScrollDataByJpql() {
		Pageable pageable = new PageRequest(0, 10);
		Object[] params = new Object[]{};
		QueryResult<User> qr = userRepository.getScrollDataByJpql(User.class, null, params, null, pageable);
		System.out.println(qr.getResultList().size());
		System.out.println(qr.getTotalRecord());
	}

}
*/