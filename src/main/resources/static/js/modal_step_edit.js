$(document).ready(function () {
    $('#modalStepEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipeId = button.data('recipe');
        var stepId = button.data('step');
        var description = button.data('description');
        var modal = $(this);
        modal.find('.modal-body textarea').val(description);
        modal.find('.modal-body form').attr('action', '/recipe/' + recipeId + '/manual/step/' + stepId + '/edit');
    });
});