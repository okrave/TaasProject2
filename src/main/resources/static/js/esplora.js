var app;


function initMap() {
    var torino = new google.maps.LatLng(45.070, 7.686);

    infowindow = new google.maps.InfoWindow();

    map = new google.maps.Map(document.getElementById('map'),{
        center: torino,
        zoom: 14,
        disableDefaultUI: true,
        gestureHandling: 'greedy'
    });
}

function createInfoWindows(group){

    var groupTitle = group.groupName;
    var groupCreator = group.creator;
    var groupData = group.groupDate;
    var groupDescription = group.description;
    var groupId = group.groupId;
    var htmlInfoWindows =
        '<div>' +
        '<h1><a href="/group_page/'+ groupId+'">'+ groupTitle +'</a></h1>'+
        //'<h1><a href="localhost:8080/group_page/49">'+ groupTitle +'</a></h1>'+
        '<p>'+ groupCreator +'</p>'+
        '<p>'+ groupData +'</p>'+
        '<p>'+ groupDescription +'</p>'+
        '</div>';

    return htmlInfoWindows;
}

function setAllMarkerMaps(){
    var groups = app.groupFind;

    for (var i = 0; i < groups.length; i++) {
        console.log(groups[i].location.lat);
        console.log(groups[i].location.lng);
        var latlng = new google.maps.LatLng(groups[i].location.lat,groups[i].location.lng);
        var contentInfo = createInfoWindows(groups[i]);
        setOneMarkerMaps(latlng,contentInfo);
    }
}

function setOneMarkerMaps(latlng,contentInfo) {
    var marker = new google.maps.Marker({
        map: map,
        position: latlng
    });

    var infowindow = new google.maps.InfoWindow({
        content: contentInfo
    });

    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map, this);
    });

}

window.onload = _ =>{

    app = new Vue({
        el:"#appGroupEsplora",
        data: {
            toGroupAPI : new ToGroup(),
            userLogged: new UserLogged(),
            typeSearch : "tags",
            inputGroupName : "",
            inputCreator: "",
            inputDate : null,
            groupFind : [],
            map : null,
            service: null,
            infowindow: null,
            myLocation: "torino",

            //tags
            tagNewAndFIlter: "", // agisce sia come "nuovo tag" che come filtro di quelli gia' esistenti
            allTags : [],
            filteredTags: [],
            filterTags: ""

            , filters: new GroupSearch()/*{
                groupName: "",
                creatorMember: "", //name, not id
                isSearchingByCreator: true, // if false, search by member
                dateStartRnge: null,
                dateEndRAnge: null,
                location: null,
                maxDistance: 0.0,
                tags: []
            }*/
            /*
            GroupSearch
	            (creator="", groupName="", location=null, tags=null,
				dateStartRange=null, dateEndRange=null, maxDistance="0.0")
            * */
        },

        mounted(){
            this.userLogged.reloadUserInfo();
        },

        created(){
            this.removeLoader();
        },

        computed: {
            getTypeSearch:function () {
                return this.typeSearch;
            }

            , getFilteredTags(){
                if(this.filterTags === '') {
                    return this.allTags;
                }
                this.applyFilter();
                return this.filteredTags;
            }

            , getSelectedTagsAsList(){ // deprecated
                return this.filters.tags.join(", ");
            }
        },

        methods:{
            removeLoader(){
                document.getElementById('loaderCustom').style.visibility = 'hidden';
                document.getElementById('appGroupEsplora').style.visibility = 'visible';
            }

            , switchFilterCreatorOrMember(){
                this.filters.creatorMember = ! this.filters.creatorMember;
            }

            , searchLocationThroughGoogleMaps(){
                // TODO to be implemented
                console.log("searchLocationThroughGoogleMaps TO BE IMPLEMENTED");
            }

            , addTag(){
                if( this.filters.addTag(this.tagNewAndFIlter.trim())){
                    this.tagNewAndFIlter = "";
                }
            }
            , addTagFromExisting(tag){
                let prevTag = this.tagNewAndFIlter;
                this.tagNewAndFIlter = tag.trim();
                this.addTag();
                this.tagNewAndFIlter = prevTag;
            }
            , removeTag(tag, index){
                this.filters.removeTag(tag, index);
            }
            , applyFilter(){
                if(this.tagNewAndFIlter == null || this.tagNewAndFIlter === '') {
                    return;
                }
                let filter = this.tagNewAndFIlter.trim();
                this.filteredTags = this.allTags.filter( record => {
                    record = record["name"];
                    return record === filter || record.includes(filter);
                });
            }
            /*
            * */

            ,findGroupByTag(tagName){
                console.log(tagName);
            }

            ,mapsSearch(){
                this.typeSearch = "maps";
                document.getElementById('map').style.visibility = 'visible';

                this.toGroupAPI
                    .getGroupEndpoint()
                    .listAllGroups()
                    .then(response => {
                        console.log("Risposta search group maps: ");
                        console.log(response);
                        this.groupFind = response;
                        setAllMarkerMaps();
                    }).catch(this.createErrorHandler("create group"));


            }

            ,tagsSearch(){
                document.getElementById('map').style.visibility = 'hidden';
                this.typeSearch = "tags";
                let thisVue = this;
                this.toGroupAPI
                    .getGroupEndpoint()
                    .listAllTags()
                    .then(response => {
                        if(response != null && response !== undefined && response.length > 0) {
                            console.log(response);
                            thisVue.allTags = thisVue.filteredTags = response;
                        }
                })
            .catch(this.createErrorHandler("list all tags"));
            }

            ,dateSearch(){
                document.getElementById('map').style.visibility = 'hidden';
                this.typeSearch = "date";

            }

            ,dateSearchGroup(){
                var filters = new GroupSearch();

                if(this.inputDate != null){
                    filters.setDate(this.inputDate);
                    this.searchGroup(filters);
                }
            }

            ,userOrGroupSearch(){
                document.getElementById('map').style.visibility = 'hidden';
                this.typeSearch = "userOrGroup";

                var filters = new GroupSearch();
                var isIn = false;
                if(this.inputCreator != ""){
                    filters.setCreator(this.inputCreator);
                    isIn = true;

                }

                if(this.inputGroupName != ""){
                    filters.setGroupName(this.inputGroupName);
                    isIn = true;
                }

                if(isIn)
                    this.searchGroup(filters);

            }

            , searchAdvanced(){
                // TODO to be implemented
                console.log("ricerca avanzata");
            }
            ,searchGroup(filters){
                this.toGroupAPI
                    .getGroupEndpoint()
                    .searchGroups(filters)
                    .then(response => {
                        console.log("Risposta search group: ");
                        console.log(response[0]);
                        console.log("tutta la response search group: ");
                        console.log(JSON.stringify(response));
                        this.groupFind = response;
                        this.viewGroupFind();
                    }).catch(this.createErrorHandler("create group"));

            }


            ,createErrorHandler(methodName) {
                let thisVue = this;
                return function (err) {
                    console.log("Error on method: " + methodName);
                    console.log(err.status + " - " + err.error + "\n-----\n" + err.message);
                }
            }
        }

    });

};