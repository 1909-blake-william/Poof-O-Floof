package dao;

import model.AnimalBasic;
import model.AnimalFull;

public interface AnimalDao {

	/************************************************
	 * 					Save Logic					*
	 ***********************************************/
	/**
	 * Save full animal to DB? Currently confused with Hao's DB design. I cannot tell what we are keeping or removing.
	 * @param animalFull
	 * @return
	 */
	boolean saveFullAnimal(AnimalFull animalFull);
	
	/**
	 * save the information of a favorite picture
	 * @param animalId = the animalID (with this, when can query the DB and retrieve all information
	 * @param userId = the user that wanted to save 
	 * @return
	 */
	boolean saveFavoritePicture(int animalId, int userId);

	/************************************************
	 * 					Update Logic				*
	 ***********************************************/
	boolean updateDailyLikes(AnimalBasic animalBasic);
	
	boolean updateTotalLikes(AnimalBasic animalBasic);

	/************************************************
	 * 					Get Logic					*
	 ***********************************************/
}
