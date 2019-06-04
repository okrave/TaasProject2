/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {
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
                this.restHome();
            }
            , restHome(){
                this.toGroupAPI.restHome().then(resp => console.log(resp));
            }

            , login(){
                console.log("Loggin: " + this.userInfo);
                //this.toGroupAPI
                //    .getUserEndpoint()
            }

            , register(){
                let userInfo;

                userInfo = new User(this.userInfo.username, this.userInfo.password, this.userInfo.email, true);
                this.toGroupAPI
                    .getUserEndpoint()
                    .register(userInfo)
                    .then(resp => {
                        console.log("registered :D");
                        console.log(resp);
                    })
                    .catch(this.createErrorHandler("register"));
            }
        }
    });
};