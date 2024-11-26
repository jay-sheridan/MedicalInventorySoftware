package ca.sheridancollege.pajaynar.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.pajaynar.beans.Invoice;
import ca.sheridancollege.pajaynar.beans.Medicine;

@Repository
public class InvoicesDatabaseAccess {

    @Autowired
    NamedParameterJdbcTemplate jdbc; // JDBC template for executing SQL queries with named parameters

    /**
     * Generates a new invoice and saves it along with the associated medicines.
     *
     * @param invoice The invoice object containing details to insert.
     */
    public void generateInvoice(Invoice invoice) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("invoice_created_date", invoice.getInvoiceCreatedDate());
        namedParameters.addValue("invoice_created_time", invoice.getRoundedInvoiceCreatedTime());
        namedParameters.addValue("customer_phone", invoice.getCustomerPhone());
        namedParameters.addValue("total_amount", invoice.getTotalAmount());

        // Insert the invoice into the invoices table
        String query = "INSERT INTO invoices(invoice_created_date, invoice_created_time, customer_phone, total_amount) " +
                       "VALUES(:invoice_created_date, :invoice_created_time, :customer_phone, :total_amount);";
        jdbc.update(query, namedParameters);

        // Fetch the newly generated invoice ID
        int invoiceId = getInvoiceId(namedParameters);
        namedParameters.addValue("invoice_id", invoiceId);
        System.out.println("Generated Invoice ID: " + namedParameters.getValue("invoice_id"));

        // Insert each medicine from the processedMedicineList into invoice_medicines table
        for (Medicine medicine : invoice.getProcessedMedicineList()) {
            namedParameters.addValue("name", medicine.getName());
            namedParameters.addValue("batch_no", medicine.getBatchNo());
            namedParameters.addValue("qty", medicine.getQty());
            namedParameters.addValue("price", medicine.getPrice() / medicine.getQty());

            query = "INSERT INTO invoice_medicines(invoice_id, name, batch_no, qty, price) " +
                    "VALUES(:invoice_id, :name, :batch_no, :qty, :price)";
            jdbc.update(query, namedParameters);
        }
    }

    /**
     * Fetches the ID of an invoice based on its creation details.
     *
     * @param namedParameters Parameters used to identify the invoice.
     * @return The ID of the invoice.
     */
    private int getInvoiceId(MapSqlParameterSource namedParameters) {
        System.out.println("Fetching invoice ID...");
        String query = "SELECT invoice_id FROM invoices WHERE invoice_created_date = :invoice_created_date " +
                       "AND invoice_created_time = :invoice_created_time AND customer_phone = :customer_phone";
        return jdbc.queryForObject(query, namedParameters, Integer.class);
    }

    /**
     * Fetches a specific invoice by its ID, including its associated medicines.
     *
     * @param id The ID of the invoice.
     * @return An Invoice object containing the details of the invoice.
     */
    public Invoice getInvoiceById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("invoice_id", id);

        String query = "SELECT * FROM invoices WHERE invoice_id = :invoice_id";
        Invoice invoice = jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(Invoice.class));

        // Populate the medicine list for the invoice
        invoice.setMedicineList(getMedicineListByInvoiceId(id.intValue()));
        return invoice;
    }

    /**
     * Retrieves the list of medicines associated with a specific invoice.
     *
     * @param invoiceId The ID of the invoice.
     * @return A list of Medicine objects.
     */
    public List<Medicine> getMedicineListByInvoiceId(int invoiceId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("invoice_id", invoiceId);

        String query = "SELECT name, batch_no, qty, price FROM invoice_medicines WHERE invoice_id = :invoice_id";
        List<Medicine> medicineList = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Medicine.class));

        // Log details of the medicines retrieved
        for (Medicine medicine : medicineList) {
            System.out.println("Medicine Name: " + medicine.getName());
            System.out.println("Batch No: " + medicine.getBatchNo());
            System.out.println("Quantity: " + medicine.getQty());
            System.out.println("Price: " + medicine.getPrice());
        }
        return medicineList;
    }

    /**
     * Fetches the list of all invoices from the database.
     *
     * @return A list of Invoice objects.
     */
    public List<Invoice> getInvoiceList() {
        String query = "SELECT invoice_id, invoice_created_date, invoice_created_time, customer_phone, total_amount FROM invoices";
        return jdbc.query(query, new MapSqlParameterSource(), new BeanPropertyRowMapper<>(Invoice.class));
    }

    /**
     * Fetches invoices based on a customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return A list of Invoice objects associated with the phone number.
     */
    public List<Invoice> getInvoiceListByPhone(Long phoneNumber) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("customer_phone", phoneNumber);

        String query = "SELECT * FROM invoices WHERE customer_phone = :customer_phone";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Invoice.class));
    }
}
