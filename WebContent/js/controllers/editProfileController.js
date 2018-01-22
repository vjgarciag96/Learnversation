angular.module('LearnversationApp')
	.controller('editProfileCtrl', [
		'usersFactory', 'countriesFactory',
		'$location',
		function(usersFactory, countriesFactory, $location){
		var vm = this;		
		vm.countries = [];
		vm.loggedUser = {};
		vm.exchangeTypeFirst;
		vm.exchangeTypeSecond;
		vm.birthDateFormated;
		vm.errorMessage = "";		
		vm.functions = {
			getUserInSession : function(){
				usersFactory.getLoggedUser().
				then(function(response){
					vm.loggedUser = response;
					console.log("Obteniendo user ", vm.loggedUser.username);
					vm.exchangeTypeFirst = vm.loggedUser.exchangeTypes.includes("Cara a cara");
					vm.exchangeTypeSecond = vm.loggedUser.exchangeTypes.includes("A través de internet");
					vm.birthDateFormated = new Date(vm.loggedUser.birthDate);
					console.log("Fecha ", vm.loggedUser.birthDate);
				}, function(response){
					console.log("Error obteniendo el usuario logueado", response.status);
				})
			},
			getCountries : function(){
				countriesFactory.getAllCountries()
					.then(function(response){
						vm.countries = response;
					}, function(response){
						console.log("error obteniendo los paises", response);
					})
			},
			confirmEdition : function(){
				if(vm.exchangeTypeFirst == true && vm.exchangeTypeSecond == true)
					vm.loggedUser.exchangeTypes = "Cara a cara, A través de internet";
				else if(vm.exchangeTypeFirst == true)
					vm.loggedUser.exchangeTypes = "Cara a cara";
				else if(vm.exchangeTypeSecond == true)
					vm.loggedUser.exchangeTypes = "A través de internet";
				else 
					vm.loggedUser.exchangeTypes = "No tiene preferencias";
				vm.loggedUser.birthDate = vm.birthDateFormated.toISOString().slice(0, 10);				
				usersFactory.updateUser(vm.loggedUser)
					.then(function(response){
						console.log("Usuario actualizado con éxito");
						$location.path("/userProfile/myProfile");						
					}, function(response){
						console.log("Error actualizando el usuario");
						vm.errorMessage = response.data.userMessage;
					})
			}
		}
		vm.functions.getUserInSession();
		vm.functions.getCountries();
		}
	])