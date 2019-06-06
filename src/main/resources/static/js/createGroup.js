/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {
    app = new Vue({
        el: "#appNewGroup",
        data: {
            toGroupAPI: new ToGroup()
            , newGroupInfo: new GroupSearch()
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
                this.toGroupAPI
                    .getGroupEndpoint()
                    .newGroup(this.newGroupInfo)
                    .then(response => {
                        console.log("group created successfully :D");
                    })
                    .catch(createErrorHandler("create group"));
            }
        }
    });
};