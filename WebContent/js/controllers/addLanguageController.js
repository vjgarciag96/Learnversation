angular.module('LearnversationApp')
	.controller('addLanguageCtrl', ['usersFactory',
		'levelsFactory',
		'languagesFactory',
		'usersLanguagesFactory',
		'$location',
		function(usersFactory, levelsFactory, 
				languagesFactory, usersLanguagesFactory, 
				$location){
		var vm = this;		
		vm.loggedUser = {};
		vm.languages = {};
		vm.levels = {};
		vm.usersLanguages = {};
		vm.userLanguage = {};
		vm.userLanguage.idl = 0;
		vm.userLanguage.speakingLevel = 0;
		vm.userLanguage.writingLevel = 0;
		vm.userLanguage.readingLevel = 0;
		vm.userLanguage.listeningLevel = 0;
		vm.functions = {
			getUserInSession : function(){
				usersFactory.getLoggedUser().
				then(function(response){
					vm.loggedUser = response;
					vm.functions.getLevels();
					console.log("Obteniendo user ", vm.loggedUser.username);
				}, function(response){
					console.log("Error obteniendo el usuario logueado", response.status);
				})
			},	
			getLanguages : function() {		
				languagesFactory.getAllLanguages()
					.then(function(response){
						console.log("Getting all languages:", response);
						vm.functions.getUserLanguages();
						vm.languages = response;
					}, function(response){						
						console.log("Error obteniendo los lenguajes");
					})
			},
			getLevels : function() {
				levelsFactory.getAllLevels()
					.then(function(response){
						console.log("Getting all levels:", response);
						vm.levels = response;
						vm.functions.getLanguages();
					}, function (response) {
						console.log("Error obteniendo los niveles")
					})		
			},
			getUserLanguages : function(){
				usersLanguagesFactory.getUserLanguagesByUser(vm.loggedUser.idu)
					.then(function(response){
						vm.userLanguages = response;
						console.log("lenguajes del user :", vm.userLanguages);
						var deleted = 0;
						angular.forEach(vm.userLanguages, function(userLanguage){							
							vm.languages.splice(userLanguage.idl-deleted, 1);
							deleted++;
						});
						if(vm.languages != null && vm.languages != undefined)
							vm.userLanguage.idl = vm.languages[0].idl;
					}, function(response){
						console.log("error getting users language", response.code);
					})
			},
			startSynchronousFunctionsCalls : function(){
				vm.functions.getUserInSession();
			},
			addLanguage : function(){
				vm.userLanguage.idu = vm.loggedUser.idu;
				console.log("Nuevo lenguaje", vm.userLanguage);
				usersLanguagesFactory.addUserLanguage(vm.userLanguage)
					.then(function(response){
						console.log("Idioma añadido con éxito", response.status);
						$location.path("/userProfile/myProfile");					
					}, function(response){
						console.log("Error añadiendo el lenguaje al user", response.status);
					})
			}
		}
		vm.functions.startSynchronousFunctionsCalls();
		}
	])