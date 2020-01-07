var app = angular.module('myApps', []);
app.controller('UserCRUDCtrl', 
  function ($scope , UserCRUDService) {
     
      $scope.addUser = function () {
        if ($scope.user != null && $scope.user.name) {
            UserCRUDService.addUser($scope.user.name, $scope.user.email)
              .then (function success(response){
                  $scope.message = 'User added!';
                  $scope.errorMessage = '';
              },
              function error(response){
                  $scope.errorMessage = 'Error adding user!';
                  $scope.message = '';
            });
        }
        else {
            $scope.errorMessage = 'Please enter a name!';
            $scope.message = '';
        }
    };

    $scope.findUser = function () {
        if ($scope.user != null && $scope.user.name) {
            UserCRUDService.findUser($scope.user.name, $scope.user.email)
              .then (function success(response){
                  $scope.message = 'User added!';
                  $scope.errorMessage = '';
              },
              function error(response){
                  $scope.errorMessage = 'Error adding user!';
                  $scope.message = '';
            });
        }
        else {
            $scope.errorMessage = 'Please enter a name!';
            $scope.message = '';
        }
    };

    $scope.getAllUsers = function () {
        UserCRUDService.getAllUsers()
          .then(function success(response) {
              $scope.users = response.data;
              $scope.message='';
              $scope.errorMessage = '';
          },
          function error (response) {
              $scope.message='';
              $scope.errorMessage = 'Error getting users!';
          });
    }

});