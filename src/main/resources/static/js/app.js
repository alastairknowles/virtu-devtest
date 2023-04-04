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

                if ($location.path() === '/login') {
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
            return $http.post('api/login',
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

        function basicAuth() {
            let user = $sessionStorage.loggedUser;
            return window.btoa('Basic ' + user.username + ':' + user.password)
        }

        return {
            login: login,
            logout: logout,
            isLoggedIn: isLoggedIn,
            basicAuth: basicAuth
        }
    });

    app.service('NoteService', function ($http, AuthService) {
        function createNote(note) {
            return $http.post('api/notes', note, {
                headers: {'Authorization': AuthService.basicAuth()}
            })
        }

        function getNotes() {
            return $http.get('api/notes', {
                headers: {'Authorization': AuthService.basicAuth()}
            })
        }

        function updateNote(note) {
            return $http.put('api/notes/' + note.id, note, {
                headers: {'Authorization': AuthService.basicAuth()}
            })
        }

        return {
            createNote: createNote,
            getNotes: getNotes,
            updateNote: updateNote
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
                    $location.path('/');
                }, function (error) {
                    console.log(error);
                    $scope.invalidCreds = true;
                });
            };
        });

    app.controller('notesController',
        function ($scope, AuthService, NoteService) {
            NoteService.getNotes().then(function (response) {
                $scope.notes = response.data.items
            })

            $scope.isEditCreateView = false;

            $scope.newNoteView = function () {
                $scope.note = {}
                $scope.isEditCreateView = true;
            };

            $scope.viewNote = function (note) {
                $scope.note = note;
                $scope.isEditCreateView = true;
            }

            $scope.cancelEditCreate = function () {
                $scope.note = null;
                $scope.isEditCreateView = false;
            }

            $scope.editCreate = function (note) {
                (note.id
                    ? NoteService.updateNote(note)
                    : NoteService.createNote(note))
                .then(function (response) {
                    $scope.note.id = response.data.id
                    return NoteService.getNotes()
                })
                .then(function (response) {
                    $scope.notes = response.data.items
                })
            }
        });

})();