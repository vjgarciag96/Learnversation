angular.module('LearnversationApp')
	.controller('editLanguageCtrl', [
		'usersLanguagesFactory', 'languagesFactory',
		'levelsFactory', 'usersFactory', 
		'$location', '$routeParams',
		function(usersLanguagesFactory, languagesFactory,
				levelsFactory, usersFactory, $location, $routeParams){
		var vm = this;
		vm.languages = {};
		vm.levels = {};
		vm.userLanguage;
		vm.loggedUser = {};
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
			getUserLanguage : function(){
				usersLanguagesFactory.getUserLanguage(vm.loggedUser.idu, $routeParams.ID, 
						-1, -1, -1, -1)
					.then(function(response){
						vm.userLanguage = response;
						console.log("lenguajes del user :", vm.userLanguage);
					}, function(response){
						console.log("error getting users language", response.code);
					})
			},
			getUserInSession : function(){
				usersFactory.getLoggedUser().
				then(function(response){
					vm.loggedUser = response;
					vm.functions.getUserLanguage();
					console.log("Obteniendo user ", vm.loggedUser.username);
				}, function(response){
					console.log("Error obteniendo el usuario logueado", response.status);
				})
			},
			confirmEdition : function(){
				usersLanguagesFactory.updateUserLanguage(vm.userLanguage)
					.then(function(response){
						console.log("lenguage actualizado con éxito");
						$location.path("/userProfile/myProfile");
					}, function(response){
						console.log("lenguaje del usuario actualizado", response.status);
					})
			}
		}
		vm.functions.getUserInSession();
		vm.functions.getLanguages();
		vm.functions.getLevels();
		}
	])