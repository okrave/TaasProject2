/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;

function removeAllModalBackdrop() {
    document.querySelectorAll(".modal-backdrop").forEach(e => {
        e.remove();
        e.parentNode.removeChild(e);
    })
}

function switchFromRegistModalToLogin(){
    $('#registerModal').modal('hide');
    removeAllModalBackdrop();
    $('#loginModal').modal('show');
    return false;
}

function switchFromLoginModalToRegistration(){
    $('#loginModal').modal('hide');
    removeAllModalBackdrop();
    $('#registerModal').modal('show');
    return false;
}

//----------------------------------------------------------------------------------------------------------------------

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
        el: "#appLoginRegister",
        data: {
            toGroupAPI: new ToGroup()

            , userInfo: {
                username            : "",
                password            : "",
                email               : "",
                passwordConfirmation: ""
            }
            , messages: new NotificationsMessage()

            , groupsCarousel: null
},
        created() {
            this.ping();
            this.fetchGroupSimple();
        },

        computed: {
            groupToGroupsCarousel() {
                var ret, groupSize, i, gsIndex, groupEachSection, gC; //  sectionAmount,
                gC = this.groupsCarousel;
                if(gC == null) return [];
                ret = [];
                groupSize = gC.length;
                groupEachSection = 3;

                console.log("... during groupToGroupsCarousel: groups received:");
                console.log(JSON.stringify(gC));
                console.log("sections: :");
                //sectionAmount = Math.floor(groupSize / 3);
                i = 0;
                while( i < groupSize ) {
                    var section = [];
                    gsIndex = -1;
                    while( ++gsIndex < groupEachSection && i < groupSize) {
                        section.push(gC[i]);
                        i++;
                    }
                    ret.push(section);
                    console.log("section: ...");
                    console.log(JSON.stringify(section));
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
            createErrorHandler(methodName){
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err);

                    thisVue.messages.setErrorMessage(err);
                    thisVue.messages.clearMessagesAfter(5000)

                }
            }

            //API

            , ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }

            , login(){
                console.log("Loggin: " + this.userInfo);
                //this.toGroupAPI
                //    .getUserEndpoint()
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
                    })
                    .catch(this.createErrorHandler("register"));
            }

            // group

            , fetchGroupSimple(){
                var thisVue = this;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .listAllGroupsSimple()
                    .then(resp => {
                        if(resp.length == 0){
                            resp = /*JSON.parse(//"[{\"groupId\":2,\"creator\":\"lonevetad\",\"groupName\":\"Ciao\",\"groupDate\":\"0017-12-10\",\"description\":\"tanti saluti\",\"location\":1},{\"groupId\":10,\"creator\":\"lonevetad\",\"groupName\":\"Cucina itinerante\",\"groupDate\":\"0019-12-10\",\"description\":\"CIBOOOOOOOOOOOOOOOOOOOOOOOO\",\"location\":9},{\"groupId\":16,\"creator\":\"calo\",\"groupName\":\"Front-end TAASS\",\"groupDate\":\"0020-12-09\",\"description\":\"Lavoriamo alle pagine html\",\"location\":15},{\"groupId\":22,\"creator\":\"calo\",\"groupName\":\"Beviamoci su\",\"groupDate\":\"0019-12-10\",\"description\":\"Birra in compagnia\",\"location\":21}]")
                                "[" +
                                    "[" +
                                    " {\"groupId\":2 ,\"creator\":\"lonevetad\",\"groupName\":\"Ciao\",\"groupDate\":\"0017-12-10\",\"description\":\"tanti saluti\",\"location\":1}" +
                                    ",{\"groupId\":10,\"creator\":\"lonevetad\",\"groupName\":\"Cucina itinerante\",\"groupDate\":\"0019-12-10\",\"description\":\"CIBOOOOOOOOOOOOOOOOOOOOOOOO\",\"location\":9}" +
                                    ",{\"groupId\":16,\"creator\":\"calo\",\"groupName\":\"Front-end TAASS\",\"groupDate\":\"0020-12-09\",\"description\":\"Lavoriamo alle pagine html\",\"location\":15}" +
                                    "],[" +
                                    " {\"groupId\":22,\"creator\":\"calo\",\"groupName\":\"Beviamoci su\",\"groupDate\":\"0019-12-10\",\"description\":\"Birra in compagnia\",\"location\":21}" +
                                    ", {\"groupId\":23,\"creator\":\"luca\",\"groupName\":\"Back-end TAASS\",\"groupDate\":\"0020-12-09\",\"description\":\"Lavoriamo al back\",\"location\":22}" +
                                    "]" +
                                    "]")*/
                                [ //
                                    //[
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
                                        }
                                    ,//], [

                                        {
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
                                    //]
                                ];

                        }
                        console.log("resp is:\n" + JSON.stringify(resp));
                        thisVue.groupsCarousel = resp; //thisVue.groupToGroupsCarousel(resp);
                    })
                    .catch(this.createErrorHandler("fetchGroupSimple"))
            }
        }
    });
};