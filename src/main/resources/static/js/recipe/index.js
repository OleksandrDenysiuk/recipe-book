$('#deleteRecipeBtn').click(function () {

    var recipeId = $('#recipeId').val();

    var url = '/api/recipes/' + recipeId;

    $.ajax({
        type: "DELETE",
        url: url,
        success: function(response) {
            window.location.href = '/recipes'
        }
    });
});

$(function () {
    var recipeId = $('#recipeId').val();

    var url = '/api/recipes/' + recipeId;

    $.ajax({
        type: "GET",
        url : url,
        success: function (recipe) {
            console.log(recipe);
            $('#image').attr('src', `data:image/jpeg;base64,` + btoa(String.fromCharCode.apply(null, new Uint8Array(recipe.image))));
        }
    })
});