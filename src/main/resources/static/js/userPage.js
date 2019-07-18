window.onload = _ => {


    $(document).ready(function(){
        $('#linkToLogin').click(switchFromRegistModalToLogin);
        $('#linkToRegistration').click(switchFromLoginModalToRegistration);
        $('#nav-linkRegister').click(function(){
            $('#registerModal').modal('show');
        });
        $('#nav-linkToLogin').click(function(){
            $('#loginModal').modal('show');
        });
    });


    app = new Vue({
        el:"#appUserPage",
        data:{
            toGroupAPI: new ToGroup(),
            userInfo:{
                userId : "",
                userName : "",
                userEmail : "",
                description: "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dignissimos veniam numquam amet aspernatur aliquid, dicta laborum blanditiis alias et labore ratione tempora porro laboriosam iusto temporibus impedit obcaecati! Impedit, iusto."
            },
            urlImage : "https://source.unsplash.com/category/nature/400x260",
            imgChange : false,
            descriptionButton : "Cambia",
            descriptionRead: false
            ,userLogged:{
                isLogged: false,
                username: "",
                id: 0
            }



        },
        mounted(){
            if (localStorage.getItem('connectedUserName')) {
                this.userLogged.isLogged = true;
                this.userLogged.username = localStorage.getItem('connectedUserName');
                this.userLogged.id = localStorage.getItem('connectedUserId');
                console.log("In group page user loggato: "+ this.userLogged.username);
            }
        },

        created(){
            var currentUrl = window.location.pathname;
            this.ping();
            this.loadUser(currentUrl);
            this.removeLoader();
        },

        methods:{
            removeLoader(){
                document.getElementById('loaderCustom').style.visibility = 'hidden';
                document.getElementById('appUserPage').style.visibility = 'visible';
            },

            changeUserDescription(){
                if(this.descriptionRead == true) {
                    document.getElementById("descriptionInput").readOnly = false;
                    document.getElementById("descriptionInput").style.cssText = "box-shadow: 0 0 1px 0px black;"
                    this.descriptionButton = "Salva";
                }else{
                    document.getElementById("descriptionInput").readOnly = true;
                    document.getElementById("descriptionInput").style.cssText = "box-shadow: 0 0 0px 0px black;"
                    this.descriptionButton = "Cambia";
                }

                this.descriptionRead = !this.descriptionRead;
            }

            ,changeImageUser(){

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