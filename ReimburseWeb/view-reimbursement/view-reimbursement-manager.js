let currentUser;

function newPokemonSubmit(event) {
    event.preventDefault(); // stop page from refreshing
    console.log('submitted');

    const reimbursement = getReimbursementsFromInputs();

    fetch('http://localhost:8080/ReimbursementApp/reimbursements/update', {
        method: 'POST',
        body: JSON.stringify(reimbursement),
        headers: {
            'content-type': 'application/json'
        },
        mode: 'cors',
        credentials: 'include'
    })
        .then(res => res.json())
        .then(data => {
            //addPokemonToTableSafe(data);
            refreshTable();
            console.log(data);
        })
        .catch(err => console.log(err));

}

function newPokemonSubmit2(event) {
    event.preventDefault(); // stop page from refreshing
    console.log('submitted');

    const reimbursement = getReimbursementsFromInputs2();
    console.log(reimbursement);
    if (reimbursement.reimb_id != "" && reimbursement.reimb_status_id == undefined) {
        console.log(reimbursement.reimb_id );
        fetch(`http://localhost:8080/ReimbursementApp/reimbursements/user/${reimbursement.reimb_id}/`)
        .then(res => res.json())
        .then(data => {
            let rows = document.getElementById('pokemon-table-body').rows.length
            for (let i = rows - 1; i >= 0; i--) {
                document.getElementById('pokemon-table-body').deleteRow(i);
            }
            data.forEach(addPokemonToTableSafe)
        })
        .catch(console.log);
    } else if (reimbursement.reimb_id == "" && reimbursement.reimb_status_id != null) {
        console.log(reimbursement.reimb_status_id);
        fetch(`http://localhost:8080/ReimbursementApp/reimbursements/status/${reimbursement.reimb_status_id}`)
        .then(res => res.json())
        .then(data => {
            let rows = document.getElementById('pokemon-table-body').rows.length
            for (let i = rows - 1; i >= 0; i--) {
                document.getElementById('pokemon-table-body').deleteRow(i);
            }
            data.forEach(addPokemonToTableSafe)
        })
        .catch(console.log);
    } else if (reimbursement.reimb_id != "" && reimbursement.reimb_status_id != "") {
        fetch(`http://localhost:8080/ReimbursementApp/reimbursements/user-status/${reimbursement.reimb_id}/${reimbursement.reimb_status_id}/`)
            .then(res => res.json())
            .then(data => {
                let rows = document.getElementById('pokemon-table-body').rows.length
                for (let i = rows - 1; i >= 0; i--) {
                    document.getElementById('pokemon-table-body').deleteRow(i);
                }
                data.forEach(addPokemonToTableSafe)
            })
            .catch(console.log);
    } else {
        fetch('http://localhost:8080/ReimbursementApp/reimbursements')
            .then(res => res.json())
            .then(data => {
                let rows = document.getElementById('pokemon-table-body').rows.length
                for (let i = rows - 1; i >= 0; i--) {
                    document.getElementById('pokemon-table-body').deleteRow(i);
                }
                data.forEach(addPokemonToTableSafe)
            })
            .catch(console.log);
    }
}

function addPokemonToTableSafe(reimburse) {

    // create the row element
    const row = document.createElement('tr');

    // create all the td elements and append them to the row
    const reimid = document.createElement('td');
    reimid.innerText = reimburse.reimb_id;
    row.appendChild(reimid);

    const amount = document.createElement('td');
    amount.innerText = reimburse.reimb_amount;
    row.appendChild(amount);

    const time1 = document.createElement('td');
    time1.innerText = new Date(reimburse.reimb_submitted);
    row.appendChild(time1);

    const time2 = document.createElement('td');
    if (!reimburse.reimb_resolved) {
        time2.innerText = "Still pending"
    } else {
        time2.innerText = new Date(reimburse.reimb_resolved);
    }
    row.appendChild(time2);

    const descr = document.createElement('td');
    descr.innerText = reimburse.reimb_description;
    row.appendChild(descr);

    const receipt = document.createElement('td');
    receipt.innerText = "None";
    row.appendChild(receipt);

    const author = document.createElement('td');
    author.innerText = reimburse.reimb_author_name;
    row.appendChild(author);

    const resolver = document.createElement('td');
    if (!reimburse.reimb_resolver) {
        resolver.innerText = "Still pending"
    } else {
        resolver.innerText = reimburse.reimb_resolver_name;
    }
    row.appendChild(resolver);

    const status = document.createElement('td');
    if (reimburse.reimb_status_id == 1) {
        status.innerText = "Pending"
    } else if (reimburse.reimb_status_id == 2) {
        status.innerText = "Approved";
    } else if (reimburse.reimb_status_id == 3) {
        status.innerText = "Denied";
    }
    row.appendChild(status);

    const typ = document.createElement('td');
    if (reimburse.reimb_type_id == 1) {
        typ.innerText = "Lodging"
    } else if (reimburse.reimb_type_id == 2) {
        typ.innerText = "Travel";
    } else if (reimburse.reimb_type_id == 3) {
        typ.innerText = "Food";
    } else {
        typ.innerText = "Other";
    }
    row.appendChild(typ);

    // append the row into the table
    if (reimburse.reimb_author != currentUser.ers_users_id) {
        document.getElementById('pokemon-table-body').appendChild(row);
    }
}


function getReimbursementsFromInputs() {
    const reimId = document.getElementById('pokemon-amount-input').value;
    const reimStatus = document.getElementById('pokemon-hp-input').value;
    let status;
    if (reimStatus === 'Pending') {
        status = 1;
    } else if (reimStatus === 'Approved') {
        status = 2;
    } else if (reimStatus === 'Denied') {
        status = 3;
    }
    const reimburse = {
        reimb_resolver: currentUser.ers_users_id,
        reimb_id: reimId,
        reimb_status_id: status
    }
    return reimburse;
}

function getReimbursementsFromInputs2() {
    const reimId = document.getElementById('pokemon-amount-input2').value;
    const reimStatus = document.getElementById('pokemon-hp-input2').value;
    let status;
    if (reimStatus === 'Pending') {
        status = 1;
    } else if (reimStatus === 'Approved') {
        status = 2;
    } else if (reimStatus === 'Denied') {
        status = 3;
    }
    const reimburse = {
        reimb_resolver: currentUser.ers_users_id,
        reimb_id: reimId,
        reimb_status_id: status
    }
    return reimburse;
}

function refreshTable() {
    fetch('http://localhost:8080/ReimbursementApp/reimbursements')
        .then(res => res.json())
        .then(data => {
            let rows = document.getElementById('pokemon-table-body').rows.length
            for (let i = rows - 1; i >= 0; i--) {
                document.getElementById('pokemon-table-body').deleteRow(i);
            }
            data.forEach(addPokemonToTableSafe)
        })
        .catch(console.log);
}


function getCurrentUserInfo() {
    fetch('http://localhost:8080/ReimbursementApp/auth/session-user', {
        credentials: 'include'
    })
        .then(resp => resp.json())
        .then(data => {
            document.getElementById('users-name').innerText = data.ers_username
            refreshTable();
            currentUser = data;
        })
        .catch(err => {
            window.location = '/ReimbursementApp/login.html';
        })
}

function logout(event) {
    fetch('http://localhost:8080/ReimbursementApp/auth/logout', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        mode: 'cors',
        //credentials: 'include', // put credentials: 'include' on every request to use session info
    })
        .then(resp => {
            getCurrentUserInfo();
            window.location = '/ReimbursementApp/login.html';
        })
}


getCurrentUserInfo();