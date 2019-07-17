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

window.onload = function(){
    app2 = new Vue({
        el: "appNav",
        data: {
            userLogged:{
                isLogged: false,
                username: "",
                id:0
            }
        },

        mounted(){
            if(localStorage.getItem('connectedUserName')){
                this.userLogged.username = localStorage.getItem('connectedUserName');
                this.userLogged.id = localStorage.getItem('connectedUserId');
                this.userLogged.isLogged = true;
            }
        },




    });
}
