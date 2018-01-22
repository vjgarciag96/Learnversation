angular.module('LearnversationApp').controller(
		'searchResultCtrl',
		[		'languagesFactory', 
				'levelsFactory',
				'usersFactory',
				'searchParametersFactory',
				'userProfileImagesFactory',
				'usersLanguagesFactory',
				'$route',
				'$location',
				function(languagesFactory, levelsFactory, 
						usersFactory, searchParametersFactory,
						userProfileImagesFactory,
						usersLanguagesFactory, $route, $location) {
					var vm = this;
					vm.country;
					vm.countryName;
					vm.exchangeType1;
					vm.exchangeType2;
					vm.username;
					vm.exchangeTypeTextFirst = "";
					vm.exchangeTypeTextSecond = "";
					vm.language = "";
					vm.speakingLevel = "";
					vm.writingLevel = "";
					vm.listeningLevel = "";
					vm.readingLevel = "";
					vm.users = [];
					vm.images = [];
					vm.languages = [];
					vm.levels = [];
					vm.usersLanguages = [];
					vm.loggedUser = {};
					vm.currentUser = {};
					vm.index;
					vm.functions = {							
						getLanguages : function() {		
							languagesFactory.getAllLanguages()
								.then(function(response){
										console.log("Getting all languages:", response);
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
									}, function (response) {
										console.log("Error obteniendo los niveles")
							})		
						},														
						filterUsersByUserParameters : function(){
							usersFactory.searchUsers(vm.countryName, vm.exchangeTypeTextFirst,
									vm.exchangeTypeTextSecond, vm.username).then(function(response) {
								vm.users = response;
								console.log("exchangeTypeFirst :", vm.exchangeTypeTextFirst);
								console.log("exchangeTypeSecond :", vm.exchangeTypeTextSecond);
								console.log("Getting users", response);								
								vm.functions.getUserInSession();
							}, function(response) {
								console.log("Error obteniendo los usuarios");
							})
						},
						getProfileImages : function(){
							userProfileImagesFactory.getAllProfileImages().
							then(function(response){
								vm.images = response;
								console.log("Getting user images", response);
							}, function(response){
								console.log("Error obteniendo las imágenes de los users")
						})
						},
						checkFilterOnNextUser : function(){
							vm.currentUser = vm.users[vm.index];													
							usersLanguagesFactory.getUserLanguage(vm.currentUser.idu, vm.language, 
								vm.speakingLevel, vm.writingLevel, vm.listeningLevel, vm.readingLevel)
									.then(function(response){	
										if(vm.currentUser.idu == vm.loggedUser.idu || response == ""){											
											vm.users.splice(vm.index, 1);
											console.log("te estas cargando a ", vm.currentUser.username);
										}
										else{
											//vm.usersLanguages.push(response);
											vm.index++;
										}
										if(vm.index < vm.users.length)
											vm.functions.checkFilterOnNextUser();
										else{
											vm.index = 0;
											vm.functions.getNextUserLanguages();
										}
									}, function(response){
										console.log("error filtering users by languages", response.code);
									})							
						},
						getNextUserLanguages : function(){
							vm.currentUser = vm.users[vm.index];
							usersLanguagesFactory.getUserLanguagesByUser(vm.currentUser.idu)
								.then(function(response){
									vm.usersLanguages = vm.usersLanguages.concat(response);
									if(vm.index < vm.users.length-1){
										vm.index++;
										vm.functions.getNextUserLanguages();
									}
									else
										vm.index = 0;
								}, function(reponse){
									console.log("error getting users languages");
								})
						}
						,filterUsersByLanguages : function(){
							console.log("tamaño de lista users", vm.users.length);
							vm.index = 0;
							vm.functions.checkFilterOnNextUser();
							console.log("Después del for, users languages", vm.usersLanguages);
							console.log("Después del for, users", vm.users);							
						},
						getUserInSession : function(){
							usersFactory.getLoggedUser().
							then(function(response){
								vm.loggedUser = response;
								console.log("Obteniendo user ", vm.loggedUser.username);
								vm.functions.filterUsersByLanguages();
							}, function(response){
								console.log("Error obteniendo el usuario logueado", response.status);
							})
						},
						getSearchParameters : function(){
							vm.country = searchParametersFactory
							.getSelectedCountry();
							
							if(vm.country !== null && vm.country !== undefined)													
								vm.countryName = vm.country.name;
							
							vm.exchangeType1 = searchParametersFactory
								.getSelectedExchangeType1();
							vm.exchangeType2 = searchParametersFactory
								.getSelectedExchangeType2();
							
							vm.username = searchParametersFactory
								.getSelectedUsername();						
							
							if (vm.exchangeType1 == true) {
								vm.exchangeTypeTextFirst = "Cara a cara";
							}
							if (vm.exchangeType2 == true) {
								vm.exchangeTypeTextSecond = "A través de internet";
							}						
							
							vm.language = searchParametersFactory.getSelectedLanguage();
							if(vm.language === undefined || vm.language === null)
								vm.language = "";
							
							vm.speakingLevel = searchParametersFactory.getSelectedSpeakingLevel();
							if(vm.speakingLevel === undefined || vm.speakingLevel === null)
								vm.speakingLevel = "";
							
							vm.writingLevel = searchParametersFactory.getSelectedWritingLevel();
							if(vm.writingLevel === undefined || vm.writingLevel === null)
								vm.writingLevel = "";
							
							vm.listeningLevel = searchParametersFactory.getSelectedListeningLevel();
							if(vm.listeningLevel === undefined || vm.listeningLevel === null)
								vm.listeningLevel = "";
							
							vm.readingLevel = searchParametersFactory.getSelectedReadingLevel();	
							if(vm.readingLevel === undefined || vm.readingLevel === null)
								vm.readingLevel = "";
							console.log("------Country------", vm.countryName);
						},
						filterUsers : function(){	
							vm.functions.filterUsersByUserParameters();
						}
						,listUsers : function() {		
							vm.functions.getLanguages();
							vm.functions.getLevels();
							vm.functions.getSearchParameters();								
							vm.functions.getProfileImages();
							vm.functions.filterUsers();												
						},
						refilterUsers : function(){
							vm.countryName = "";
							vm.exchangeTypeTextFirst = "";
							vm.exchangeTypeTextSecond = "";
							vm.usersLanguages = [];
							vm.functions.getSearchParameters();
							vm.functions.filterUsers();
						}
					}
					vm.functions.listUsers();
				} ])