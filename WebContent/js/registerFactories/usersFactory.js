angular.module('RegisterApp')
.factory("usersFactory",['$http', 'sha256', function($http, sha256) {
			var url = 'https://localhost:8443/Learnversation/rest/users/';
			var userResourceInterface = {
				getAllUsers : function() {
					return $http.get(url).then(function(response) {
						return response.data;
					});
				},
				getUserById : function(userId) {
					var urlWithId = url + userId;
					return $http.get(urlWithId).then(function(response){
						return response.data;
					});
				},
				getLoggedUser : function(){
					var urlWithPath = url + 'logged';
					return $http.get(urlWithPath).then(function(response){
						return response.data;
					});
				},
				searchUsers : function(country, exchangeTypeFirst, exchangeTypeSecond, username){
					if(country === undefined || country === null)
						country = "";
					if(exchangeTypeFirst === undefined || exchangeTypeFirst === null)
						exchangeTypeFirst = "";
					if(exchangeTypeSecond === undefined || exchangeTypeSecond === null)
						exchangeTypeSecond = "";
					if(username === undefined || username === null)
						username = "";				
					var urlWithParams = url + 'search?country=' + country 
						+ '&exchangeType1=' + exchangeTypeFirst
						+ '&exchangeType2=' + exchangeTypeSecond
						+ '&username=' + username;
					console.log("url: "+urlWithParams);
					return $http.get(urlWithParams).then(function(response){
						return response.data;
					})
				},
				updateUser : function(user) {
					var urlWithId = url + user.idu;
					console.log("la url de editar el user", urlWithId);
					console.log("y el user :D", user);
					return $http.put(urlWithId, user).then(function(response){
						return response.status;
					});
				},
				addUser : function(user) {
					var newUser = JSON.parse(JSON.stringify(user));
					newUser.password = sha256.convertToSHA256(user.password, "").toUpperCase(); 									
					return $http.post(url, newUser).then(function(response){
						return response.status;
					});
				},
				deleteUser : function(userId){
					var urlWithId = url + userId;
					return $http.delete(urlWithId)
						.then(function(response){
							return response.status;
						});			
				}
			}
		return userResourceInterface;
}])