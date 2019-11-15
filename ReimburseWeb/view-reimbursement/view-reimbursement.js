let currentUser;

function newPokemonSubmit(event) {
    event.preventDefault(); // stop page from refreshing
    console.log('submitted');

    const reimbursement = getReimbursementsFromInputs();

    fetch('http://localhost:8080/ReimbursementApp/reimbursements', {
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
            addPokemonToTableSafe(data);
            console.log(data);
        })
        .catch(err => console.log(err));

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
    author.innerText = currentUser.ers_first_name;
    row.appendChild(author);

    const resolver = document.createElement('td');
    if (!reimburse.reimb_resolver) {
        resolver.innerText = "Still pending"
    } else {
        resolver.innerText = reimburse.reimb_resolver;
    }
    row.appendChild(resolver);

    const status = document.createElement('td');
    if (reimburse.reimb_status_id == 1) {
        status.innerText = "Pending"
    } else if (reimburse.reimb_status_id == 2) {
        status.innerText = "Accepted";
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
    if (reimburse.reimb_author == currentUser.ers_users_id) {
        document.getElementById('pokemon-table-body').appendChild(row);
    }
}


function getReimbursementsFromInputs() {
    //const reimId = document.getElementById('pokemon-name-input').value;
    const reimAmount = document.getElementById('pokemon-amount-input').value;
    const reimDescrip = document.getElementById('pokemon-hp-input').value;
    const reimType = document.getElementById('pokemon-level-input').value;

    let newType;
    if (reimType === 'Lodging') {
        newType = 1;
    } else if (reimType === 'Travel') {
        newType = 2;
    } else if (reimType === 'Food') {
        newType = 3;
    } else {
        newType = 4;
    }

    const reimburse = {
        reimb_author: currentUser.ers_users_id,
        reimb_amount: reimAmount,
        reimb_description: reimDescrip,
        reimb_type_id: newType
    }
    return reimburse;
}

function refreshTable() {
    fetch('http://localhost:8080/ReimbursementApp/reimbursements')
        .then(res => res.json())
        .then(data => {
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

getCurrentUserInfo();
