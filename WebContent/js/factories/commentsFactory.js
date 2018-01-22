angular.module('LearnversationApp').
		factory("commentsFactory",function($http) {
			var url = 'https://localhost:8443/Learnversation/rest/comments/';
			var commentResourceInterface = {
				getAllComments : function() {
					return $http.get(url).then(function(response) {
						return response.data;
					});
				},
				getCommentByCommentId : function(commentId) {
					var urlWithId = url + commentId;
					console.log("getCommentByCommentId", urlWithId);
					return $http.get(urlWithId).then(function(response){
						return response.data;
					});
				},
				getAllCommentsBySender : function(senderId) {
					var urlWithPathAndQuery = url + 'sender?id=' + senderId;
					return $http.get(urlWithPathAndQuery).then(function(response){
						return response.data;
					});
				},
				getAllCommentsByReceiver : function(receiverId) {
					var urlWithPathAndQuery = url + 'receiver?id=' + receiverId;
					return $http.get(urlWithPathAndQuery).then(function(response){
						return response.data;
					});
				},
				updateComment : function(comment) {
					var urlWithId = url + comment.idc;
					return $http.put(urlWithId, comment).then(function(response){
						return response.status;
					});
				},
				addComment : function(comment) {
					console.log("insertando comentario", comment);
					return $http.post(url, comment).then(function(response){
						return response.status;
					});
				},
				deleteComment : function(commentId){
					var urlWithId = url + commentId;
					return $http.delete(urlWithId)
						.then(function(response){
							return response.status;
						});			
				}
			}
		return commentResourceInterface;
})