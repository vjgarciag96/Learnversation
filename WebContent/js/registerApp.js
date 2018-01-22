angular.module('RegisterApp', ["ngRoute", "angularCSS", "angular-encryption"]).
config(function($routeProvider) {
	$routeProvider
	.when("/", {
		controller : "registerProfileCtrl",
		controllerAs : "vm",
		templateUrl : "register.html",
		css : "../css/InputsWrapper.css"
	})
});