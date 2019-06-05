

/*
 @param [string] url : string holding the URL where will be appended the query string compute
 @param [object|array] queryData: can be both a plain object (map string->value) or a 2D array (array of pair of strings [the key and value, rispectively])
 @return [URL] url : URL's instance holding the original url with "search" field setted with the query string, as desired
 */
function encodeQueryDataOnURL(url, queryData) {
    var newUrl;
    newUrl = new URL(url);
    newUrl.search = new URLSearchParams(queryData);
    return newUrl;
}


function tryParseJSON(aProbablyJSON) {
    if ((typeof aProbablyJSON) === 'string') { //it's still a JSON? if yes, parse it
        return JSON.parse(aProbablyJSON);
    }
    if(aProbablyJSON.hasOwnProperty('json')){
        return aProbablyJSON.json();
    }
    return aProbablyJSON;
}