/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



const APIUrl = "http://localhost:8282";

// little utils

const checkResponseHoldsErrors = function(response){
	return ( response.hasOwnProperty("ErrorDetail")
		|| (response.hasOwnProperty('status') && response.hasOwnProperty('error') &&
				(response.status >= 300 || response.status < 200)) // not a "ok" response
		);
}


//

class ToGroup {

	constructor(url = null) {
		if (url === null) {
			url = APIUrl;
		}
		this.baseURL = url;
		this.user = new UserAPI(this.baseURL);
		this.group = new GroupAPI(this.baseURL);
	}

	getUserEndpoint() {
		return this.user;
	}

	getGroupEndpoint() {
		return this.group;
	}

	/**
	 * Returns the status of the API.
	 */
	status() {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/ping`) //fetch 'e una funzione delle Promise, che mi pare Vue renda disponibile se assente
			.then(response => response.json())
			.then(response => {
				if (checkResponseHoldsErrors(response)) {
					reject(response);
				}
				resolve(response);
			});
		});
	}

	ping() {
		return this.status();
	}

	restHome(){
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/resthome`, {
				method: "GET"
			})
				.then(response => response.json())
				.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
				});
		});
	}
}

/**
 * Represents the User API methods.
 */
class UserAPI {
	/**
	 * Creates a new User API instance, connected to the specified base URL.
	 * @param {String|null} url [Optional] The Base URL of the API.
	 */
	constructor(url = null) {
		if (url === null)
			throw new Error("Cannot get base URL of User API");
		this.baseURL = url + `/User`;
		//this.alternativeURL = `https://blabla qualcos'altro.com`;
	}

	getUserInfo(userName) {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/Info`, {
				method: "GET",
				credentials: 'include', // serve per mandare i cookie, soprattutto quelli di sessione, del "domain" corrente
				data: JSON.stringify({
					userName: userName
				})
			})
			.then(response => response.json())
			.then(response => {
				if (checkResponseHoldsErrors(response)) {
					reject(response);
				}
				resolve(response);
			})
			.catch(reject);
		});
	}

	getAllUsers() {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/listAllUsers`, {
				method: "GET"
			})
			.then(response => response.json())
			.then(response => {
				if (checkResponseHoldsErrors(response)) {
					reject(response);
				}
				resolve(response);
			})
			.catch(reject);
		});
	}

	// aggiungere anche login, register, ..

	register(newUserInfo){
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/register`, {
				method: "POST",
				headers: {
					'content-type': 'application/json'
				},
				body: JSON.stringify(newUserInfo)
			})
				.then(response => response.json())
				.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
				})
				.catch(reject);
		});
	}

	removeByID(userID){
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/deleteByID/` + userID, {
				method: 'DELETE'
				//, headers: {'Content-Type': 'application/json'}
				//, body: JSON.stringify({ "userID": userID }),
			})
				.then(response => response.json())
				.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
				})
				.catch(reject);
		});

	}
}


/**
 * Represents the Group API methods.
 */
class GroupAPI {
	/**
	 * Creates a new Group API instance, connected to the specified base URL.
	 * @param {String|null} url [Optional] The Base URL of the API.
	 */
	constructor(url = null) {
		if (url === null)
			throw new Error("Cannot get base URL of Group API");
		this.baseURL = url + `/Group`;
		//this.alternativeURL = `https://blabla qualcos'altro.com`;
	}

