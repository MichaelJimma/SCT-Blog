/*********************************************************************************
* Project: GBC SCT Blog
* Assignment: 2
* Author(s): Michael Jimma, Ramzi Haddad, Daniel Dos Santos, Rany Akkad, Flex Chan
* Student Number: 100963147, 100885168, 100706404, 100821528, 100991675
* Date: 2016-11-30
* Description: A interface which is extended by all other data persistent interfaces.  
***********************************************************************************
*****************/
package blogmanager.services;

import java.util.List;

public interface IDbService<T> 
{
	public T find(int id) throws Exception;
	
	public List<T> list() throws Exception;
	
	public void add(T entity) throws Exception;
	
	public void update(T entity) throws Exception;
	
	public void close() throws Exception;
}
