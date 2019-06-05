/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {
    app = new Vue({
        el: "#appListUser",
        data: {
            toGroupAPI: new ToGroup()
            , users: []
            , showPassword: false
            , errorMessage: null
        },
        created() {
            this.ping();
        },

        methods: {
            createErrorHandler(methodName) {
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);
                    thisVue.errorMessage = err;
                }
            }

            //API

            , ping() {
                this.toGroupAPI.ping().then(resp => console.log("pinged :D " + resp));
            }

            , reloadUserList(){
                this.toGroupAPI
                    .getUserEndpoint()
                    .getAllUsers()
                    .then(response => {
                        if( response.hasOwnProperty('status') && response.hasOwnProperty('error') && response.status != 200 ){
                            this.errorMessage = response.status + ": " + response.error;
                        }else {
                            console.log("users retrieved :D");
                            this.users = response;
                        }
                    })
                    .catch(this.createErrorHandler("reload users list"))
            }

            , deleteUser(userId){
                const thisVue = this;
                this.toGroupAPI
                    .getUserEndpoint()
                    .removeByID(userId)
                    .then(_ => {
                        console.log("user " + userId + " removed :D");
                        thisVue.errorMessage = "user " + userId + " removed :D";
                        setTimeout( ()=>{
                            //delete the error
                            thisVue.errorMessage = null;
                        }, 3000);
                        thisVue.reloadUserList();
                    })
                    .catch(this.createErrorHandler("remove user with id: " + userId))

            }
        }
    });
};