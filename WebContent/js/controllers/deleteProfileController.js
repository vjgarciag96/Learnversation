angular.module('LearnversationApp')
	.controller('deleteProfileCtrl', ['sha256', 'usersFactory', 
		'$location', '$window', 
		function(sha256, usersFactory, $location, $window){
		var vm = this;
		vm.loggedUser = {};
		vm.password = "";
		vm.errorMessage = ""
		vm.functions = {
			getUserInSession : function(){
				usersFactory.getLoggedUser().
				then(function(response){
					vm.loggedUser = response;
					console.log("Obteniendo user ", vm.loggedUser.username);
				}, function(response){
					console.log("Error obteniendo el usuario logueado", response.status);
				})
			},
			confirmDelete : function(){
				console.log("no encriptada", vm.password);
				var encrypted = sha256.convertToSHA256(vm.password, "").toUpperCase();	
				console.log("passwordDeAhora", encrypted);
				console.log("la que tenia", vm.loggedUser.password);
				if(vm.loggedUser.password == encrypted){
					usersFactory.deleteUser(vm.loggedUser.idu)
					.then(function(response){
						console.log("Usuario eliminado con exito", response);
						$window.location.href = "../exchange/LogoutServlet";
					}, function(response){
						vm.errorMessage = response.data.userMessage;
						console.log("Error eliminando el user", response.status);
					})	
				}
				else
					vm.errorMessage = "Las contraseñas no coinciden";
			}			
		}
		vm.functions.getUserInSession();
		}
	])