	getGroupInfo(groupID) {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/info`, {
				method: "GET",
				data: JSON.stringify({
					id: id
				})
			})
			.then(response => response.json())
			.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
			})
			.catch(reject);
		});
	}

	newGroup(filters) {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/createGroup`, {
				method: "POST",
				body: filters
			})
				.then(response => response.json())
				.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
				})
				.catch(reject);
		});
	}

	searchGroup(filters /*userCreator, title, date, location, maxDistance, description, genre, tags*/) {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/createGroup`, {
				method: "POST",
				body: filters
				/*{
                "groupName"		: title,
                "description"	: description,
                "groupDate"		: date,
                "creator"		: userCreator

                // omessi perchè ancora non gestiti lato back-end
                // , "location"	: location
                // , "maxDistance": maxDistance
                // , "genre"	: genre
                // , "tags"		: tags
            }
                */
			})
				.then(response => response.json())
				.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
				})
				.catch(reject);
		});
	}

	listAllGroups() {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/listGroupRest`, {
				method: "GET"
			})
				.then(response => response.json())
				.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
				})
				.catch(reject);
		});
	}


	listAllTags() {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/listTagRest`, {
				method: "GET"
			})
				.then(response => response.json())
				.then(response => {
					if (checkResponseHoldsErrors(response)) {
						reject(response);
					}
					resolve(response);
				})
				.catch(reject);
		});
	}


}

/** Represents a User, without a ID set */
class UserRegistration {
//it's the API payload
	constructor(userName, password, passwordRepeat, email) {
		this.userName = userName;
		this.password = password;
		this.passwordRepeat = passwordRepeat;
		this.email = email;
	}
}
/** Represents a User */
class User {
//it's the API payload
	constructor(userID = null, userName, password, email, enabled) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
	}
}


class GoogleLocation{
	constructor(lat=0.0, lng=0.0){
		this.lat = lat;
		this.lng = lng;
	}
}

/** Represents a New-Group informations*/
class GroupNew {
//it's the API payload
	constructor(creator="", groupName="", location=null, groupDate=null, description="", /*genre="",*/ tags=null
			/*location, */
		) {
		this.creator = creator;
		this.groupName = groupName;
		this.location = location;
		this.groupDate = groupDate;
		this.description = description;
		//this.genre = genre;
		this.tags = tags;

		// omessi perchè ancora non gestiti lato back-end
		/*
		*/
	}
	
	/**Trim strings*/
	format(){
		if(this.creator != null){
			this.creator = this.creator.trim()
		}
		if(this.groupName != null){
			this.groupName = this.groupName.trim()
		}
		if(this.description != null){
			this.description = this.description.trim()
		}
		return this;
	}

	addTag(tag){
		if(this.tags == null){
			this.tags = [tag];
			return true;
		}
		if(this.tags.includes(tag)){
			return false;
		}
		this.tags.push(tag);
		return true;
	}

	removeTag(tag, index){
		/*var i, elem, newTag, len;
		var t = this.tags;
		if(t == null){
			return false;
		}
		len = t.length;
		if(len <= 0){
			return false;
		}
		if(t.includes(tag)){
			newTag = [];
			i = -1;
			while ( ++i < len) {
				elem = t[i];
				if(tag !== elem){
					newTag.push(elem);
				}
			}
			this.tags = newTag;
			if(newTag.length <= 0){
				this.tags = null;
			}
			return true;
		}
		return false;*/
		if( this.tags == null || index < 0 || index >= this.tags.length || this.tags[index] !== tag){
			return false;
		}
		this.tags.splice(index, 1);
		if (this.tags.length <= 0){
			this.tags = null;
		}
		return true;
	}
}

/** Represents a Group */
class Group {
//it's the API payload
	constructor(id = null, creator="", groupName="", location=null, groupDate="2020-01-01", description="") {
		this.id = id;
		this.creator = creator;
		this.groupName = groupName;
		this.location = location;
		this.groupDate = groupDate;
		this.description = description;

		// omessi perchè ancora non gestiti lato back-end
		/*
		this.maxDistance = maxDistance; // only for search o.o
		this.genre = genre;
		this.tags = tags;
		*/
	}
}
/** Represents a search-Group informations*/
class GroupSearch {
//it's the API payload
	constructor( groupName="", location=null, dateStartRange="2019-01-01", dateEndRange="2048-01-01", creator="", maxDistance="0.0", genre="", tags=[]) {
		this.groupName = groupName;
		this.location = location;
		this.dateStartRange = dateStartRange;
		this.dateEndRange = dateEndRange;
		this.creator = creator;
		this.maxDistance = maxDistance; // only for search o.o
		this.genre = genre;
		this.tags = tags;
	}
}