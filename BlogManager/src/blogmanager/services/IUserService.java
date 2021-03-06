/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: An interface implementation class used by UserRepository.  
***********************************************************************************
*****************/
package blogmanager.services;
import blogmanager.model.*;
public interface IUserService extends IDbService<User> 
{
	public User findUserByCredentials(String username,String password) throws Exception;
	public User findUserByEmail(String email) throws Exception;
}
