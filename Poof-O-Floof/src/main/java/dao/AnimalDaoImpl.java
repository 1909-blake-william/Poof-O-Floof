package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.AnimalBasic;
import util.ConnectionUtil;
import util.Exceptions;

public class AnimalDaoImpl implements AnimalDao {

	private static final AnimalDao instance = new AnimalDaoImpl();
	
	private AnimalDaoImpl() {}
	
	public static AnimalDao getInstance() {
		return instance;
	}
	
	public static final String SAVE_ANIMAL = "INSERT INTO animal (animal_id, animal_type, species, age, gender, animal_size) " + 
			"VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String SAVE_FAVORITE = "INSERT INTO favorite (favorite_id, user_id, animal_id) " +
		    "VALUES (favorite_id_seq.nextval, ?, ?)";

	@Override
	public boolean saveBasicAnimal(AnimalBasic animalBasic) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = SAVE_ANIMAL;
			
			int stIndex = 0;
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(++stIndex, animalBasic.getId());
			ps.setString(++stIndex, animalBasic.getType());
			ps.setString(++stIndex, animalBasic.getSpecies());
			ps.setString(++stIndex, animalBasic.getAge());
			ps.setString(++stIndex, animalBasic.getGender());
			ps.setString(++stIndex, animalBasic.getSize());
			
			return ps.executeUpdate() == 1;
			
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}
	
	@Override
	public boolean saveFavoritePicture(int animalId, int userId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(SAVE_FAVORITE);
			
			int stIndex = 0;
			ps.setInt(++stIndex, userId);
			ps.setInt(++stIndex, animalId);
			
			return ps.executeUpdate() == 1;
			
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}

	@Override
	public boolean updateDailyLikes(AnimalBasic animalBasic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTotalLikes(AnimalBasic animalBasic) {
		// TODO Auto-generated method stub
		return false;
	}



}
