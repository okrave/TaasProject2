
function logoutCustom(){
    localStorage.removeItem('connectedUserName');
    localStorage.removeItem('connectedUserId');
}


function removeAllModalBackdrop() {
    document.querySelectorAll(".modal-backdrop").forEach(e => {
        e.remove();
    e.parentNode.removeChild(e);
})
}

function switchFromRegistModalToLogin(){
    console.log("Switch login");
    $('#registerModal').modal('hide');
    removeAllModalBackdrop();
    $('#loginModal').modal('show');
    return false;
}

function switchFromLoginModalToRegistration(){
    console.log("Switch registration");
    $('#loginModal').modal('hide');
    removeAllModalBackdrop();
    $('#registerModal').modal('show');
    return false;
}
