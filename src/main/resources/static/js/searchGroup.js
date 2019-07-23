/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app;


//----------------------------------------------------------------------------------------------------------------------

window.onload = _ => {

	$(document).ready(attachClicksToModalButtonsLoginRegister);

	app = new Vue({
		el: "#appSearch",
		data: {
			toGroupAPI: new ToGroup()
			, filterSearch: new GroupSearch()
			, tag : ""
			, messages: new NotificationsMessage()
			, groups: []

			//tags yet present on remove
			, allTags: [] //yet existing tags on database
			, filteredTags : []
			, filterTags: ''
		},
		created(){
			this.ping();
		}
		, computed: {
			isLogged(){
				return (this.userLogged != null && this.userLogged  !== undefined) ? this.userLogged.isLogged : false;
			},

			getFilteredTags(){
				if(this.filterTags === '') {
					return this.allTags;
				}
				this.applyFilter();
				return this.filteredTags;
			}
		}
		, methods: {

			// utils

			createErrorHandler(functionName){
				return err => {
					console.log("error on " + functionName + ":");
					console.log(err);
					this.messages.setErrorMessage(err);
					this.messages.clearMessagesAfter(5000);
				};
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

			, removeTag(tag, index){
				this.newGroupInfo.removeTag(tag, index);
			}
			, applyFilter(){
				if(this.filterTags == null || this.filterTags === '') {
					return;
				}
				let filter = this.filterTags.trim();
				this.filteredTags = this.allTags.filter( record => {
					record = record["name"];
					return record === filter || record.includes(filter);
				});
			}

			, formatDateGroup(dg){
				var splitted;
				splitted = dg.split("-");
				if(splitted[0].length != 4){
					//so, the format is not yyyy-mm-dd and should be like this !
					return splitted.reverse().join("-");
				}
				return dg;
			}

			// API
			
			, ping(){
				console.log("pinging");
				this.toGroupAPI
				.ping()
				.then(response => {
					console.log("connected successfully");
				})
				.catch(this.createErrorHandler("ping"));
			}


			//group
			, listAllGroups(){
				this.toGroupAPI
				.getGroupEndpoint()
				.listAllGroups()
				.then(resp => {
					console.log("groups fetched");
					this.groups = resp
				})
				.catch(this.createErrorHandler("listAllGroups"));
			}

			, advSearchGroups(){
				let thisVue = this;
				this.toGroupAPI
				.getGroupEndpoint()
				.searchGroups(this.filterSearch)
				.then(resp => {
				})
				.catch(this.createErrorHandler("searchGroups"));
			}

			, deleteGroup(groupId){
				console.log("removing: " + groupId);
			}
		}
	});
};