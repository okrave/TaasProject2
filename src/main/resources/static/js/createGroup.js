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
            , errorMessage: null
            , tag: ""
            , newGroupInfo: new GroupNew()
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

            , addTag(){
                if(! this.newGroupInfo.tags.includes(this.tag)){
                    this.newGroupInfo.tags.push(this.tag);
                    this.tag = "";
                }
            }
            , removeTag(tag){
                var i, elem;
                var t = this.newGroupInfo.tags;
                var len = t.length;
                var newTag, t;
                if(this.newGroupInfo.tags.includes(tag)){
                    newTag = [];
                    i = -1;
                    while ( ++i < len) {
                        elem = t[i];
                        if(tag !== elem){
                            newTag.push(elem);
                        }
                    }
                    this.newGroupInfo.tags = newTag;
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