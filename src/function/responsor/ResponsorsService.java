package function.responsor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Responsor;
import function.callout.CalloutDao;

public class ResponsorsService {
		public List<Responsor> findResponsors(int eeID) throws SQLException{
			ResponsorsDao responsorsDao=new ResponsorsDao();
			List<Object[]> l=responsorsDao.findResponsorsByEEID(eeID);
			List<Responsor> responsors=new ArrayList();
			
			for (Object[] s: l){
				Responsor responsor = new Responsor();
				String UserID=(String)s[2];
				responsor.setUserID(UserID);
				responsor.setUserName((String)s[3]);
				CalloutDao calloutDao = new CalloutDao();
				System.out.println("tel"+calloutDao.findUserByUserID(UserID).getTel());
				responsor.setTel(calloutDao.findUserByUserID(UserID).getTel());
				responsors.add(responsor);
			}
			return responsors;
		}
}
