angular.module('LearnversationApp').factory("countriesFactory",['$http', function($http) {
			var url = 'https://localhost:8443/Learnversation/rest/countries/';
			var countryResourceInterface = {
				getAllCountries : function() {
					return $http.get(url).then(function(response) {
						return response.data;
					});
				},
				getCountryById : function(countryId) {
					var urlWithId = url + countryId;
					return $http.get(urlWithId).then(function(response){
						return response.data;
					});
				}
			}
		return countryResourceInterface;
}])