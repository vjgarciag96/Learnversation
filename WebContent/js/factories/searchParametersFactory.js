angular.module('LearnversationApp').factory("searchParametersFactory",['$http', function($http) {
			var selectedLanguage = "";
			var selectedSpeakingLevel = "";
			var selectedWritingLevel = "";
			var selectedListeningLevel = "";
			var selectedReadingLevel = "";
			var selectedCountry = "";
			var selectedExchangeType1 = "";
			var selectedExchangeType2 = "";
			var selectedUsername = "";
			var searchParametersInterface = {
				setSelectedLanguage : function(language){
					selectedLanguage = language;
				},
				getSelectedLanguage : function(){
					return selectedLanguage;
				},
				setSelectedSpeakingLevel : function(speakingLevel){
					selectedSpeakingLevel = speakingLevel;
				},
				getSelectedSpeakingLevel : function(){
					return selectedSpeakingLevel;
				},
				setSelectedWritingLevel : function(writingLevel){
					selectedWritingLevel = writingLevel;
				},
				getSelectedWritingLevel : function(){
					return selectedWritingLevel;
				},
				setSelectedListeningLevel : function(listeningLevel){
					selectedListeningLevel = listeningLevel;
				},
				getSelectedListeningLevel : function(){
					return selectedListeningLevel;
				},
				setSelectedReadingLevel : function(readingLevel){
					selectedReadingLevel = readingLevel;
				},
				getSelectedReadingLevel : function(){
					return selectedReadingLevel;
				},
				setSelectedCountry : function(country){
					selectedCountry = country;
				},
				getSelectedCountry : function(){
					return selectedCountry;
				},
				setSelectedExchangeType1 : function(exchangeType1){
					selectedExchangeType1 = exchangeType1;
				},
				getSelectedExchangeType1 : function(){
					return selectedExchangeType1;
				},
				setSelectedExchangeType2 : function(exchangeType2){
					selectedExchangeType2 = exchangeType2;
				},
				getSelectedExchangeType2 : function(){
					return selectedExchangeType2;
				},
				setSelectedUsername : function(username){
					selectedUsername = username;
				},
				getSelectedUsername : function(){
					return selectedUsername;
				}
			}
		return searchParametersInterface;
}])