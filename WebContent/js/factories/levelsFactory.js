angular.module('LearnversationApp').factory("levelsFactory",['$http', function($http) {
			var url = 'https://localhost:8443/Learnversation/rest/levels/';
			var levelResourceInterface = {
				getAllLevels : function() {
					return $http.get(url).then(function(response) {
						return response.data;
					});
				},
				getLevelById : function(levelId) {
					var urlWithId = url + levelId;
					return $http.get(urlWithId).then(function(response){
						return response.data;
					});
				}
			}
		return levelResourceInterface;
}])