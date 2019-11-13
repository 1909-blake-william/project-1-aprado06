function login(event) {
    event.preventDefault();

    const ers_username = document.getElementById('inputUsername').value;
    const ers_password = document.getElementById('inputPassword').value;
    const credential = {
        ers_username,
        ers_password
    };

    fetch('http://localhost:8080/ReimbursementApp/auth', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        mode: 'cors',
        credentials: 'include', // put credentials: 'include' on every request to use session info
        body: JSON.stringify(credential)
    })
    .then(resp => {
        if(resp.status === 201) {
            // redirect
            console.log('navigate to reimbursement')
            window.location = '/view-reimbursement/view-reimbursement.html';
        } else {
            document.getElementById('error-message').innerText = 'Failed to login';
        }
    })
    .catch(err =>{
        console.log(credential);
        console.log(err);
    })


}