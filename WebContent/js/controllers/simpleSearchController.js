angular.module('LearnversationApp')
.controller('simpleSearchCtrl', ['languagesFactory', 'levelsFactory',
	'countriesFactory', 'usersFactory', 'searchParametersFactory','$route',
	'$location', '$scope',
	function(languagesFactory, levelsFactory,
			countriesFactory, usersFactory, searchParametersFactory, 
			$route, $location, $scope) {
	var vm = this;
	vm.path = $location.path();
	vm.user = null;
	vm.languages = [];
	vm.levels = [];
	vm.countries = [];
	vm.selectedLanguage = "";
	vm.selectedSpeakingLevel = "";
	vm.selectedWritingLevel = "";
	vm.selectedListeningLevel = "";
	vm.selectedReadingLevel = "";
	vm.selectedCountry = "";
	vm.selectedExchangeType1 = "";
	vm.selectedExchangeType2 = "";
	vm.selectedUsername = "";
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
		getCountries: function() {
			countriesFactory.getAllCountries()
				.then(function(response){
				console.log("Getting all countries:", response);
					vm.countries = response;
				}, function (response){
					console.log("Error obteniendo los paises");
				})
		},
		searchUsers : function() {
			searchParametersFactory.setSelectedLanguage(vm.selectedLanguage);	
			searchParametersFactory.setSelectedSpeakingLevel(vm.selectedSpeakingLevel);		
			searchParametersFactory.setSelectedWritingLevel(vm.selectedWritingLevel);			
			searchParametersFactory.setSelectedListeningLevel(vm.selectedListeningLevel);	
			searchParametersFactory.setSelectedReadingLevel(vm.selectedReadingLevel);															
			searchParametersFactory.setSelectedCountry(vm.selectedCountry);	
			searchParametersFactory.setSelectedExchangeType1(vm.selectedExchangeType1);
			searchParametersFactory.setSelectedExchangeType2(vm.selectedExchangeType2);
			searchParametersFactory.setSelectedUsername(vm.selectedUsername);
						
			$location.path("/searchResult");	
		},
		initSearchParameters : function() {
			vm.selectedLanguage = searchParametersFactory.getSelectedLanguage();
			vm.selectedSpeakingLevel = searchParametersFactory.getSelectedSpeakingLevel();	
			vm.selectedWritingLevel = searchParametersFactory.getSelectedWritingLevel();
			vm.selectedListeningLevel = searchParametersFactory.getSelectedListeningLevel();
			vm.selectedReadingLevel = searchParametersFactory.getSelectedReadingLevel();
			vm.selectedCountry = searchParametersFactory.getSelectedCountry();
			vm.selectedExchangeType1 = searchParametersFactory.getSelectedExchangeType1();
			vm.selectedExchangeType2 = searchParametersFactory.getSelectedExchangeType2();
			vm.selectedUsername = searchParametersFactory.getSelectedUsername();			
		}
	}
	vm.functions.getLanguages();
	vm.functions.getLevels();
	vm.functions.getCountries();
	vm.functions.initSearchParameters();
}])

