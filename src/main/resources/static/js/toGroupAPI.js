/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const APIUrl = "http://localhost:8080";
// little utils

const checkResponseHoldsErrors = function (response) {
    return (response.hasOwnProperty("ErrorDetail")
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
        this.isLoaded = false;
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

    restHome() {
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

    getFacebookUserInfo() {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/facebookUser/`, {
                method: "GET"
            })
                .then(response => response.json())
                .then(response => {
                    if (checkResponseHoldsErrors(response)) {
                        reject(response);
                    }
                    resolve(response);
                }).catch(reject);

        });

    }

    getUserInfo2(userID) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/info/` + userID, {
                method: "GET"
            })
                .then(response => response.json())
                .then(response => {
                    if (checkResponseHoldsErrors(response)) {
                        reject(response);
                    }
                    resolve(response);
                }).catch(reject);

        });
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

    login(newUserInfo) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/login`, {
                method: "PATCH",
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
                }).catch(reject);
        });

    }


    register(newUserInfo) {
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

    removeByID(userID) {
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
        this.baseURL = url + '/Group';
        //this.alternativeURL = `https://blabla qualcos'altro.com`;
    }

    isMember(groupMember) {

        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/isMember`, {
                method: "PATCH"
                , headers: {'content-type': 'application/json'},
                body: JSON.stringify(groupMember)
            })
                .then(response => response.json())
                .then(response => {
                    if (checkResponseHoldsErrors(response)) {
                        reject(response);
                    }
                    resolve(response);
                }).catch(reject);
        });


    }

    removeGroupMember(groupMember) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/removeMember`, {
                method: "PATCH",
                headers: {'content-type': 'application/json'},
                body: JSON.stringify(groupMember)
            })
                .then(response => response.json())
                .then(response => {
                    if (checkResponseHoldsErrors(response)) {
                        reject(response);
                    }
                    resolve(response);
                }).catch(reject);
        });
    }

    addGroupMember(groupMember) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/addMember`, {
                method: "PATCH",
                headers: {'content-type': 'application/json'},
                body: JSON.stringify(groupMember)
            })
                .then(response => response.json())
                .then(response => {
                    if (checkResponseHoldsErrors(response)) {
                        reject(response);
                    }
                    resolve(response);
                }).catch(reject);
        });

    }

    getGroupInfo(groupID) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/info/` + groupID, {
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

    newGroup(filters) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/createGroup`, {
                method: "POST",
                headers: {'content-type': 'application/json'},
                body: JSON.stringify(filters)
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

    searchGroups(filters) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/advGroupSearch`, {
                method: "PATCH"
                , headers: {'content-type': 'application/json'},
                body: JSON.stringify(filters)
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

    listAllGroupsSimple() {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/listGroupSimple`, {
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

    loadUserGroups(userID) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/userGroups/` + userID, {
                method: "GET"
            })
                .then(response => response.json())
                .then(response => {
                    if (checkResponseHoldsErrors(response)) {
                        reject(response);
                    }
                    resolve(response);
                }).catch(reject);

        });

    }


    createNewTag(newTag) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/createTag/` + newTag, {
                method: "GET"
            })
                .then(response => response.json())
                .then(response => {
                    resolve(response);
                }).catch(reject);

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

    deleteGroup(groupId) {

    }

    fetchMessages(msgQueryPayload) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/fetchMessages`, {
                method: "PATCH" // TODO dovrebbe essere una GET, lo diventera' appena si trova il modo di passare un oggetto nella query in modo automatico
                , headers: {'content-type': 'application/json'},
                body: JSON.stringify(msgQueryPayload)
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

    sendMessage(messageNewPayload) {
        return new Promise((resolve, reject) => {
            fetch(this.baseURL + `/sendMessage`, {
                method: "POST"
                , headers: {'content-type': 'application/json'},
                body: JSON.stringify(messageNewPayload)
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

class MemberGroupPayload {

    constructor(userId, userName, groupId) {
        this.userName = userName;
        this.userId = userId;
        this.groupId = groupId;

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


/** Represents a Group */

/*
class Group {
//it's the API payload
	constructor(id = null, creator="", groupName="", location=null, groupDate="2020-01-01", description="", tags = null) {
		this.id = id;
		this.creator = creator;
		this.groupName = groupName;
		this.location = location;
		this.groupDate = groupDate;
		this.description = description;
		this.tags = tags;

		// omessi perchè ancora non gestiti lato back-end
		//this.maxDistance = maxDistance; // only for search o.o
		//this.tags = tags;
	}
}*/

class GroupBasic {
    constructor(creator = "", groupName = "", location = null, tags = null) {
        this.creator = creator;
        this.groupName = groupName;
        this.location = location;
        this.tags = tags;
    }

    /**Trim strings*/
    format() {
        if (this.creator != null) {
            this.creator = this.creator.trim()
        }
        if (this.groupName != null) {
            this.groupName = this.groupName.trim()
        }
        if (this.description != null) {
            this.description = this.description.trim()
        }
        return this;
    }

    addTag(tag) {
        var i;
        if (this.tags == null) {
            this.tags = [];
        }
        if (!this.tags.includes(tag)) {
            this.tags.push(tag);
        }
    }

    removeTag(tag, index) {
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
        if (this.tags == null || index < 0 || index >= this.tags.length || this.tags[index] !== tag) {
            return false;
        }
        this.tags.splice(index, 1);
        if (this.tags.length <= 0) {
            this.tags = null;
        }
        return true;
    }
}


class GoogleLocation {
    constructor(lat = 0.0, lng = 0.0) {
        //this.locationId = 0;
        this.lat = lat;
        this.lng = lng;
        //this.groupId = 0;
    }

    static fromString(text) {
        text = text.replace("(", "");
        text = text.replace(")", "");
        text = text.replace("\'", "");
        /*
            La stringa ora e' composta da due numeri, con notazione americana
            (la nostra virgola e' il loro punto), separati da una virgola.
            Separo quindi i due numeri con uno split, che fornisce un array
            di stringhe, e mappo tali stringhe in qualcos'altro, ossia un
            numero, che sono i valori desiderati.
            L'espressione (+(x.trim())) serve ad effettuare la conversione,
            eliminando gli spazi e convertendo la stringa "purificata" in un numero
            tramite l'operatore "+", che induce il motore Javascript a parsificare
            la stringa in un numero.
        */
        var numbers = text.split(",").map(x => (+(x.trim())));
        return new GoogleLocation(numbers[0], numbers[1]);
    }
}

/** Represents a New-Group informations*/
class GroupNew extends GroupBasic {
//it's the API payload
    constructor(creator = "", groupName = "", location = null, tags = null,
                groupDate = null, description = "", creatorId = null) {
        super(creator, groupName, location, tags);
        this.groupDate = groupDate;
        this.description = description;
        this.creatorId = creatorId;
    }
}

/** Represents a search-Group informations*/
class GroupSearch extends GroupBasic {
//it's the API response
    constructor(isSearchingByCreator = true, creator = "", groupName = "", location = null, tags = [],
                dateStartRange = null, dateEndRange = null, maxDistance = "0.0") {
        super(creator, groupName, location, tags);
        this.isSearchingByCreator = isSearchingByCreator;
        this.creatorMember = creator;
        this.dateStartRange = dateStartRange;
        this.dateEndRange = dateEndRange;
        this.maxDistance = maxDistance; // only for search o.o
    }

    setCreator(creator) {
        this.creator = creator;
        this.creatorMember = creator;
    }

    setCreatorMember(creatorMember) {
        this.creator = creatorMember;
        this.creatorMember = creatorMember;
    }

    setIsSearchingByCreator(isSearchingByCreator) {
        this.isSearchingByCreator = isSearchingByCreator;
    }

    setGroupName(groupName) {
        this.groupName = groupName;
    }

    setDateStart(date) {
        this.dateStartRange = date;
    }

    setDateEnd(date) {
        this.dateStartEnd = date;
    }

    resetFields() {
        this.isSearchingByCreator = true;
        this.setCreator("");
        this.groupName = "";
        this.location = null;
        this.tags = [];
        this.dateStartRange = null;
        this.dateEndRange = null;
        this.maxDistance = "0.0";
        return this;
    }
}

class GroupFullDetail extends GroupNew {

    constructor(creator = "", groupName = "", location = null, tags = null, groupDate = null, description = "", creatorId = null//
        , groupId = null, members = []) {
        super(creator, groupName, location, tags, groupDate, description, creatorId);
        this.groupId = groupId;
        this.members = members;
    }
}

class MessageQueryPayload {
    constructor(groupId = null, dateStart = null) {
        this.groupId = groupId;
        this.dateStart = dateStart;
    }
}

class MessageNewPayload {
    constructor(userId = null, groupId = null, testo = "") {
        this.userId = userId;
        this.groupId = groupId;
        this.testo = testo;
    }
}
