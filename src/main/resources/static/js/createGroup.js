/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

class NewGroupProgression {
    constructor() {
        this.actualStep = 0;
        this.maxSteps = 3;
    }

    toNextStep() {
        if (this.actualStep < this.maxSteps) {
            this.actualStep = this.actualStep + 1;
        }
    }

    toPreviousStep() {
        if (this.actualStep > 0) {
            this.actualStep = this.actualStep - 1;
        }
    }

    getActualStep() {
        return this.actualStep;
    }

    getMaxSteps() {
        return this.maxSteps;
    }
}

//----------------------------------------------------------------------------------------------------------------------

const googleMapApis = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";


function initAll() {
    app = new Vue({
        el: "#appNewGroup",
        data: {
            toGroupAPI: new ToGroup()
            , messages: new NotificationsMessage()
            , tag: ""
            , tag1: ""
            , terms: false
            , locationSearch: "Torino"
            , newGroupInfo: new GroupNew()
            , creationProgression: new NewGroupProgression()
            , allTags: []
            , tags: [
                {id: 1, name: 'Famiglia'},
                {id: 2, name: 'Montagna'},
                {id: 3, name: 'Escursioni'},
                {id: 4, name: 'Corsa'},
                {id: 5, name: 'Appuntamenti'},
                {id: 6, name: 'Studio'},
                {id: 7, name: 'Gruppo Lavoro'},
                {id: 8, name: 'Danza'},
                {id: 9, name: 'Karate'},
                {id: 10, name: 'Yoga'},
                {id: 11, name: 'Mare'},
                {id: 12, name: 'Intrattenimento'},
                {id: 13, name: 'Sport'},
                {id: 14, name: 'Lavoro'},
            ] //yet existing tags on database
            , filteredTags: []
            , filterTags: ''
            , isActive: true
            , activeClass: 'btn activeButton'
            , deactiveClass: 'btn deactiveButton'
            , googleMaps: false
            , userLogged: new UserLogged()
            , newTag: ""

            //google maps
            , gMapData: {
                map: null,
                mapOptions: {
                    zoom: 8,
                    center: null
                },
                defaultBounds: null,
                optionsAutocomplete: null,
                autocomplete: null

            }
        },
        mounted() {
            if (this.userLogged.reloadUserInfo()) { //not logged
                this.newGroupInfo.creatorId = this.userLogged.id;
                this.newGroupInfo.creator = this.userLogged.username;
            } else {
                this.userLogged.isLogged = false;
                window.location = "Home";
                //this.$router.push('Home');
            }
        },
        created() {
            let thisVue = this;
            this.ping();
            setTimeout(() => {
                thisVue.fetchYetExistingTags();
            }, 1000);

            this.removeLoader();
        },
        computed: {
            isLogged() {
                return (this.userLogged != null && this.userLogged !== undefined) ? this.userLogged.isLogged : false;
            },
            isDisabled: function () {
                return this.terms;

            },

            getFilteredTags() {
                if (this.filterTags === '') {
                    return this.allTags;
                }
                this.applyFilter();
                return this.filteredTags;
            }

            , selectedTags(){
                return this.newGroupInfo.tags == null ? "" : this.newGroupInfo.tags.join(", ");
            }
        },
        methods: {

            removeLoader() {
                document.getElementById('loaderCustom').style.visibility = 'hidden';
                document.getElementById('appNewGroup').style.visibility = 'visible';
                document.getElementById('map').style.visibility = 'visible';
            },

            setLocation(location) {
                console.log("dentro SetLocation");
                console.log(location.lng);
                console.log(location.lat);
                console.log(location.name);
                this.newGroupInfo.location = location;
            },

            activateMaps() {
                this.googleMaps = true;
            },
            disactivateMaps() {
                this.googleMaps = false;
            },


            setUpGoogleMapsStuffs() {
                var inputElement, thisVue;
                thisVue = this;
                inputElement = document.getElementById("locationQuery");
                console.log("setting up google maps api START");

                thisVue.gMapData.mapOptions.center = new google.maps.LatLng(45.075741, 7.673600);
                thisVue.gMapData.map = new google.maps.Map(
                    document.getElementById('map-canvas'),
                    thisVue.gMapData.mapOptions
                );
                thisVue.gMapData.defaultBounds = new google.maps.LatLngBounds(
                    new google.maps.LatLng(45.07, 7.67),
                    new google.maps.LatLng(45.08, 7.68)
                );
                thisVue.gMapData.optionsAutocomplete = {bounds: thisVue.gMapData.defaultBounds};

                thisVue.gMapData.map.controls[google.maps.ControlPosition.TOP_LEFT].push(inputElement);
                thisVue.gMapData.autocomplete = new google.maps.places.Autocomplete(
                    inputElement,
                    thisVue.gMapData.optionsAutocomplete
                );

                console.log("setting up google maps api END");
            },


            createErrorHandler(methodName) {
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err.status + " - " + err.error + "\n-----\n" + err.message);
                    thisVue.messages.setErrorMessage(err);
                    thisVue.messages.clearMessagesAfter(5000);
                }
            }


            , toNextStep() {
                this.creationProgression.toNextStep();
                this.checkActualStep();
            }
            , toPreviousStep() {
                this.creationProgression.toPreviousStep();
                this.checkActualStep();
            }
            , checkActualStep() {
                console.log("Entrato checkActual");
                if (this.creationProgression.getActualStep() == 0) {
                    document.getElementById('map').style.visibility = 'visible';
                } else {
                    document.getElementById('map').style.visibility = 'hidden';
                }
            }

            , selectTag(tagName) {

                let buttonTag = document.getElementById(tagName);
                if ((this.newGroupInfo.tags == null) || (!this.newGroupInfo.tags.includes(tagName))) {
                    buttonTag.className = "btn activate-tag";
                    selected
                    this.newGroupInfo.addTag(tagName);
                } else {
                    buttonTag.className = "btn deactivate-tag";
                    this.newGroupInfo.removeTag(tagName); // selectedTag = this.selectedTag.filter(tag => tag !== tagName);
                }
            }

            , addTag(){
                this.newGroupInfo.addTag(this.newTag);
            }

            , createNewTag() {
                console.log("Entra in createTag");
                if (this.newTag != "") {
                    this.toGroupAPI
                        .getGroupEndpoint()
                        .createNewTag(this.newTag.trim())
                        .then(response => {
                            this.fetchYetExistingTags();
                            console.log("Tag created successfully :D");
                        }).catch(this.createErrorHandler("create group"));
                }

            }


            /*if (this.terms == true) {//disattivato
            // 20/07/2019 Marco: usare i metodi che ho gia' fatto di newGroupInfo, o meglio della sua classe di toGroupAPI.js
                this.terms = false;
                if (!this.selectedTag.includes(tagName)) {
                    this.selectedTag.push(tagName);
                }


            } else {
                this.term = true;
                if (this.selectedTag.includes(tagName)) {
                    this.selectedTag.splice(tagName);
                }
            }*/

            /*if( this.newGroupInfo.addTag(this.tag.trim())){
                this.selectedTag.push(this.tag.trim)
                this.tag = "";
            }*/


            , addTagFromExisting(tag) {
                let prevTag = this.tag;
                this.tag = tag.trim();
                this.addTag();
                this.tag = prevTag;
            }

            , removeTag(tag, index) {
                this.newGroupInfo.removeTag(tag, index);
            }
            , applyFilter() {
                if (this.filterTags == null || this.filterTags === '') {
                    return;
                }
                let filter = this.filterTags.trim();
                this.filteredTags = this.allTags.filter(record => {
                    record = record["name"];
                    return record === filter || record.includes(filter);
                });
            }

            , formatDateGroup(dg) {
                var splitted;
                splitted = dg.split("-");
                console.log("original date: " + dg + ", splitted: " + JSON.stringify(splitted));
                if (splitted[0].length != 4) {
                    //so, the format is not yyyy-mm-dd and should be like this !
                    console.log("then reverse: : " + JSON.stringify(splitted.reverse()) + ", and joined: " + splitted.reverse().join("-"));
                    return splitted.reverse().join("-");
                }
                return dg;
            }

            //API

            , ping() {
                this.toGroupAPI.ping().then(resp => console.log("pinged :D " + resp));
            }

            , createGroup() {
                let thisVue = this, ngi;


                if (this.locationSearch == null || this.locationSearch === '') {
                    alert("Location not setted");
                    return;
                }

                //get location, in some way
                //this.newGroupInfo.location = GoogleLocation.fromString(this.locationSearch);

                //now the date
                //this.newGroupInfo.groupDate = this.formatDateGroup(this.newGroupInfo.groupDate);

                ngi = this.newGroupInfo.format();

                console.log("creating group:");
                console.log(JSON.stringify(ngi));

                if ((ngi.creatorId == null || ngi.creatorId === '')
                    || (ngi.creator == null || ngi.creator === '')
                    || (ngi.groupName == null || ngi.groupName === '')
                    || (ngi.description == null || ngi.description === '')
                    || (ngi.location == null || ngi.location === '')
                    || (ngi.tags == null || ngi.tags.length <= 0)
                ) {
                    alert("fill all new group info");
                    return;
                }
                this.toGroupAPI
                    .getGroupEndpoint()
                    .newGroup(ngi)
                    .then(response => {
                        console.log("group created successfully :D");
                        thisVue.messages.setSuccessMessage("group created successfully :D");
                        thisVue.messages.clearMessagesAfter(3000);
                    })
                    .catch(this.createErrorHandler("create group"));
            }

            , fetchYetExistingTags() {
                let thisVue = this;
                console.log("entro in fetch");
                this.toGroupAPI
                    .getGroupEndpoint()
                    .listAllTags()
                    .then(response => {
                        if (response != null && response !== undefined && response.length > 0) {
                            thisVue.allTags = thisVue.filteredTags = response;
                        }
                    })
                    .catch(this.createErrorHandler("list all tags"));
            }

            , getLocationFromAddress() {
                console.log("locationSearch: " + locationSearch);
                // https://maps.googleapis.com/maps/api/place/js/AutocompletionService.GetPredictions?1sTorino polo del
                // &4sit-IT&6m6&1m2&1d44.96502155524857&2d7.43074650610356&2m2&1d45.18613135982509&2d7.917921493896529&14e3&15e3&20sCC2E2C53-0C2D-4E4F-9F4B-CCD975A78F2964w8tengi17z&21m1&2e1&callback=_xdc_._uwrn9u&client=gme-addictive&channel=geocoder-tool&token=44656
                //googleGeolocalisation
            }
        }
    });
}

window.onload = _ => {

    $(document).ready(attachClicksToModalButtonsLoginRegister);

    initAll();
};
