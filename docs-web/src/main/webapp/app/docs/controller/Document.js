'use strict';

/**
 * Document controller.
 */
App.controller('Document', function($scope, $state, Restangular) {
  /**
   * Documents table sort status.
   */
  $scope.sortColumn = 3;
  $scope.asc = false;
  $scope.offset = 0;
  $scope.currentPage = 1;
  $scope.limit = 10;
  $scope.search = '';
  
  /**
   * Load new documents page.
   */
  $scope.pageDocuments = function() {
    Restangular.one('document')
    .getList('list', {
      offset: $scope.offset,
      limit: $scope.limit,
      sort_column: $scope.sortColumn,
      asc: $scope.asc,
      search: $scope.search
    })
    .then(function(data) {
      $scope.documents = data.documents;
      $scope.totalDocuments = data.total;
      $scope.numPages = Math.ceil(data.total / $scope.limit);
    });
  };
  
  /**
   * Reload documents.
   */
  $scope.loadDocuments = function() {
    $scope.offset = 0;
    $scope.currentPage = 1;
    $scope.pageDocuments();
  };
  
  /**
   * Watch for current page change.
   */
  $scope.$watch('currentPage', function(prev, next) {
    if (prev == next) {
      return;
    }
    $scope.offset = ($scope.currentPage - 1) * $scope.limit;
    $scope.pageDocuments();
  });
  
  /**
   * Watch for search scope change.
   */
  $scope.$watch('search', function(prev, next) {
    $scope.loadDocuments();
  }, true);
  
  /**
   * Sort documents.
   */
  $scope.sortDocuments = function(sortColumn) {
    if (sortColumn == $scope.sortColumn) {
      $scope.asc = !$scope.asc;
    } else {
      $scope.asc = true;
    }
    $scope.sortColumn = sortColumn;
    $scope.loadDocuments();
  };
  
  /**
   * Go to add document form.
   */
  $scope.addDocument = function() {
    $state.transitionTo('document.add');
  };
  
  /**
   * Go to edit document form.
   */
  $scope.editDocument = function(id) {
    $state.transitionTo('document.edit', { id: id });
  };
  
  /**
   * Display a document.
   */
  $scope.viewDocument = function(id) {
    $state.transitionTo('document.view', { id: id });
  };
});