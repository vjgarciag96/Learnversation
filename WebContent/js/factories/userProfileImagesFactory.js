angular.module('LearnversationApp').factory("userProfileImagesFactory",function($http) {
			var url = 'https://localhost:8443/Learnversation/rest/profileimage/';
			var userProfileImageResourceInterface = {
				getAllProfileImages : function() {
					return $http.get(url).then(function(response) {
						return response.data;
					});
				},
				getProfileImageById : function(imageId) {
					var urlWithId = url + imageId;
					return $http.get(urlWithId).then(function(response){
						return response.data;
					});
				}
			}
		return userProfileImageResourceInterface;
})