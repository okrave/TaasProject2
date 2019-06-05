/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {

    $(document).ready(function(){
        $('#linkLogin').click(function(){
            $('#registerModal').modal('hide');
            $('#loginModal').modal('show');
        });
        $('#linkRegistration').click(function(){
            $('#loginModal').modal('hide');
            $('#registerModal').modal('show');
        });
        $('#nav-linkRegister').click(function(){
            $('#loginModal').modal('show');
        });
        $('#nav-linkLogin').click(function(){
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
            , errorMessage: null
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
                    thisVue.errorMessage = err;
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
                        thisVue.userInfo.username = resp ? "registration successfull" : "username yet present";
                    })
                    .catch(this.createErrorHandler("register"));
            }
        }
    });
};