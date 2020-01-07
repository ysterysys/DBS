app.service('UserCRUDService', [ '$http', function($http) {
 
    this.getUser = function getUser(userId) {
        return $http({
            method : 'GET',
            url : 'users/' + userId
        });
    }

    this.addUser = function addUser(name, email) {
        return $http({
            method : 'POST',
            url : '/contact',
            data : {
                name : name
            }
        });
    }
    
    this.updateUser = function updateUser(id, name, email) {
        return $http({
            method : 'PATCH',
            url : 'users/' + id,
            data : {
                name : name,
                email: email
            }
        });
    }
    
    this.deleteUser = function deleteUser(id) {
        return $http({
            method : 'DELETE',
            url : 'users/' + id
        })
    }
    
    this.getAllUsers = function getAllUsers() {
        return $http({
            method : 'GET',
            url: '/getAll',
        }).then(function successCallback(response) {       
            return response;
        });
    }

} ]);


