
function removeAllModalBackdrop() {
    document.querySelectorAll(".modal-backdrop").forEach(e => {
        e.remove();
    e.parentNode.removeChild(e);
})
}

function switchFromRegistModalToLogin(){
    $('#registerModal').modal('hide');
    removeAllModalBackdrop();
    $('#loginModal').modal('show');
    return false;
}

function switchFromLoginModalToRegistration(){
    $('#loginModal').modal('hide');
    removeAllModalBackdrop();
    $('#registerModal').modal('show');
    return false;
}

function logoutCustom(){
    localStorage.removeItem('connectedUserName');
    localStorage.removeItem('connectedUserId');
}
