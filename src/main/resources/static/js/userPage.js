window.onload = _ => {

    app = new Vue({
        el:"#appUserPage",
        data:{
            toGroupAPI: new ToGroup(),
            userInfo:{
                userId : "",
                userName : "",
                userEmail : ""
            },
            urlImage : "https://source.unsplash.com/category/nature/400x260",
            imgChange : false



        },

        created(){
            var currentUrl = window.location.pathname;
            this.ping();
            this.loadUser(currentUrl);
        },

        methods:{

            changeImageUser(){

                if(!this.imgChange)
                    this.urlImage = "https://source.unsplash.com/random/400x260";
                else
                    this.urlImage = "https://source.unsplash.com/category/nature/400x260";

                this.imgChange = !this.imgChange;


            }
            ,ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }

            ,createErrorHandler(methodName){
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);
                }
            }

            ,loadUser(currentUrl){
                piecesUrl = currentUrl.split("/");
                userId = piecesUrl[piecesUrl.length-1];

                console.log(userId)

                this.toGroupAPI
                    .getUserEndpoint()
                    .getUserInfo2(userId)
                    .then(resp => {
                        console.log("paginaUser");
                        console.log(JSON.stringify(resp));
                        this.userInfo.userId = resp.userId;
                        this.userInfo.userName = resp.userName;
                        this.userInfo.email = resp.email;
                    }).catch(this.createErrorHandler("userPage"));
            }

        }
    });

}