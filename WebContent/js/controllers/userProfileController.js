angular.module('LearnversationApp')
.controller('userProfileCtrl', ['usersFactory', 'usersLanguagesFactory', 
	'languagesFactory', 'levelsFactory', 'userProfileImagesFactory',
	'commentsFactory', '$route', '$location', '$scope', '$routeParams',
	function(usersFactory, usersLanguagesFactory,
			languagesFactory, levelsFactory, 
			userProfileImagesFactory, commentsFactory, $route, 
			$location, $scope, $routeParams) {
	var vm = this;
	vm.loggedUser = {};
	vm.visitedUser = {};
	vm.images = [];
	vm.languages = [];
	vm.levels = [];
	vm.users = [];
	vm.userIdFromPath = "";
	vm.userLanguages = {};
	vm.comments = {};
	vm.errorMessage = "";
	vm.messageState = "";
	vm.boxComment = {};
	vm.boxComment.idc = "";
	vm.boxComment.receiver = "";
	vm.boxComment.sender = "";
	vm.boxComment.text = "";
	vm.boxComment.timeStamp = "";
	vm.userPath = "";	
	vm.editing = false;
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
			getProfileImages : function(){
				userProfileImagesFactory.getAllProfileImages().
				then(function(response){
					vm.images = response;
					console.log("Getting user images", response);
				}, function(response){
					console.log("Error obteniendo las imágenes de los users")
			})
			},
			getAllUsers : function(){
				usersFactory.getAllUsers()
					.then(function(response){
						vm.users = response;
						console.log("Todos los usuarios", vm.users);
					}, function(response){
						console.log("Error obteniendo todos los usuarios");
					})
			}
			,getComments : function(){
				var userId = "";
				if(vm.editing == true)
					userId = vm.loggedUser.idu;
				else
					userId = vm.visitedUser.idu;
				commentsFactory.getAllCommentsByReceiver(userId)
					.then(function(response){
						vm.comments = response;
						console.log("comments : ", vm.comments);
					}, function(response){
						console.log("error getting comments", response.status);
					})
			},
			getUserInSession : function(){
				usersFactory.getLoggedUser().
				then(function(response){
					vm.loggedUser = response;
					vm.functions.getUserPath();
					console.log("Obteniendo user ", vm.loggedUser.username);
				}, function(response){
					console.log("Error obteniendo el usuario logueado", response.status);
				})
			},
			getUserPath : function(){
				vm.userPath = $location.path();
				if(vm.userPath == "/userProfile/myProfile"){
					vm.editing = true;
					vm.functions.getUserLanguages();	
					vm.functions.getComments();
				}else{
					vm.userIdFromPath = $routeParams.ID;					
					console.log("string parseado", vm.userIdFromPath);
					vm.functions.getUserVisited();
				}
				console.log("userPath", vm.userPath);
			},
			getUserVisited : function(){
				usersFactory.getUserById(vm.userIdFromPath)
					.then(function(response){
						vm.visitedUser = response;
						vm.functions.getUserLanguages();
						vm.functions.getComments();
					}, function(response){
						console.log("error obteniendo el usuario visitado");
					})
			},
			getUserLanguages : function(){
				var userId = "";
				if(vm.editing == true)
					userId = vm.loggedUser.idu;
				else
					userId = vm.visitedUser.idu;
				usersLanguagesFactory.getUserLanguagesByUser(userId)
					.then(function(response){
						vm.userLanguages = response;
						console.log("lenguajes del user :", vm.userLanguages);
					}, function(response){
						console.log("error getting users language", response.code);
					})
			},
			sendComment : function(){
				vm.boxComment.receiver = vm.visitedUser.idu;
				vm.boxComment.sender = vm.loggedUser.idu;
				commentsFactory.addComment(vm.boxComment)
					.then(function(response){
						vm.functions.getComments(); 
						vm.messageState = "Mensaje enviado con éxito";
						vm.boxComment.text = "";
						vm.errorMessage = "";
					}, function(response){
						console.log("error sending comment", response);
						vm.messageState = "";
						vm.errorMessage = response.data.userMessage;
					})
			},
			
	}
	vm.functions.getLanguages();
	vm.functions.getLevels();
	vm.functions.getAllUsers();
	vm.functions.getProfileImages();
	vm.functions.getUserInSession();
}])