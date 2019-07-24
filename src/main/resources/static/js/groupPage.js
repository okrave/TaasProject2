var app;

const RELOAD_MSG_INTERVAL = 5000;
const J2EE_URL = "localhost:8081/J2EEToGroup-web/NewServlet/";

window.onload = _ =>{

    $(document).ready(attachClicksToModalButtonsLoginRegister);

    app = new Vue({
        el:"#appGroupPage",
        data: {
            toGroupAPI : new ToGroup()
            , j2eeUrl: J2EE_URL
            ,groupInfo: new GroupFullDetail()
            ,listElement: 0
            ,listUser : []
            ,currentUrl : ""
            ,userLogged: new UserLogged()
            , isUserMember: false

            //messages:
            , newMessage: new MessageNewPayload()
            , msgFilter: new MessageQueryPayload()
            , groupMessages: []
            , msgUpdaterIntervalID: 0
        },
        mounted(){
            var thisVue;
            thisVue = this;
            this.userLogged.reloadUserInfo(ul => {
                thisVue.newMessage.userId = ul.id;
            });
            this.recalculateIsMember();
            //this.getAllUser();
            this.reloadMessageList();
        },

        created(){
            this.currentUrl = window.location.pathname;
            this.loadGroup();
            this.ping();
            this.removeLoader();
        },

        computed:{
            isLogged(){
                return (this.userLogged != null && this.userLogged  !== undefined) ? this.userLogged.isLogged : false;
            },
            isUserLogged() {
                return this.userLogged.isLogged;
            }

            , getCreatorId(){
                return this.groupInfo.creatorId;
            }
            , lastMsgDate() {
                var lastMsg;
                if(this.groupMessages == null || this.groupMessages === undefined || this.groupMessages.length == 0)
                    return null;
                lastMsg = this.groupMessages[this.groupMessages.length - 1];
                return lastMsg.dateCreation;
            }
        },

        methods:{

            removeLoader(){
                document.getElementById('loaderCustom').style.visibility = 'hidden';
                document.getElementById('appGroupPage').style.visibility = 'visible';

            },

            recalculateIsMember(){
                var thisVue = this;
                var groupMember = new MemberGroupPayload(this.userLogged.id,this.userLogged.username,this.groupInfo.groupId);
                this.toGroupAPI
                    .getGroupEndpoint()
                    .isMember(groupMember)
                    .then(resp => {
                        console.log("isMember: " + resp);
                        thisVue.isUserMember = resp;
                    }).catch(err => {
                        thisVue.isUserMember = false;
                    });
            }
            ,addGroupMember(){
                let groupMember = new MemberGroupPayload(this.userLogged.id,this.userLogged.username,this.groupInfo.groupId);
                this.toGroupAPI
                    .getGroupEndpoint()
                    .addGroupMember(groupMember)
                    .then(resp => {
                        console.log("paginaGruppo:D - add g member");
                        console.log(JSON.stringify(resp));
                        this.loadGroup();
                        //this.groupInfo.members = resp.members;

                    })

            },

            removeGroupMember(){
                let groupMember = new MemberGroupPayload(this.userLogged.id,this.userLogged.username,this.groupInfo.groupId);
                this.toGroupAPI
                    .getGroupEndpoint()
                    .removeGroupMember(groupMember)
                    .then(resp => {
                    console.log("removeGroupMember: ");
                    console.log(JSON.stringify(resp));
                    this.loadGroup();
                //this.groupInfo.members = resp.members;

            })

            }

            ,setListElement(liNumber){
                this.listElement = liNumber;
                console.log(this.listElement);
                if(liNumber == 2){
                    //messages
                    this.restartMsgUpdater();
                } else {
                    this.stopMsgUpdater();
                }
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
                        this.listUser = resp;
                    })
            }

            ,loadGroup(){
                var piecesUrl = this.currentUrl.split("/");
                var groupId = piecesUrl[piecesUrl.length-1];
                this.toGroupAPI
                    .getGroupEndpoint()
                    .getGroupInfo(groupId)
                    .then(resp => {
                        var gId;
                        gId = resp.groupId;
                        this.groupInfo.creatorId = resp.creatorId;
                        this.groupInfo.creator = resp.creator;
                        this.groupInfo.groupName = resp.groupName;
                        this.groupInfo.data = resp.groupDate;
                        this.groupInfo.description = resp.description;
                        this.groupInfo.groupId = gId;
                        this.groupInfo.locationId = resp.location.locationId;
                        this.groupInfo.location = resp.location;
                        this.groupInfo.members = resp.members;
                        this.groupInfo.tags = resp.tags;
                        this.toGroupAPI.isLoaded = true;

                        this.msgFilter.groupId = gId;
                        this.newMessage.groupId = gId;

                        this.recalculateIsMember();
                    })
                    .catch(this.createErrorHandler("register"));

            }
            ,ping(){
                this.toGroupAPI.ping().then( resp => console.log("pinged :D " + resp) );
            }

            , restartMsgUpdater(){
                var thisVue = this;
                this.reloadMessageList();
                this.msgUpdaterIntervalID = setInterval(
                    //this.reloadMessageList
                    function () {
                        console.log("reload message list");
                        thisVue.reloadMessageList();
                    }
                    , RELOAD_MSG_INTERVAL);
            }
            , stopMsgUpdater(){
                clearInterval(this.msgUpdaterIntervalID);
            }
            , reloadMessageList(){
                this.msgFilter.dateStart = this.lastMsgDate;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .fetchMessages(this.msgFilter)
                    .then(resp => {
                        const msgs = resp.messages;
                        if(msgs != null || msgs !== undefined)
                            this.groupMessages = msgs;
                    })
                    .catch(err =>{
                        console.log(err);
                        this.stopMsgUpdater();
                    });
            }

            , sendMessage(){
                this.stopMsgUpdater();
                this.toGroupAPI
                    .getGroupEndpoint()
                    .sendMessage(this.newMessage)
                    .then(resp => {
                        if(resp) this.newMessage.testo = "";
                        this.reloadMessageList();
                        this.restartMsgUpdater();
                    })
                    .catch(err => {
                        this.restartMsgUpdater();
                        console.log(err);
                    });
            }

            , splitTextToLines(text){
                return text.split("\n");
            }

        }

    });
};