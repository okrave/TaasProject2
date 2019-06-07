/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

class NewGroupProgression{
    constructor(){
        this.actualStep = 0;
        this.maxSteps = 4;
    }

    setStep(){

    }
}

//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {
    app = new Vue({
        el: "#appNewGroup",
        data: {
            toGroupAPI: new ToGroup()
            , messages: new NotificationsMessage()
            , tag: ""
            , newGroupInfo: new GroupNew()
            , allTags: [] //yet existing tags on database
        },
        created() {
            let thisVue = this;
            this.ping();
            setTimeout( ()=>{thisVue.fetchYetExistingTags();}, 1000);
        },

        methods: {
            createErrorHandler(methodName) {
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);
                    thisVue.messages.setErrorMessage(err);
                    thisVue.messages.clearMessagesAfter(5000);
                }
            }

            , addTag(){
                if( this.newGroupInfo.addTag(this.tag.trim())){
                    this.tag = "";
                }
            }

            , addTagFromExisting(tag){
                let prevTag = this.tag;
                this.tag = tag.trim();
                this.addTag();
                this.tag = prevTag;
            }

            , removeTag(tag){
                this.newGroupInfo.removeTag(tag);
            }

            //API

            , ping() {
                this.toGroupAPI.ping().then(resp => console.log("pinged :D " + resp));
            }

            , createGroup(){
                let thisVue = this;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .newGroup(this.newGroupInfo.format())
                    .then(response => {
                        console.log("group created successfully :D");
                        thisVue.setSuccessMessage("group created successfully :D");
                        thisVue.messages.clearMessagesAfter(3000);
                    })
                    .catch(this.createErrorHandler("create group"));
            }

            , fetchYetExistingTags(){
                let thisVue = this;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .listAllTags()
                    .then(response => {
                        thisVue.allTags = response;
                    })
                    .catch(this.createErrorHandler("list all tags"));
            }
        }
    });
};