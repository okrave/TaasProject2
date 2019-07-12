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
            ,listElement: 0
            ,listUser : []
        },

        created(){
            var currentUrl = window.location.pathname;
            this.loadGroup(currentUrl);
            this.ping();
            this.getAllUser();
        },

        methods:{
            addGroupMember(userName){
                this.toGroupAPI
                    .getGroupEndpoint()
                    .addGroupMember(userName,this.groupInfo.groupId)
                    .then(resp => {
                        console.log("paginaGruppo:D");
                        console.log(JSON.stringify(resp));

                    })
            }

            ,setListElement(liNumber){
                this.listElement = liNumber;
                console.log(this.listElement)
            }

            ,createErrorHandler(methodName){
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);
                }
            }

            ,getAllUser(){
                this.toGroupAPI
                    .getUserEndpoint()
                    .getAllUsers()
                    .then(resp => {
                    console.log("tuttiGliUtenti:D");
                    console.log(JSON.stringify(resp));
                    this.listUser = resp;

            })
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
                        this.toGroupAPI.isLoaded = true;
                    })

            .catch(this.createErrorHandler("register"));

            }
            ,ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }

        }

    });
};