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
        mode: 'cors'
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
    reimid.innerText = 1;
    row.appendChild(reimid);

    const amount = document.createElement('td');
    amount.innerText = reimburse.amount;
    row.appendChild(amount);

    const time1 = document.createElement('td');
    time1.innerText = "Time";
    row.appendChild(time1);

    const time2 = document.createElement('td');
    time2.innerText = "Time";
    row.appendChild(time2);

    const descr = document.createElement('td');
    descr.innerText = reimburse.descrip;
    row.appendChild(descr);

    const receipt = document.createElement('td');
    receipt.innerText = "";
    row.appendChild(receipt);

    const author = document.createElement('td');
    author.innerText = reimburse.id;
    row.appendChild(author);

    const resolver = document.createElement('td');
    resolver.innerText = "";
    row.appendChild(resolver);

    const status = document.createElement('td');
    status.innerText = 2;
    row.appendChild(status);

    const typ = document.createElement('td');
    typ.innerText = reimburse.type;
    row.appendChild(typ);

    // append the row into the table
    document.getElementById('pokemon-table-body').appendChild(row);
}

function addPokemonToTable(pokemon) {
    document.getElementById('pokemon-table-body').innerHTML += `
    <tr>
        <td>1</td>
        <td>${reimburse.amount}</td>
        <td>"Time"</td>
        <td>"Time"</td>
        <td>${reimburse.descrip}</td>
        <td> </td>
        <td>${reimburse.id}</td>
        <td> </td>
        <td>2</td>
        <td>${reimburse.type}</td>
    </tr>
    `;
}


function getReimbursementsFromInputs() {
    const reimId = document.getElementById('pokemon-name-input').value;
    const reimAmount = document.getElementById('pokemon-amount-input').value;
    const reimDescrip = document.getElementById('pokemon-hp-input').value;
    const reimType = document.getElementById('pokemon-level-input').value;

    const reimburse = {
        id : reimId,
        amount: reimAmount,
        descrip: reimDescrip,
        type: reimType
    }
    return reimburse;
}

function refreshTable() {
    fetch('http://localhost:8080/PokemonApi/pokemon')
        .then(res => res.json())
        .then(data => {
            data.forEach(addPokemonToTableSafe)
        })
        .catch(console.log);
}

/*
function getCurrentUserInfo() {
    fetch('http://localhost:8080/PokemonApi/auth', {
        credentials: 'include'
    })
    .then(resp => resp.json())
    .then(data => {
        document.getElementById('ers_username').innerText = data.username
        refreshTable();
        currentUser = data;
    })
    .catch(err => {
        window.location = '/ReimbursementApp/login.html';
    })
}

getCurrentUserInfo();
*/