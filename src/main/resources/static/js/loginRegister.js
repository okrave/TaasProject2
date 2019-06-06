/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;

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

//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {

    $(document).ready(function(){
        $('#linkToLogin').click(switchFromRegistModalToLogin);
        $('#linkToRegistration').click(switchFromLoginModalToRegistration);
        $('#nav-linkRegister').click(function(){
            $('#registerModal').modal('show');
        });
        $('#nav-linkToLogin').click(function(){
            $('#loginModal').modal('show');
        });
    });

    app = new Vue({
        el: "#appLoginRegister",
        data: {
            toGroupAPI: new ToGroup()

            , userInfo: {
                username            : "",
                password            : "",
                email               : "",
                passwordConfirmation: ""
            }
            , messages: new NotificationsMessage()
        },
        created() {
            this.ping();
        },

        methods: {
            createErrorHandler(methodName){
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);
                    thisVue.messages.setErrorMessage(err);
                    thisVue.messages.clearMessagesAfter(5000)
                }
            }

            //API

            , ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }

            , login(){
                console.log("Loggin: " + this.userInfo);
                //this.toGroupAPI
                //    .getUserEndpoint()
            }

            , register(){
                let userInfo, thisVue;
                userInfo = new UserRegistration(this.userInfo.username, this.userInfo.password, this.userInfo.passwordConfirmation, this.userInfo.email);
                thisVue = this;
                this.toGroupAPI
                    .getUserEndpoint()
                    .register(userInfo)
                    .then(resp => {
                        console.log("registered :D");
                        console.log(resp);
                        if(resp){
                            thisVue.messages.setSuccessMessage("registration successful");
                            thisVue.messages.clearMessagesAfter(3000);
                        } else {
                            thisVue.messages.setErrorMessage("username yet present");
                            thisVue.messages.clearMessagesAfter(5000);
                        }
                    })
                    .catch(this.createErrorHandler("register"));
            }
        }
    });
};