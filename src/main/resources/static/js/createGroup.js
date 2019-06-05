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
            , newGroupInfo:
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

            , createGroup(){

            }
        }
    });
};