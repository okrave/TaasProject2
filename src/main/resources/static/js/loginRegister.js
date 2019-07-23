
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {

    $(document).ready(attachClicksToModalButtonsLoginRegister);

    app = new Vue({
        el: "#appLoginRegister",
        data: {
            toGroupAPI: new ToGroup()

            , userInfo: {
                username            : "",
                password            : "",
                email               : "",
                passwordConfirmation: ""
            }
            , userLogged: new UserLogged()
            , messages: new NotificationsMessage()

            , groupsCarousel: []
            , groupEachSection: 4
},

        mounted(){
            this.userLogged.reloadUserInfo();

        },
        created() {
            this.ping();
            this.fetchGroupSimple();
            user1 = "luca";
            window.userNameHeader = "Luca";
            console.log("window.userName: " + window.userNameHeader);
            //this.getFacebookUserInfo();
            this.removeLoader();
        },

        computed: {
            isLogged(){
                return (this.userLogged != null && this.userLogged  !== undefined) ? this.userLogged.isLogged : false;
            },

            amountCarouselSections(){
                if(this.groupsCarousel == null ){
                    return 0;
                }
                return ((this.groupsCarousel.length % this.groupEachSection) == 0) ? Math.trunc(this.groupsCarousel.length / this.groupEachSection)
                    : (Math.trunc(this.groupsCarousel.length / this.groupEachSection) + 1);
            },

            groupToGroupsCarousel() {
                var ret, groupSize, i, gsIndex, gC; //  sectionAmount,
                gC = this.groupsCarousel;
                if(gC == null) return [];
                ret = [];
                groupSize = gC.length;

                //sectionAmount = Math.floor(groupSize / 3);
                i = 0;
                while( i < groupSize ) {
                    var section = [];
                    gsIndex = -1;
                    while( ++gsIndex < this.groupEachSection && i < groupSize) {
                        section.push(gC[i]);
                        i++;
                    }
                    ret.push(section);
                    section = null;
                }
                return ret;
            }
            , groupToGroupsCarouselInactive(){
                var ret, gtgc, i, len;
                ret = [];
                gtgc = this.groupToGroupsCarousel;
                len = gtgc.length;
                if(len == 0) {
                    return ret;
                }
                i = 0;
                while( ++i < len){
                    ret.push(gtgc[i]);
                }
                return ret;
            }
        },

        methods: {
            getFacebookUserInfo(){
                this.toGroupAPI
                    .getUserEndpoint()
                    .getFacebookUserInfo()
                    .then(resp => {
                        console.log("dentro getFacebookUserInfo");
                        console.log(resp.userAuthentication.details.name);
                        this.setLoggedUsername(resp.userAuthentication.details.name);
            }).catch(this.createErrorHandler("getFacebookUserInfo"));

            },
            removeLoader(){
                document.getElementById('loaderCustom').style.visibility = 'hidden';
                document.getElementById('appLoginRegister').style.visibility = 'visible';

            },
            setLoggerUser(resp){
                localStorage.setItem('connectedUserName',resp.userName);
                localStorage.setItem('connectedUserId',resp.userId);
                this.userLogged.reloadUserInfo();
                console.log(JSON.stringify(this.userLogged));
            },

            setLoggedFacebook(username){
                console.log("Dentro setLogged: "+ username);
                localStorage.setItem('connectedUserName',username);

                //Registrazione facebook
                this.userInfo.username = username+"";
                this.userInfo.password = "";
                this.userInfo.email = username + "@gmail.com";
                this.userInfo.passwordConfirmation = "";
                this.register();
            }

            ,customLogin(){
                console.log(this.userInfo.username);
                console.log(this.userInfo.password);
                //userInfo = new UserRegistration(this.userInfo.username, this.userInfo.password, this.userInfo.password, this.userInfo.username);

                this.toGroupAPI
                    .getUserEndpoint()
                    .login(//userInfo
                        {
                            "email": this.userInfo.username,
                            "password": this.userInfo.password
                        })
                    .then(resp => {
                        this.setLoggerUser(resp);
                    }).catch(this.createErrorHandler("register"));
            }

            , loadGroupPage(groupId){
                /*
                TODO 20/07/2019 by Marco
                testare quale delle due soluzioni (oppure altre) funziona per redirezionare alla Home
                */
                window.location = '/group_page/' + groupId;
                //this.$router.push('/group_page/' + groupId);
            }

            ,createErrorHandler(methodName){
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);

                    thisVue.messages.setErrorMessage(err);
                    thisVue.messages.clearMessagesAfter(5000)

                }
            }


            , ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }



            , register(){
                let userInfo, thisVue;
                userInfo = new UserRegistration(this.userInfo.username, this.userInfo.password, this.userInfo.passwordConfirmation, this.userInfo.email);
                thisVue = this;
                this.toGroupAPI
                    .getUserEndpoint()
                    .register(userInfo)
                    .then(resp => {
                    console.log("registered :D");
                    console.log(resp);
                    if(resp){
                        thisVue.messages.setSuccessMessage("registration successful");
                        thisVue.messages.clearMessagesAfter(3000);
                    } else {
                        thisVue.messages.setErrorMessage("username yet present");
                        thisVue.messages.clearMessagesAfter(5000);
                    }
                    this.customLogin();
                })
                .catch(this.createErrorHandler("register"));
            }

            , logout(){
                $.post("/logout", function() {
                    $("#user").html('');
                    $(".unauthenticated").show();
                    $(".authenticated").hide();
                });
                return true;
            }

            // group

            , fetchGroupSimple(){
                var thisVue = this;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .listAllGroupsSimple()
                    .then(resp => {
                        /*if(resp.length == 0){
                        resp = [
                                {
                                    "groupId":2 ,
                                    "creator":"lonevetad",
                                    "groupName":"Ciao",
                                    "groupDate":"0017-12-10",
                                    "description":"tanti saluti",
                                    "location":1
                                }, {
                                "groupId":10,
                                "creator":"lonevetad",
                                "groupName":"Cucina itinerante",
                                "groupDate":"0019-12-10",
                                "description":"CIBOOOOOOOOOOOOOOOOOOOOOOOO",
                                "location":9
                            }, {
                                "groupId":16,
                                "creator":"calo",
                                "groupName":"Front-end TAASS",
                                "groupDate":"0020-12-09",
                                "description":"Lavoriamo alle pagine html",
                                "location":15
                            } ,{
                                    "groupId":22,
                                    "creator":"calo",
                                    "groupName":"Beviamoci su",
                                    "groupDate":"0019-12-10",
                                    "description":"Birra in compagnia",
                                    "location":21
                                }, {
                                "groupId":23,
                                "creator":"luca",
                                "groupName":"Back-end TAASS",
                                "groupDate":"0020-12-09",
                                "description":"Lavoriamo al back",
                                "location":22
                            }
                            ];
                        }*/
                        thisVue.groupsCarousel = resp;
                    }).catch(this.createErrorHandler("fetchGroupSimple"));
            }
        }
    });

    function setLoggedUsername(){
        app.getFacebookUserInfo();
    }

    var myusername = ""
    $.get("/user", function(data) {
        if(data != undefined && data != null && data.userAuthentication != undefined && data.userAuthentication != null){
            console.log(data);
            myusername = data.userAuthentication.details.name;
            app.setLoggedFacebook(myusername);
            $("#user").html(myusername);
        }
        $(".unauthenticated").hide();
        $(".authenticated").show();
    });



};