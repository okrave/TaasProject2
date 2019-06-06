/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app;


//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {
	app = new Vue({
		el: "#appSearch",
		data: {
			toGroupAPI: new ToGroup()
			, filterSearch: {
				userCreator	: "",
				groupName	: "",
				location	: "",
				maxDistance	: 0.0,
				date		: "2020-01-01",
				description	: "",
				genre		: "",
				tags		: []
			}
			, tag : ""
			, messages: new NotificationsMessage()
			, groups: []
		},
		created(){
			this.ping();
		},

		methods: {
			// utils


			errorHandling(functionName){
				return err => {
					console.log("error on " + functionName + ":");
					console.log(err);
					this.messages.setErrorMessage(err);
				};
			},

			addTag(){
				if(! this.filterSearch.tags.includes(this.tag)){
					this.filterSearch.tags.push(this.tag);
					this.tag = "";
				}
			},
			removeTag(tag){
				var i, elem;
				var t = this.filterSearch.tags;
				var len = t.length;
				var newTag, t;
				if(this.filterSearch.tags.includes(tag)){
					newTag = [];
					i = -1;
					while ( ++i < len) {
						elem = t[i];
						if(tag !== elem){
							newTag.push(elem);
						}
					}
					this.filterSearch.tags = newTag;
				}
			},

			// API
			
			ping(){
				console.log("pinging");
				this.toGroupAPI
				.ping()
				.then(response => {
					console.log("connected successfully");
				})
				.catch(this.errorHandling("ping"));
			},

			//users


			//group
			listAllGroups(){
				this.toGroupAPI
				.getGroupEndpoint()
				.listAllGroups()
				.then(resp => {
					console.log("groups fetched");
					this.groups = resp
				})
				.catch(this.errorHandling("listAllGroups"));
			},

			newGroup(){
				let thisVue = this;
				this.toGroupAPI
				.getGroupEndpoint()
				.newGroup(this.filterSearch)
				.then(resp => {
					console.log("group created");
					thisVue.messages.setSuccessMessage("group created");
				})
				.catch(this.errorHandling("newGroup"));

			}
		}
	});
};