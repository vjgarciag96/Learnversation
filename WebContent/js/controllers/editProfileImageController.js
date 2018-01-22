angular.module('LearnversationApp')
	.controller('editProfileImageCtrl', [
		'usersFactory', 'userProfileImagesFactory',
		'$location',
		function(usersFactory, userProfileImagesFactory, 
				$location){
		var vm = this;		
		vm.loggedUser = {};
		vm.images = [];
		vm.functions = {
			getProfileImages : function(){
				userProfileImagesFactory.getAllProfileImages().
				then(function(response){
					vm.images = response;
					console.log("Getting user images", response);
				}, function(response){
					console.log("Error obteniendo las imágenes de los users")
			})
			},
			getUserInSession : function(){
				usersFactory.getLoggedUser().
				then(function(response){
					vm.loggedUser = response;
					}, function(response){
					console.log("Error obteniendo el usuario logueado", response.status);
				})
			},
			changeProfileImage : function(newImageId){
				console.log("nuevo id image", newImageId);
				vm.loggedUser.idi = newImageId;
				usersFactory.updateUser(vm.loggedUser)
					.then(function(response){
						console.log("Imagen de perfil actualizada con exito", response);
						$location.path("/userProfile/myProfile");
					}, function(response){
						console.log("Error actualizando la imagen de perfil", response);
					})
			}
		}
		vm.functions.getUserInSession();
		vm.functions.getProfileImages();
		}
	])