/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const APIUrl = "http://localhost:8080";
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
				if (response.hasOwnProperty("ErrorDetail")) {
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
					if (response.hasOwnProperty("ErrorDetail")) {
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
				if (response.hasOwnProperty("ErrorDetail")) {
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
				if (response.hasOwnProperty("ErrorDetail")) {
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
					if (response.hasOwnProperty("ErrorDetail")) {
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
					if (response.hasOwnProperty("ErrorDetail")) {
						reject(response);
					}
					resolve(response);
			})
			.catch(reject);
		});
	}

	newGroup(filters /*userCreator, title, date, location, maxDistance, description, genre, tags*/) {
		return new Promise((resolve, reject) => {
			fetch(this.baseURL + `/newGroup`, {
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
				if (response.hasOwnProperty("ErrorDetail")) {
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
				if (response.hasOwnProperty("ErrorDetail")) {
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
class UserWithID {
//it's the API payload
	constructor(userID = null, userName, password, email, enabled) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
	}
}

/** Represents a Group */
class Group {
//it's the API payload
	constructor(id = null, groupName, description, groupDate, creator) {
		this.id = id;
		this.groupName = groupName;
		this.description = description;
		this.groupDate = groupDate;
		this.creator = creator;

		// omessi perchè ancora non gestiti lato back-end
		/*
		this.location = location;
		this.maxDistance = maxDistance;
		this.genre = genre;
		this.tags = tags;
		*/
	}
}
