package app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class AuctionDao {

	@Autowired
	private AuctionRepo auctionRepo;

//
//	public void saveGazzettaCredentials(Credentials credentials) {
//		String username = "mene"; //TROVA LO USER DALLA SESSION
//		
//		User user = userRepo.findByUsername(username);
//		
//		user.setGazzettaPassword(credentials.getPassword());
//		user.setGazzettaUsername(credentials.getUsername());
//		
//		userRepo.save(user);
//		
//	}
//
//
//	public void createUser(UserBean userBean) {
//		User ent = new User();
//		ent.setFirstname(userBean.getFirstname());
//		ent.setLastname(userBean.getLastname());
//		ent.setEmail(userBean.getEmail());
//		ent.setUsername(userBean.getUsername());
//		ent.setPassword(userBean.getPassword());
//		Integer rnd = userBean.hashCode();
//		ent.setToBeConfirm(rnd);
//		
//		userRepo.save(ent);
//		
//	}
//	
//	
//	public Boolean confirmUser(ConfirmUser confirmUser) {
//		String username = confirmUser.getUsername();
//		
//		User user = userRepo.findByUsername(username);
//
//		if (user.getToBeConfirm().toString().equals(confirmUser.getRnd())) {
//			user.setToBeConfirm(0);
//			userRepo.save(user);
//			return true;
//		}
//		else {
//			return false;
//		}
//		
//	}
//
//
//	public UserBean login(Credentials credentials) {
//		User dbUser = userRepo.findByUsername(credentials.getUsername());
//		if (credentials.getPassword().equals(dbUser.getPassword())) {
//			UserBean userBean = createUserBean(dbUser);
//			return userBean;
//		}
//		return null;
//	}
//
//
//	private UserBean createUserBean(User ent) {
//		UserBean userBean = new UserBean();
//		userBean.setFirstname(ent.getFirstname());
//		userBean.setLastname(ent.getLastname());
//		userBean.setEmail(ent.getEmail());
//		userBean.setUsername(ent.getUsername());
//		userBean.setPassword(ent.getPassword());
//		return userBean;
//		
//	}
//	
//	

	
	
}
