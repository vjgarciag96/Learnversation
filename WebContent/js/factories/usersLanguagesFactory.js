angular.module('LearnversationApp')
.factory("usersLanguagesFactory",['$http', function($http) {
			var url = 'https://localhost:8443/Learnversation/rest/userslanguages/';
			var userLanguagesResourceInterface = {
				getAllUserLanguages : function() {
					return $http.get(url).then(function(response) {
						return response.data;
					});
				},
				getUserLanguagesByUser : function(userId) {
					var urlWithId = url + userId;
					return $http.get(urlWithId).then(function(response){
						return response.data;
					});
				},
				getUserLanguage : function(userId, languageId, 
						speakingLevel, writingLevel, listeningLevel, 
						readingLevel) {
					var urlWithParams = url + 'params?idu=' + userId +
						'&idl=' + languageId + '&speakingLevel=' + 
						speakingLevel + '&writingLevel=' + writingLevel +
						'&listeningLevel=' + listeningLevel + 
						'&readingLevel=' + readingLevel;
					console.log("urlGetUserLanguages", urlWithParams)
					return $http.get(urlWithParams).then(function(response){
						return response.data;
					});
				},
				updateUserLanguage : function(userLanguage) {
					var urlWithPathParams = url + userLanguage.idu 
										+ '/' + userLanguage.idl;
					return $http.put(urlWithPathParams, userLanguage).then(function(response){
						return response.status;
					});
				},
				addUserLanguage : function(userLanguage) {
					return $http.post(url, userLanguage).then(function(response){
						return response.status;
					});
				},
				deleteUserLanguage : function(userId, languageId){
					var urlWithPathParams = url + userId 
										+ '/' + languageId;
					return $http.delete(urlWithPathParams)
						.then(function(response){
							return response.status;
						});			
				}
			}
		return userLanguagesResourceInterface;
}])