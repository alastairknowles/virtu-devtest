(function () {

    let app = angular.module('notesApp',
        ['ngRoute', 'ngMaterial', 'ngStorage']);

    app.config(['$locationProvider', '$routeProvider',
        function ($locationProvider, $routeProvider) {

            $routeProvider
            .when('/', {
                templateUrl: '/partials/notes-view.html',
                controller: 'notesController'
            })
            .when('/login', {
                templateUrl: '/partials/login.html',
                controller: 'loginController',
            })
            .otherwise('/');
        }
    ]);

    app.run(['$rootScope', '$location', '$sessionStorage', 'AuthService',
        function ($rootScope, $location, $sessionStorage, AuthService) {
            $rootScope.$on('$routeChangeStart', function (event) {

                if ($location.path() === "/login") {
                    return;
                }

                if (!AuthService.isLoggedIn()) {
                    console.log('DENY');
                    event.preventDefault();
                    $location.path('/login');
                }
            });

            $rootScope.isLoggedIn = function () {
                return AuthService.isLoggedIn()
            }

            $rootScope.logout = function () {
                AuthService.logout();
                $location.path('/login');
            }
        }]);

    app.service('AuthService', function ($http, $sessionStorage) {
        function login(username, password) {
            return $http.post("api/login",
                {username: username, password: password}).then(function (user) {
                $sessionStorage.loggedUser = user;
            }, function (error) {
                $sessionStorage.loggedUser = null;
                throw error;
            })
        }

        function logout() {
            $sessionStorage.loggedUser = null;
        }

        function isLoggedIn() {
            return $sessionStorage.loggedUser != null;
        }

        return {
            login: login,
            logout: logout,
            isLoggedIn: isLoggedIn
        }
    });

    app.controller('loginController',
        function ($scope, AuthService, $location) {

            $scope.invalidCreds = false;
            $scope.login = {
                username: null,
                password: null
            };

            $scope.login = function () {
                AuthService.login($scope.login.username,
                    $scope.login.password).then(function (user) {
                    console.log(user);
                    $location.path("/");
                }, function (error) {
                    console.log(error);
                    $scope.invalidCreds = true;
                });
            };
        });

    app.controller('notesController', function ($scope) {

        $scope.isEditCreateView = false;

        $scope.newNoteView = function () {
            $scope.isEditCreateView = true;
        };

        $scope.deleteNote = function (i) {
            let prompt = confirm("Are you sure you want to delete this note?");
            if (prompt) {
                //TODO delete the note
            }
        };

        $scope.viewNote = function () {
            //TODO
        }
    });

})();