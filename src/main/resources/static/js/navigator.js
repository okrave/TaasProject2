
class MapsGoogleManager {
	constructor(divName = "") {
		this.divName = divName;
		this.div = null;
		this.options = {
			enableHighAccuracy: true,
			timeout: 5000,
			maximumAge: 0
		};
		this.mapInitOptions = {
			center: null, // deve essere impostata
			gestureHandling: 'cooperative',
			zoom: 16
		};
		this.mapsImage = {
			url: "./logo.png",
			size: new google.maps.Size(40, 40),
			origin: new google.maps.Point(0, 0),
			anchor: new google.maps.Point(20, 20),
			scaledSize: new google.maps.Size(40, 40)
		};
		this.position = { lat: 0.0, lng: 0.0 };
		this.updaterIntervalID = null;
		this.map = null;
		this.marker = null;

	}

	getPosition(){
		return this.position;
	}

	getOptionsMapsInit(){
		return this.options;
	}

	isUpdating(){
		return this.updaterIntervalID != null;
	}

	//

	start(){
		if(this.resetMapDiv()){
			//this.recalculateGpsPosition();
			this.updateMap();
			this.restartGpsUpdater(5000);
			setTimeout(
				(function (thisIns) {
					const thisInstance = thisIns;
					return function(){
						thisInstance.suspendGpsUpdater()
					};
				})(this)
				, 5500);
		} else console.log("ERROR: cannot find a component to attach Google Maps's map");
	}

	suspendGpsUpdater(){
		if(this.isUpdating()){
			clearInterval(this.updaterIntervalID);
			this.updaterIntervalID = null;
		}
	}

	restartGpsUpdater(interval = 1000){
		const thisManager = this;
		this.suspendGpsUpdater();
		if(typeof interval !== 'number' || interval < 1000)
			interval = 1000;
		this.updaterIntervalID = setInterval(function(){ thisManager.updateMap(); }, interval);
	}


	errorGpsHandler(err){
		console.log("Gps Error" + err.code + "\n\t" + err);
	}

	resetMapDiv(){
		var divSetted;
		divSetted = false;
		if(this.divName != null && this.divName !== undefined && this.divName != "" && this.divName !== ""){
			this.div = document.getElementById(this.divName);
			divSetted = true;
		}
		if( (!divSetted) || this.div == null || this.div === undefined){
			this.divName = 'map';
			this.div = document.getElementById(this.divName);
		}
		return this.div != null && this.div !== undefined;
	}

	createSaveCurrentPositionFunction(thisIn){
		const thisInstance = thisIn;
		return function(pos){
			if( thisInstance == null){
				console.log("THIS IS NULL?? HOW?? ");
				return;
			}
			thisInstance.position.lat = pos.coords.latitude;
			thisInstance.position.lng = pos.coords.longitude;
			console.log(JSON.stringify(thisInstance.position));
		};
	}

	recalculateGpsPosition(){
		//navigator e' disponibile globalmente?
		if (navigator && navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(this.createSaveCurrentPositionFunction(this), this.errorGpsHandler, this.options);
		} else {
			this.div.innerHTML = "Geolocation is not supported by this browser.";
		}
	}

	updateMap(){
		this.recalculateGpsPosition();
		if(this.position.lat && this.position.lng){
			if(!this.position.map){
				this.setMap();
			} else {
				this.marker.setPosition(this.position);
				this.map.panTo(this.position);
			}
		}
	}

	setMap(){
		this.mapInitOptions.center = this.position;
		this.map = new google.maps.Map(this.div, this.mapInitOptions);

		this.marker = new google.maps.Marker({
			position: this.position,
			title:"Posizione attuale",
			animation: google.maps.Animation.DROP,
			icon: this.mapsImage
		});

		var infoWindow= new google.maps.InfoWindow({
			content: '<h1>' + 'Posizione trovata' + '<h1>'
		});

		const thisManager = this;
		this.marker.addListener('click',function(){
			infoWindow.open(thisManager.map, thisManager.marker);
			thisManager.map.panTo(thisManager.marker.getPosition());
			thisManager.map.setZoom(thisManager.map.getZoom() + 1);
		});
		this.marker.setMap(this.map);
	}
}


//

//


function createGooleMapsManager(divName = 'map'){
	const gmManager = new MapsGoogleManager(divName);
	gmManager.start();
	return gmManager;
}