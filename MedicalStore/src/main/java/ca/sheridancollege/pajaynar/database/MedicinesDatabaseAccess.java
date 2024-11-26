package ca.sheridancollege.pajaynar.database;

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
    NamedParameterJdbcTemplate jdbc; // JDBC template for executing SQL queries with named parameters

    /**
     * Adds a new medicine to the database.
     *
     * @param medicine The medicine object containing details to insert.
     */
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

        String query = "INSERT INTO medicines(name, qty, mfg_lic_no, batch_no, exp_date, mfg_date, price) " +
                       "VALUES (:name, :qty, :mfg_lic_no, :batch_no, :exp_date, :mfg_date, :price)";
        System.out.println("********************************");
        System.out.println(jdbc.update(query, namedParameters));
    }

    /**
     * Updates the quantity of an existing medicine in the database by incrementing it.
     *
     * @param medicine The medicine object containing the updated details.
     */
    public void updateMedicineToDatabase(Medicine medicine) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", medicine.getName());
        namedParameters.addValue("batch_no", medicine.getBatchNo());
        namedParameters.addValue("qty", medicine.getQty());

        String query = "UPDATE medicines SET qty = qty + :qty WHERE name = :name AND batch_no = :batch_no";
        jdbc.update(query, namedParameters);
    }

    /**
     * Retrieves the list of all medicines from the database.
     *
     * @return A list of Medicine objects.
     */
    public List<Medicine> getMedicineList() {
        String query = "SELECT * FROM medicines";
        return jdbc.query(query, new MapSqlParameterSource(), new BeanPropertyRowMapper<>(Medicine.class));
    }

    /**
     * Retrieves a medicine by its name.
     *
     * @param name The name of the medicine.
     * @return The Medicine object, or null if no medicine is found.
     */
    public Medicine getMedicineByName(String name) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", name);

        String query = "SELECT * FROM medicines WHERE name = :name";
        try {
            return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(Medicine.class));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Deletes a medicine by its name.
     *
     * @param name The name of the medicine to delete.
     */
    public void deleteMedicineByName(String name) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", name);

        String query = "DELETE FROM medicines WHERE name = :name";
        jdbc.update(query, namedParameters);
    }

    /**
     * Updates the quantity of a medicine in the database to a specific value.
     *
     * @param medicine The medicine object containing the updated quantity and batch details.
     */
    public void updateMedicine(Medicine medicine) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("medicine_name", medicine.getName());
        namedParameters.addValue("batch_no", medicine.getBatchNo());
        namedParameters.addValue("remainingQty", this.getRemainingQty(medicine));

        String query = "UPDATE medicines SET qty = :remainingQty WHERE name = :medicine_name";
        jdbc.update(query, namedParameters);
    }

    /**
     * Calculates the remaining quantity of a medicine after processing a transaction.
     *
     * @param medicine The medicine object whose quantity is to be deducted.
     * @return The remaining quantity.
     */
    private int getRemainingQty(Medicine medicine) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("medicine_name", medicine.getName());

        String query = "SELECT qty FROM medicines WHERE name = :medicine_name";
        return jdbc.queryForObject(query, namedParameters, Integer.class) - medicine.getQty();
    }
}
