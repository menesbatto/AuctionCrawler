package app.logic._0_credentialsSaver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.UserDao;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.UserBean;

@Service
public class UserExpert {
		
	@Autowired
	private UserBean userBean;
	
	@Autowired
	private UserDao userDao;

	public void saveGazzettaCredentials(Credentials credentials){
		
		userDao.saveGazzettaCredentials(credentials);
	}

	public void createUser(UserBean userBean){
		
		userDao.createUser(userBean);
	}
	
	public boolean confirmUser(ConfirmUser confirmUser){
		
		Boolean confirmed = userDao.confirmUser(confirmUser);
		return confirmed;
	}
	
	public void downlaodLeagues(){
//		UserBean userBean = new 
//		
//		return confirmed;
	}

	public Boolean login(Credentials credentials) {
		UserBean dbUserBean = userDao.login(credentials);
		userBean.setEmail(dbUserBean.getEmail());
		userBean.setUsername(dbUserBean.getUsername());
		userBean.setFirstname(dbUserBean.getFirstname());
		
		if (dbUserBean == null)
			return false;
		return true;
	}
	
	
}
