angular.module('LearnversationApp').factory(
		'languagesFactory',['$http',
		function($http) {
			var url = 'https://localhost:8443/Learnversation/rest/languages/';
			var languageResourceInterface = {
				getAllLanguages : function() {
					return $http.get(url).then(function(response) {
						return response.data;
					});
				},
				getLanguageById : function(languageId) {
					var urlWithId = url + languageId;
					return $http.get(urlWithId).then(function(response) {
						return response.data;
					});
				},
				getLanguageByLanguageName : function(languageName) {
					var urlWithPathAndQuery = url + 'langname?ln = '
							+ languageName;
					return $http.get(urlWithPathAndQuery).then(
							function(response) {
								return response.data;
							});
				}
			}
			return languageResourceInterface;
		}])