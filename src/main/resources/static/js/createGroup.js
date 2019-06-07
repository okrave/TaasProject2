/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app;


//----------------------------------------------------------------------------------------------------------------------

class NewGroupProgression{
    constructor(){
        this.actualStep = 0;
        this.maxSteps = 4;
    }

    toNextStep(){
        if(this.actualStep < this.maxSteps){
            this.actualStep = this.actualStep + 1;
        }
    }
    toNextStep(){
        if(this.actualStep > 0){
            this.actualStep = this.actualStep - 1;
        }
    }
}

//----------------------------------------------------------------------------------------------------------------------

const googleMapApis = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";


//window.onload =
function initAll() {
    app = new Vue({
        el: "#appNewGroup",
        data: {
            toGroupAPI: new ToGroup()
            , messages: new NotificationsMessage()
            , tag: ""
            , locationSearch: ""
            , newGroupInfo: new GroupNew()
            , creationProgression : new NewGroupProgression()
            , allTags: [] //yet existing tags on database

            //google maps
            , gMapData: {
                map			: null,
                mapOptions	: {
                    zoom: 8,
                    center: null
                },
                defaultBounds : null,
                optionsAutocomplete: null,
                autocomplete: null
            }
        },
        mounted(){
            this.setUpGoogleMapsStuffs();
        },
        created() {
            let thisVue = this;
            this.ping();
            setTimeout( ()=>{thisVue.fetchYetExistingTags();}, 1000);
        },

        methods: {
            setUpGoogleMapsStuffs(){
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
                    console.log(err);
                    thisVue.messages.setErrorMessage(err);
                    thisVue.messages.clearMessagesAfter(5000);
                }
            }

            , addTag(){
                if( this.newGroupInfo.addTag(this.tag.trim())){
                    this.tag = "";
                }
            }

            , addTagFromExisting(tag){
                let prevTag = this.tag;
                this.tag = tag.trim();
                this.addTag();
                this.tag = prevTag;
            }

            , removeTag(tag){
                this.newGroupInfo.removeTag(tag);
            }

            //API

            , ping() {
                this.toGroupAPI.ping().then(resp => console.log("pinged :D " + resp));
            }

            , createGroup(){
                let thisVue = this;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .newGroup(this.newGroupInfo.format())
                    .then(response => {
                        console.log("group created successfully :D");
                        thisVue.setSuccessMessage("group created successfully :D");
                        thisVue.messages.clearMessagesAfter(3000);
                    })
                    .catch(this.createErrorHandler("create group"));
            }

            , fetchYetExistingTags(){
                let thisVue = this;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .listAllTags()
                    .then(response => {
                        thisVue.allTags = response;
                    })
                    .catch(this.createErrorHandler("list all tags"));
            }

            , getLocationFromAddress(){
                console.log("locationSearch: "+locationSearch);
                // https://maps.googleapis.com/maps/api/place/js/AutocompletionService.GetPredictions?1sTorino polo del
                // &4sit-IT&6m6&1m2&1d44.96502155524857&2d7.43074650610356&2m2&1d45.18613135982509&2d7.917921493896529&14e3&15e3&20sCC2E2C53-0C2D-4E4F-9F4B-CCD975A78F2964w8tengi17z&21m1&2e1&callback=_xdc_._uwrn9u&client=gme-addictive&channel=geocoder-tool&token=44656
                //googleGeolocalisation
            }
        }
    });
}


google.maps.event.addDomListener(window, 'load', initAll);