// searching for a particular medicine.
function searchForMedicine(){
	let searchValue = document.getElementById("search").value.toLowerCase();
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