$('#deleteRecipeBtn').click(function () {

    var recipeId = $('#recipeId').val();

    var url = '/api/recipes/' + recipeId +'/delete';

    $.ajax({
        type: "DELETE",
        url: url,
        success: function(response) {
            window.location.href = '/recipes'
        }
    });
});