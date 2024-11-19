package ca.sheridancollege.pajaynar.database;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.pajaynar.beans.Medicine;

@Repository
public class MedicinesDatabaseAccess {
	
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	public void addMedicineToDatabase(Medicine medicine) {
		System.out.println("Inside addMedicine");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", medicine.getName());
		namedParameters.addValue("qty", medicine.getQty());
		namedParameters.addValue("mfg_lic_no", medicine.getMfgLicNo());
		namedParameters.addValue("batch_no", medicine.getBatchNo());
		namedParameters.addValue("mfg_date", medicine.getMfgDate());
		namedParameters.addValue("exp_date", medicine.getExpDate());
		namedParameters.addValue("price", medicine.getPrice());
		
		String query = "INSERT INTO medicines(name,qty,mfg_lic_no,batch_no,exp_date,mfg_date,price) VALUES (:name,:qty,:mfg_lic_no,:batch_no,:exp_date,:mfg_date,:price)";
		System.out.println("********************************");
		System.out.println(jdbc.update(query, namedParameters));
	}
	
	public void updateMedicineToDatabase(Medicine medicine) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", medicine.getName());
		namedParameters.addValue("batch_no", medicine.getBatchNo());
		namedParameters.addValue("qty", medicine.getQty());
		System.out.println(medicine.getQty());
		String query = "UPDATE medicines SET qty = qty + :qty WHERE name = :name AND batch_no = :batch_no";
		jdbc.update(query, namedParameters);
	}
	
	public List<Medicine> getMedicineList(){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query="SELECT * FROM medicines";
		return (jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Medicine>(Medicine.class)));
	}
	
	public Medicine getMedicineByName(String name){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", name);
		String query = "SELECT * FROM medicines WHERE name = :name";
		try{return (jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Medicine>(Medicine.class)));}
		catch(Exception e) {return null;}
	}
	
	public void deleteMedicineByName(String name) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", name);
        String query = "DELETE FROM medicines WHERE name = :name";
        jdbc.update(query, namedParameters);
    };
	
	public void updateMedicine(Medicine medicine) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("medicine_name", medicine.getName());
		namedParameters.addValue("batch_no", medicine.getBatchNo());
		namedParameters.addValue("remainingQty", this.getRemainingQty(medicine));
		
		String query = "UPDATE medicines SET qty = :remainingQty WHERE name = :medicine_name";
		jdbc.update(query, namedParameters);
	}
	
	private int getRemainingQty(Medicine medicine) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("medicine_name", medicine.getName());
		String query = "SELECT qty FROM medicines WHERE name = :medicine_name";
		return (jdbc.queryForObject(query, namedParameters, Integer.class) - medicine.getQty());
	}

}