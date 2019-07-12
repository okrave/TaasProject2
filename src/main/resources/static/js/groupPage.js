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
                locationId: "",
                tags: [],
                members: []
            }
            ,groupInfo2: null
            ,listElement: 0
            ,listUser : []
            ,currentUrl : ""
        },

        created(){
            this.currentUrl = window.location.pathname;
            this.loadGroup();
            this.ping();
            this.getAllUser();
        },

        methods:{

            addGroupMember(userId,userName){
                console.log(userId,userName,this.groupInfo.groupId)
                groupMember = new MemberGroupPayload(userId,userName,this.groupInfo.groupId);
                this.toGroupAPI
                    .getGroupEndpoint()
                    .addGroupMember(groupMember)
                    .then(resp => {
                        console.log("paginaGruppo:D");
                        console.log(JSON.stringify(resp));
                        console.log(resp.members);
                        this.loadGroup();
                        //this.groupInfo.members = resp.members;

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

            ,loadGroup(){
                piecesUrl = this.currentUrl.split("/");
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
                        this.groupInfo.members = resp.members;
                        this.groupInfo.tags = resp.tags;
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