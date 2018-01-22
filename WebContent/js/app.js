angular.module('LearnversationApp', [ "ngRoute", "angularCSS", "angular-encryption" ]).config(
		function($routeProvider) {
			$routeProvider.when("/", {
				controller : "simpleSearchCtrl",
				controllerAs : "vm",
				templateUrl : "simpleSearch.html",
				css : "../css/InputsWrapper.css",
				resolve : {
					delay : function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 100);
						return delay.promise;
					}
				}
			}).when("/searchResult", {
				controller : "searchResultCtrl",
				controllerAs : "vmsearch",
				templateUrl : "searchResult.html",
				css : "../css/SearchResultStyle.css",
				resolve : {
					delay : function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 100);
						return delay.promise;
					}
				}
			}).when("/userProfile/:ID", {
				controller : "userProfileCtrl",
				controllerAs : "vm",
				templateUrl : "userProfile.html",
				css : "../css/DetailViewStyle.css",
				resolve : {
					delay : function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 100);
						return delay.promise;
					}
				}
			}).when("/editComment/:ID", {
				controller : "editCommentCtrl",
				controllerAs : "vm",
				templateUrl : "editComment.html",
				css : "../css/InputsWrapper.css"
			}).when("/deleteComment/:ID", {
				controller : "deleteCommentCtrl",
				controllerAs : "vm",
				templateUrl : "deleteComment.html",
				css : "../css/InputsWrapper.css"
			}).when("/editProfile", {
				controller : "editProfileCtrl",
				controllerAs : "vm",
				templateUrl : "editProfile.html",
				css : "../css/InputsWrapper.css"
			}).when("/addLanguage", {
				controller : "addLanguageCtrl",
				controllerAs : "vm",
				templateUrl : "addLanguage.html",
				css : "../css/InputsWrapper.css"
			}).when("/editLanguage/:ID", {
				controller : "editLanguageCtrl",
				controllerAs : "vm",
				templateUrl : "editLanguage.html",
				css : "../css/InputsWrapper.css"
			}).when("/deleteLanguage/:ID", {
				controller : "deleteLanguageCtrl",
				controllerAs : "vm",
				templateUrl : "deleteLanguage.html",
				css : "../css/InputsWrapper.css"
			}).when("/deleteProfile", {
				controller : "deleteProfileCtrl",
				controllerAs : "vm",
				templateUrl : "deleteProfile.html",
				css : "../css/InputsWrapper.css"
			}).when("/editProfileImage", {
				controller : "editProfileImageCtrl",
				controllerAs : "vm",
				templateUrl : "editProfileImage.html",
				css : "../css/ChooseImage.css"
			}).when("/changePassword", {
				controller : "changePasswordCtrl",
				controllerAs : "vm",
				templateUrl : "changePassword.html",
				css : "../css/InputsWrapper.css"
			}).when("/languageReferences", {
				templateUrl : "languageReferences.html",
				css : "../css/LanguageReferencesStyle.css"
			})

		});
