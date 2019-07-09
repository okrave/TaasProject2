window.onload = _ =>{

    app = new Vue({
        el:"#appGroupPage",
        data: {
            toGroupAPI : new ToGroup()
            ,groupInfo:{
                groupId: "",
                groupName: "",
                groupDate: "",
                creator: "",
                description: "",
                locationId: ""
            }
            ,groupInfo2: null
        },

        created(){
            var currentUrl = window.location.pathname;
            this.loadGroup(currentUrl);
            this.ping();
        },

        methods:{
            createErrorHandler(methodName){
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);
                }
            }

            ,loadGroup(currentUrl){
                piecesUrl = currentUrl.split("/");
                groupId = piecesUrl[piecesUrl.length-1];
                console.log(groupId);
                this.toGroupAPI
                    .getGroupEndpoint()
                    .getGroupInfo(groupId)
                    .then(resp => {
                        console.log("paginaGruppo:D");
                        console.log(JSON.stringify(resp));
                        this.groupInfo.creator = resp.creator;
                        this.groupInfo.groupName = resp.groupName;
                        this.groupInfo.data = resp.groupDate;
                        this.groupInfo.description = resp.description;
                        this.groupInfo.groupId = resp.groupId;
                        this.groupInfo.locationId = resp.location;
                    })

            .catch(this.createErrorHandler("register"));

            }
            ,ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }

        }

    });
};