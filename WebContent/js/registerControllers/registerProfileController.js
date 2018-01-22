angular.module('RegisterApp')
	.controller('registerProfileCtrl', ['usersFactory', 
		'countriesFactory',
		'$location', '$window', 'sha256',
		function(usersFactory, countriesFactory, $location, $window, sha256){
		var vm = this;
		vm.user = {};
		vm.countries = [];
		vm.repeatedPassword;
		vm.exchangeTypeFirst;
		vm.exchangeTypeSecond;
		vm.birthDateFormated;
		vm.validationError;
		vm.functions = {	
				getCountries: function() {
					countriesFactory.getAllCountries()
						.then(function(response){
						console.log("Getting all countries:", response);
							vm.countries = response;
						}, function (response){
							console.log("Error obteniendo los paises");
						})
				},		
				confirmRegistration : function(){
					if(vm.exchangeTypeFirst == true && vm.exchangeTypeSecond == true)
						vm.user.exchangeTypes = "Cara a cara, A través de internet";
					else if(vm.exchangeTypeFirst == true)
						vm.user.exchangeTypes = "Cara a cara";
					else if(vm.exchangeTypeSecond == true)
						vm.user.exchangeTypes = "A través de internet";
					else 
						vm.user.exchangeTypes = "No tiene preferencias";
					if(vm.birthDateFormated !== null && vm.birthDateFormated !== undefined)
						vm.user.birthDate = vm.birthDateFormated.toISOString().slice(0, 10);
					else
						vm.user.birthDate = "";
					if(vm.user.country == null || vm.user.country == undefined)
						vm.user.country = " ";
					console.log("Lo que llevas introducido hasta el momento", vm.user);
					if(vm.user.password == vm.repeatedPassword){
					usersFactory.addUser(vm.user)
						.then(function(response){
							console.log("usuario registrado con exito", response.status);
							$window.history.back();
						}, function(response){
							console.log("error registrando el user", response);
							vm.validationError = response.data.userMessage;
						})
					}
					else
						vm.validationError = "Las contraseñas introducidas no coinciden";
				}
		}
		vm.functions.getCountries();
	}])