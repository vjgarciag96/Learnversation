angular.module('LearnversationApp')
	.controller('deleteCommentCtrl', [
		'commentsFactory', 'usersFactory', '$location',
		'$routeParams',
		function(commentsFactory, usersFactory, $location, $routeParams){
		var vm = this;
		vm.comment = {};
		vm.users = {};
		vm.commentId = "";
		vm.functions = {
			getCommentToDelete : function(){
				vm.commentId = $routeParams.ID;
				console.log("id del comentario", vm.commentId);
				commentsFactory.getCommentByCommentId(vm.commentId)
					.then(function(response){
						vm.comment = response;
						console.log("Comentario: ", vm.comment);
					}, function(response){
						console.log("Error obteniendo el comentario para eliminar", response.status)
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
			confirmDelete : function(){
				commentsFactory.deleteComment(vm.comment.idc)
					.then(function(response){
						console.log("Eliminado el comentario con exito", response);
						$location.path("/userProfile/"+vm.comment.receiver);
					}, function(response){
						console.log("Error eliminando el comentario", response.status);
					})
			}
		}
		vm.functions.getAllUsers();
		vm.functions.getCommentToDelete();
		}
	])