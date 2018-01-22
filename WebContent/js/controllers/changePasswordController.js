angular.module('LearnversationApp')
	.controller('changePasswordCtrl', [
		'sha256',
		'usersFactory', 
		'$location',
		function(sha256, usersFactory, $location){
		var vm = this;	
		vm.loggedUser = {};
		vm.currentPassword = "";
		vm.newPassword = "";
		vm.repeatedNewPassword = "";
		vm.errorMessage = "";
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
				changePassword : function(){
					var encrypted = sha256.convertToSHA256(vm.currentPassword, "").toUpperCase();
					console.log("encrypted", encrypted);
					console.log("vm.loggedUser.password", vm.loggedUser.password);
					if(encrypted == vm.loggedUser.password){
						if(vm.newPassword == vm.repeatedNewPassword){
							vm.loggedUser.password = sha256.convertToSHA256(vm.newPassword, "").toUpperCase();							
							usersFactory.updateUser(vm.loggedUser).
							then(function(response){
								console.log("contraseña actualizada con éxito", response);
								$location.path("/userProfile/myProfile");
							}, function(response){
								console.log("error actualizando el user", response);
							})
						}
						else
							vm.errorMessage = "Las nuevas contraseñas no coinciden";
					}
					else
						vm.errorMessage = "La contraseña actual no es correcta";
					
				}
		};
		vm.functions.getUserInSession();
		}
		]);