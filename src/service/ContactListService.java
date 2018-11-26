package service;

import java.sql.SQLException;

import dao.ContactListDao;
import domain.Contactlist;

public class ContactListService {
	public void ContactListAddOrUpdate(String UserID,String cName,String TelNo,String RelationShip) throws SQLException{
		ContactListDao contactListDao = new ContactListDao();
		if(contactListDao.contactList(UserID)!=null){
			contactListDao.updateUser(UserID, cName, TelNo, RelationShip);
		}else {
			contactListDao.insertContactList(UserID, cName, TelNo, RelationShip);
		}
	}
	
	public Contactlist sentMessage(String UserID){
		ContactListDao contactListDao = new ContactListDao();
		Contactlist contactlist = new Contactlist();
		if(contactListDao.contactList(UserID)!=null){
			 return contactListDao.contactList(UserID);
		}else{
			return null;
		}
	}
}
