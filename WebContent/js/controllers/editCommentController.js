angular.module('LearnversationApp')
	.controller('editCommentCtrl', [
		'commentsFactory', 'usersFactory', '$location', '$routeParams',
		function(commentsFactory, usersFactory, $location, $routeParams){
		var vm = this;
		vm.comment = {};
		vm.users = {};
		vm.commentId = "";
		vm.errorMessage = "";
		vm.editionMessage = "";
		vm.functions = {
			getCommentToEdit : function(){
				vm.commentId = $routeParams.ID;
				console.log("id del comentario", vm.commentId);
				commentsFactory.getCommentByCommentId(vm.commentId)
					.then(function(response){
						vm.comment = response;
						console.log("Comentario: ", vm.comment);
					}, function(response){
						console.log("Error obteniendo el comentario para editar", response.status)
					})
			},
			getAllUsers : function(){
				usersFactory.getAllUsers()
					.then(function(response){
						vm.users = response;
						console.log("Todos los usuarios", vm.users);
					}, function(response){
						console.log("Error obteniendo todos los usuarios", response.status);
					})
			},
			confirmEdition : function(){
				commentsFactory.updateComment(vm.comment)
					.then(function(response){
						console.log("Actualizado el comentario con exito", response);
						vm.editionState = "Comentario actualizado con éxito";
						$location.path("/userProfile/"+vm.comment.receiver);
					}, function(response){
						console.log("Error actualizando el comentario", response.status);
						vm.errorMessage = response.data.userMessage;
					})
			}
		}
		vm.functions.getAllUsers();
		vm.functions.getCommentToEdit();
		}
	])