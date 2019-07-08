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
        },

        created(){
            var currentUrl = window.location.pathname;
            this.loadGroup(currentUrl);
            this.ping();
        },

        methods:{

            loadGroup(currentUrl){
                piecesUrl = currentUrl.split("/");
                groupId = piecesUrl[piecesUrl.length-1];
                console.log(groupId);
                groupInfo = this.toGroupAPI.group.getGroupInfo(groupId);
                console.log(groupInfo);

            }
            ,ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }

        }

    });
};