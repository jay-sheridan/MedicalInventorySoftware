// searching for a particular medicine.
function searchForMedicine(){
	console.log(document.getElementsByClassName("search")[0].value);
	let searchValue = document.getElementsByClassName("search")[0].value.toLowerCase();
	let table = document.getElementById("medicineTable");
	let rows = table.getElementsByClassName("medicineRows");
	for (let i = 0 ; i<rows.length; i++) {
		let medicine  =  rows[i].getElementsByTagName("h5")[0];
		if(medicine){
			let medicine_textValue = medicine.textContent || medicine.innerText;
			if(medicine_textValue.toLowerCase().indexOf(searchValue) > -1){
				rows[i].style.display = "";
			} else{
				rows[i].style.display="none";
			}
		}
	}
}

function populateForm(card) {
		    // Get data attributes from the clicked div (medicine card)
		    const name = card.getAttribute("data-name");
		    const qty = card.getAttribute("data-qty");
		    const mfgLicNo = card.getAttribute("data-mfgLicNo");
		    const batchNo = card.getAttribute("data-batchNo");
		    const mfgDate = card.getAttribute("data-mfgDate");
		    const expDate = card.getAttribute("data-expDate");
		    const price = card.getAttribute("data-price");

		    // Set values to the form fields
		    document.getElementById("form-search").value = name;
		    document.getElementById("form-qty").focus();
		    document.getElementById("form-qty").value = "";
		    document.getElementById("form-mfgLicNo").value = mfgLicNo;
		    document.getElementById("form-batchNo").value = batchNo;
		    document.getElementById("form-mfgDate").value = mfgDate;
		    document.getElementById("form-expDate").value = expDate;
		    document.getElementById("form-price").value = price;
		  }

// sorting medicines alphabetically
window.onload = function() {
            
            let rows = Array.from(document.querySelectorAll('.medicineRows'));
			rows.sort((a, b) => {
				let medicine_title1 = a.getElementsByClassName("medicine-title")[0];
				let medicine_title2 = b.getElementsByClassName("medicine-title")[0];
				return medicine_title1.textContent.localeCompare(medicine_title2.textContent);
			})

            let medicineTable = document.getElementById('medicineTable');
            medicineTable.innerHTML = '';
            rows.forEach(row => {
		
                medicineTable.appendChild(row);
            });
        };
		
//function populateForm(card) {
		    // Get data attributes from the clicked div (medicine card)
		    /*const name = card.getAttribute("data-name");
		    const qty = card.getAttribute("data-qty");
		    const mfgLicNo = card.getAttribute("data-mfgLicNo");
		    const batchNo = card.getAttribute("data-batchNo");
		    const mfgDate = card.getAttribute("data-mfgDate");
		    const expDate = card.getAttribute("data-expDate");
		    const price = card.getAttribute("data-price");

		    // Set values to the form fields
		    document.getElementById("form-search").value = name;
		    document.getElementById("form-qty").value = qty;
		    document.getElementById("form-mfgLicNo").value = mfgLicNo;
		    document.getElementById("form-batchNo").value = batchNo;
		    document.getElementById("form-mfgDate").value = mfgDate;
		    document.getElementById("form-expDate").value = expDate;
		    document.getElementById("form-price").value = price;
		  }*/